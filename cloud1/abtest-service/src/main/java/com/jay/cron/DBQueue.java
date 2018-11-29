package com.jay.cron;

import java.util.Date;
import java.util.List;

/**
 * 定时任务生成接口
 */
public interface DBQueue {

    public void finishMsg(DBMessage msg, boolean isSuccess);

    public void send(String msgTopic, String key, String content);

    public void send(String msgTopic, String key, String content, Date msgTime);

    public void send(String msgTopic, String key, String content, int status);

    public void send(String msgTopic, String content);

    public void send(String msgTopic, String content, Date msgTime);

    public DBMessage getByContentAndStatus(String msgTopic, String msgContent, int msgStatus);

    public List<DBMessage> getListByContentAndStatus(String msgTopic, String msgContent, int msgStatus);

    public DBMessage getByTypeAndKey(String msgTopic, String msgKey);

    public List<DBMessageSummary> getInitMsgList(Date minDate, Date maxDate, int minId);

    public void update(DBMessage dbMessage);

    public void insert(DBMessage dbMessage);

    public DBMessage get(int id);

}
