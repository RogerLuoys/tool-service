package com.luoys.upgrade.toolservice.common;

import java.io.IOException;

public class ProcessUtil {

    public static void killLinuxProcess(String processName) {

    }

    public static void killWindowsProcess(String processName) {
        if (StringUtil.isNotEmpty(processName)) {
            try {
                executeCmd("taskkill /F /IM" + processName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void executeCmd(String command) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /c" + command);
    }
}
