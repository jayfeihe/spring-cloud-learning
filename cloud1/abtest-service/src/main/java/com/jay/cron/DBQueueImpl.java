package com.jay.cron;

import com.jay.cron.sequence.SequenceID;
import com.jay.cron.sequence.service.SequenceService;
import com.jay.util.DateUtils;
import com.jay.util.WebConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p/>
 * 引入DBQueue步骤
 * 1. spring: @Resource and <bean name="dbQueue" class="com.jay.cron.DBQueueImpl"/>
 * 2. mybatis: com.jay.cron
 */
@Transactional
public class DBQueueImpl implements DBQueue {

    @Autowired
    private DBQueueRepo queueRepo;

    @Resource
    private SequenceService sequenceService;

    @Override
    public void finishMsg(DBMessage msg, boolean isSuccess) {
        msg.setFinishTime(DateUtils.getNowDate());
        if (isSuccess) {
            msg.setMsgStatus(DBMessage.MSG_STATUS_SUCCESS);
        } else {
            msg.setMsgStatus(DBMessage.MSG_STATUS_FAIL);
        }
        queueRepo.update(msg);
    }

    private void send(String msgTopic, String msgKey, String content, int msgStatus, Date msgTime) {
        DBMessage msg = new DBMessage();
        msg.setId(sequenceService.next(SequenceID.DbQueue));
        msg.setMsgTopic(msgTopic);
        msg.setMsgKey(StringUtils.isNotBlank(msgKey) ? msgKey : (DateUtils.format(DateUtils.getNowDate(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(15)));
        msg.setMsgContent(content);
        msg.setMsgTime(msgTime);
        msg.setMsgStatus(msgStatus);
        queueRepo.insertWithId(msg);
    }

    @Override
    public void send(String msgTopic, String key, String content) {
        send(msgTopic, key, content, DBMessage.MSG_STATUS_INIT, DateUtils.getNowDate());
    }

    @Override
    public void send(String msgTopic, String key, String content, Date msgTime) {
        send(msgTopic, key, content, DBMessage.MSG_STATUS_INIT, msgTime);
    }

    @Override
    public void send(String msgTopic, String key, String content, int status) {
        send(msgTopic, key, content, status, DateUtils.getNowDate());
    }

    @Override
    public void send(String msgTopic, String content) {
        send(msgTopic, "", content);
    }

    @Override
    public void send(String msgTopic, String content, Date msgTime) {
        send(msgTopic, "", content, msgTime);
    }

    @Override
    public DBMessage getByContentAndStatus(String msgTopic, String msgContent, int msgStatus) {
        return queueRepo.getByContentAndStatus(msgTopic, msgContent, msgStatus, WebConfig.isProd() ? "" : WebConfig.getRuntimeEnv());
    }

    @Override
    public List<DBMessage> getListByContentAndStatus(String msgTopic, String msgContent, int msgStatus) {
        return queueRepo.getListByContentAndStatus(msgTopic, msgContent, msgStatus, WebConfig.isProd() ? "" : WebConfig.getRuntimeEnv());
    }

    @Override
    public DBMessage getByTypeAndKey(String msgTopic, String msgKey) {
        return queueRepo.getByTopicAndKey(msgTopic, msgKey);
    }

    @Override
    public List<DBMessageSummary> getInitMsgList(Date minDate, Date maxDate, int minId) {
        return queueRepo.getInitMsgList(minDate, maxDate, WebConfig.isProd() ? "" : WebConfig.getRuntimeEnv(), minId);
    }

    @Override
    public void update(DBMessage dbMessage) {
        queueRepo.update(dbMessage);
    }

    @Override
    public void insert(DBMessage dbMessage) {
        dbMessage.setMsgKey(StringUtils.isNotBlank(dbMessage.getMsgKey()) ? dbMessage.getMsgKey() : (DateUtils.format(DateUtils.getNowDate(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(15)));
        if(dbMessage.getId()>0){
            dbMessage.setId(sequenceService.next(SequenceID.DbQueue));
        }
        queueRepo.insertWithId(dbMessage);
    }

    @Override
    public DBMessage get(int id) {
        return queueRepo.get(id);
    }

}
