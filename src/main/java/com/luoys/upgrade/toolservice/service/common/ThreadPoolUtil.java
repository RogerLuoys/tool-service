package com.luoys.upgrade.toolservice.service.common;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author luoys
 */
public class ThreadPoolUtil {

    private static ThreadPoolExecutor apiExecutor = new ThreadPoolExecutor(0, 3, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutor uiExecutor = new ThreadPoolExecutor(0, 1, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());

    public static void executeAPI(Runnable task) {
        apiExecutor.execute(task);
    }

    public static void executeUI(Runnable task) {
        uiExecutor.execute(task);
    }

    public static Future submitUI(Runnable task) {
        return uiExecutor.submit(task);
    }

    public static Future submitAPI(Runnable task) {
        return apiExecutor.submit(task);
    }

}
