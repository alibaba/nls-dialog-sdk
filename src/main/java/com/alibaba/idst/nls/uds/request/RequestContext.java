package com.alibaba.idst.nls.uds.request;

import com.alibaba.idst.nls.uds.request.context.*;
import com.alibaba.idst.nls.uds.request.context.System;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 对话环境
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestContext {
    /**
     * 客户端
     */
    private SDK sdk;
    /**
     * 应用
     */
    private App app;
    /**
     * 系统
     */
    private System system;
    /**
     * 设备信息
     */
    private Device device;
    /**
     * 网络
     */
    private Network network;
    /**
     * 地理位置
     */
    private Geography geography;
    /**
     * 特殊参数，用户自定义
     */
    private Map<String, String> custom;
    /**
     * 可选参数，用来兼容老版本协议
     */
    private String optional;
}
