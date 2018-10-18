package com.alibaba.idst.nls.uds.request.context;

import lombok.Data;

/**
 * 客户端
 */
@Data
public class SDK {
    /**
     * 客户端名称
     */
    private String name;
    /**
     * 客户端版本
     */
    private String version;
    /**
     * 客户端使用的编程语言，全小写，例如：java, c++
     */
    private String language;
}
