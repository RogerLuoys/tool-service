package com.luoys.upgrade.toolservice.common;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 3, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor uiExecutor = new ThreadPoolExecutor(0, 1, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(4), new ThreadPoolExecutor.AbortPolicy());

    public static void execute(Runnable task) {
        executor.execute(task);
    }

    public static void executeUI(Runnable task) {
        uiExecutor.execute(task);
    }

}
