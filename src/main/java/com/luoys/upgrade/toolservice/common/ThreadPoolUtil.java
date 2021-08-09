package com.luoys.upgrade.toolservice.common;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 3, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());

    public static void execute(Runnable task) {
        executor.execute(task);
    }

}
