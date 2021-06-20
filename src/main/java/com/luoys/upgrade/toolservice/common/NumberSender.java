package com.luoys.upgrade.toolservice.common;

import java.util.Random;

public class NumberSender {
    private static final String TOOL_PREFIX = "9";
    private static final Integer RANDOM_BOUND = 9;
    private static Random rd = new Random();
    private static Thread nThread = new Thread();

    public NumberSender() {
    }

    public static long createIdByCurrentTime() {
        nThread.start();
        try {
            Thread.sleep(1L);
        } catch (InterruptedException var3) {
            System.out.println(var3);
        }

        long currentTime = System.currentTimeMillis();
        int num = rd.nextInt(RANDOM_BOUND);
        //todo thread
        return currentTime * 10 + num;
    }

    public static String createToolId() {
        return TOOL_PREFIX + createIdByCurrentTime();
    }
}
