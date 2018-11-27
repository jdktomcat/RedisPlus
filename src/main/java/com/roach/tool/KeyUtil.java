package com.roach.tool;

import java.util.UUID;

/**
 * 唯一键生成器
 *
 * @author jdktomcat
 */
public class KeyUtil {

    /**
     * 生成32位的UUid
     */
    public static String getUUIDKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("ss[" + i + "]=====" + getUUIDKey());
        }
    }

}
