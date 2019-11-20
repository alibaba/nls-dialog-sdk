package com.alibaba.idst.nls.dm.common.io;

/**
 * @author jianghaitao
 * @date 2019-06-21
 */
public enum RequestType {
    NORMAL("normal"),
    TEST("test"),
    OTHERS("others");

    private String value;

    RequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
