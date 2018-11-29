package com.jay.cron;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface DBQueueRepo {

    public DBMessage get(@Param("id") int id);

    public void insertWithId(DBMessage msg);

    public DBMessage getByTopicAndKey(@Param("msgTopic") String msgTopic, @Param("msgKey") String msgKey);

    public void update(DBMessage msg);

    public List<DBMessageSummary> getInitMsgList(@Param("minTime") Date minTime, @Param("maxTime") Date maxTime, @Param("env") String env, @Param("minId") int minId);

    public DBMessage getByContentAndStatus(@Param("msgTopic") String msgType,
                                           @Param("msgContent") String msgContent,
                                           @Param("msgStatus") int msgStatus,
                                           @Param("env") String env);

    public List<DBMessage> getListByContentAndStatus(@Param("msgTopic") String msgType,
                                                     @Param("msgContent") String msgContent,
                                                     @Param("msgStatus") int msgStatus,
                                                     @Param("env") String env);
}
