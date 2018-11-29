package com.jay.cron;

import com.jay.util.DateUtils;
import com.jay.util.WebConfig;

import java.util.Date;

/**
 * 用于标识一个任务
 */
public class DBMessage {
    public static final int MSG_STATUS_DRAFT = 0; // 草稿
    public static final int MSG_STATUS_INIT = 1; // 初始化状态
    public static final int MSG_STATUS_SUCCESS = 2; // 处理完成 - 成功
    public static final int MSG_STATUS_FAIL = 3; // 处理完成 - 失败
    public static final int MSG_STATUS_CANCEL = 4; // 取消


    private int id;
    private String msgTopic;  // 定时任务执行的 BeanName
    private String msgKey;  //根据时间生成的任务标识
    private String msgContent; //任务执行参数
    private int msgStatus;  // 0未推送 1已推送 2推送失败
    private Date msgTime = DateUtils.getNowDate();
    private Date createTime;  //创建时间
    private Date finishTime;  //任务执行完成时间
    private String env;

    public String getUniqueKey() {
        return String.format("consumer:%s:%s", msgTopic, id);
    }

    public String getEnv() {
        if (WebConfig.isProd()) {
            return "";
        }
        return WebConfig.getRuntimeEnv();
    }

    public void setEnv(String env) {

    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getMsgTopic() {
        return msgTopic;
    }

    public void setMsgTopic(String msgTopic) {
        this.msgTopic = msgTopic;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
