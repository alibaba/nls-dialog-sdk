package com.alibaba.idst.nls.dm.common;

/**
 * @author jianghaitao
 * @date 2019-05-21
 */
public class SdmException extends Exception {
    private final DialogResultCodeEnum errorCode;

    public SdmException(String message, DialogResultCodeEnum errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DialogResultCodeEnum getErrorCode() {
        return errorCode;
    }
}
