package com.luoys.upgrade.toolservice.common;

import java.util.Random;

/**
 * 发号器，uuid
 *
 * @author luoys
 */
public class NumberSender {
    private static final long SUITE_PREFIX = 10000000000000L;
    private static final long CASE_PREFIX = 20000000000000L;
    private static final long STEP_PREFIX = 30000000000000L;
    private static final long RESOURCE_PREFIX = 40000000000000L;
    private static final long TOOL_PREFIX = 50000000000000L;
    private static final long USER_PREFIX = 50000000000000L;
    private static final int RANDOM_BOUND = 9;
    private static final Random rd = new Random();

//    public NumberSender() {
//    }

    private static long createIdByCurrentTime() {
        try {
            Thread.sleep(1L);
        } catch (InterruptedException var3) {
            return 0L;
        }
        long currentTime = System.currentTimeMillis();
        int num = rd.nextInt(RANDOM_BOUND);
        return currentTime * 10 + num;
    }

    public static String createSuiteId() {
        return String.valueOf(SUITE_PREFIX + createIdByCurrentTime());
    }

    public static String createCaseId() {
        return String.valueOf(CASE_PREFIX + createIdByCurrentTime());
    }

    public static String createStepId() {
        return String.valueOf(STEP_PREFIX + createIdByCurrentTime());
    }

    public static String createResourceId() {
        return String.valueOf(RESOURCE_PREFIX + createIdByCurrentTime());
    }

    public static String createToolId() {
        return String.valueOf(TOOL_PREFIX + createIdByCurrentTime());
    }

    public static String createUserId() {
        return String.valueOf(USER_PREFIX + createIdByCurrentTime());
    }
}
