package com.alibaba.idst.nls.dm.common.io;

import java.util.Objects;

import lombok.Getter;

/**
 * @author jianghaitao
 * @date 2018/11/20
 */
@Getter
public enum DebugModeEnum {
    /**
     * 在dst之后
     */
    AFTER_DST(0),

    /**
     * 在tm之后
     */
    AFTER_TM(1),

    /**
     * 在flow之后
     */
    AFTER_FLOW(2),

    /**
     * 为对外的toolkit的debug标识
     */
    FOR_PUBLIC_TOOLKIT(3);

    int value;

    private DebugModeEnum(int value) {
        this.value = value;
    }

    public static DebugModeEnum getByValue(Integer value) {
        if (Objects.isNull(value)) {
            return null;
        }

        for (DebugModeEnum tmpMode : values()) {
            if (Objects.equals(tmpMode.getValue(), value)) {
                return tmpMode;
            }
        }

        return null;
    }
}
