package com.jay.cron;

import com.jay.util.DateUtils;

import java.util.Date;

public class DBMessageSummary {
    private int id;
    private String msgTopic;
    private Date msgTime = DateUtils.getNowDate();

    public String getUniqueKey() {
        return String.format("consumer:%s:%s", msgTopic, id);
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsgTopic() {
        return msgTopic;
    }

    public void setMsgTopic(String msgTopic) {
        this.msgTopic = msgTopic;
    }
}
