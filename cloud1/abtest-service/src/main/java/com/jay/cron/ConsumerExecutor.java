package com.jay.cron;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
public class ConsumerExecutor implements Callable<DBMessage> {
    private DBQueue notifyService;
    private Consumer processor;
    private DBMessage notify;
    private Map<String, DeletingNode> deletingMap;

    public ConsumerExecutor(DBQueue notifyService, Consumer processor, DBMessage notify, Map<String, DeletingNode> deletingMap) {
        this.notifyService = notifyService;
        this.processor = processor;
        this.notify = notify;
        this.deletingMap = deletingMap;
    }

    @Override
    public DBMessage call() throws Exception {
        long t = System.currentTimeMillis();
        try {
            try {
                if (notify.getMsgStatus() == DBMessage.MSG_STATUS_INIT) {
                    // 只有任务为待执行状态才开始执行
                    notifyService.finishMsg(notify, processor.process(notify));
                }
            } finally {
                DeletingNode deletingNode = new DeletingNode();
                deletingNode.timestamp = System.currentTimeMillis();
                deletingNode.uniqueKey = notify.getUniqueKey();
                synchronized (deletingMap) {
                    deletingMap.put(notify.getUniqueKey(), deletingNode);
                }
            }
        } catch (Exception e) {
            notifyService.finishMsg(notify, false);
            log.error(e.getMessage());
        }
        return notify;
    }
}
