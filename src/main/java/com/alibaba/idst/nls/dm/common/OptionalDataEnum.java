package com.alibaba.idst.nls.dm.common;

import lombok.Getter;

/**
 * @author jianghaitao
 * @date 2019/8/27
 */
@Getter
public enum OptionalDataEnum {
    /**
     * 通过sth获取data
     */
    STH_DATA(1);

    private int value;

    private OptionalDataEnum(int value) {
        this.value = value;
    }
}
