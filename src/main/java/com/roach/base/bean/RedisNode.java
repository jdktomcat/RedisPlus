package com.roach.base.bean;

import lombok.Data;

/**
 * redis节点信息类
 *
 * @author jdktomcat
 */
@Data
public class RedisNode {

    private String id;

    private String addr;

    private String flag;

    private String pid;

    private String ping;

    private String pong;

    private String epoch;

    private String state;

    private String hash;
}
