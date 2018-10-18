package com.alibaba.idst.nls.uds.request.context;

import lombok.Data;

/**
 * 应用
 */
@Data
public class App {
    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用版本
     */
    private String version;
    /**
     * 应用开发者
     */
    private String developer;
}
