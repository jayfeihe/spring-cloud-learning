package com.jay.cron;

/**
 * 任务具体执行的服务接口，
 * 执行成功返回true，失败返回false
 */
public interface Consumer {
    public boolean process(DBMessage dbMessage) throws Exception;
}
