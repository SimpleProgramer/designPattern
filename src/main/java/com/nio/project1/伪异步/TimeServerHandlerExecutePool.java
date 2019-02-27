package com.nio.project1.伪异步;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzun
 * @version 2019/2/25 下午9:40
 * @desc
 */
public class TimeServerHandlerExecutePool  {

    private ExecutorService executor;

    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120l, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }
}
