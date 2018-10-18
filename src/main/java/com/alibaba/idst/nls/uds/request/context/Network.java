package com.alibaba.idst.nls.uds.request.context;

import lombok.Data;

/**
 * 网络
 */
@Data
public class Network {
    /**
     * IP地址
     */
    private String ip;
    /**
     * MAC地址
     */
    private String mac;
    /**
     * 网络类型
     */
    private String type;
    /**
     * 网络子类型，如：HSPA、EVDO、EDGE、GPRS等
     */
    private String subtype;
    /**
     * 网络运营商
     */
    private String carrier;
}
