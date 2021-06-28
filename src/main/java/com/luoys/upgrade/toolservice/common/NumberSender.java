package com.luoys.upgrade.toolservice.common;

import java.util.Random;

public class NumberSender {
    private static final long TOOL_PREFIX = 90000000000000l;
    private static final int RANDOM_BOUND = 9;
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
        return currentTime * 10 + num;
    }

    public static long createToolId() {
        return TOOL_PREFIX + createIdByCurrentTime();
    }
}
