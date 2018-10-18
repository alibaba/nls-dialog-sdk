package com.alibaba.idst.nls.uds.request.context;

import lombok.Data;

/**
 * 系统
 */
@Data
public class System {
    /**
     * 操作系统名称
     */
    private String name;
    /**
     * 操作系统版本
     */
    private String version;
    /**
     * 操作系统本地时间，毫秒级时间戳格式
     */
    private String time;
    /**
     * 操作系统时区
     */
    private String timezone;
    /**
     * 操作系统语言区域 zh_CN
     */
    private String locale;
}
