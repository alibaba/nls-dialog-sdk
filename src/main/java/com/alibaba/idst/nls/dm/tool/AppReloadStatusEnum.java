package com.alibaba.idst.nls.dm.tool;

import lombok.Getter;

/**
 * @author jianghaitao
 * @date 2018/12/20
 */

@Getter
public enum AppReloadStatusEnum {
    /**
     * 没有发布
     */
    NOT_PUBLISHED(-1, "未发布"),

    /**
     * 开始reload
     */
    RELOADING(1, "上线中"),

    /**
     * reload success
     */
    SUCCESS(2, "上线成功"),

    /**
     * reload fail
     */
    FAIL(3, "上线失败");

    private int code;
    private String value;

    AppReloadStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
