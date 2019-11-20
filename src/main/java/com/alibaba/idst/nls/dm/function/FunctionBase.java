package com.alibaba.idst.nls.dm.function;

import java.util.List;

import com.alibaba.idst.nls.dm.common.DialogState;
import com.alibaba.idst.nls.dm.common.io.DialogRequest;
import com.alibaba.idst.nls.dm.common.io.DialogResultElement;

import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public abstract class FunctionBase {

    /**
     * OK为标准的统一的无出错的返回码
     */
    public static final String RET_CODE_OK = "OK";
    /**
     * 函数返回错误
     */
    public static final String RET_CODE_FAIL = "FAIL";
    /**
     * 参数出错返回码
     */
    public static final String RET_CODE_INVALID_ARGS = "INVALID_ARGS";
    /**
     * 标准bool值
     */
    public static final String RET_VALUE_TRUE = "true";
    public static final String RET_VALUE_FALSE = "false";
    /** 出错码或者其它的返回值由Funtion自己决定，不需要写在这里面 */

    /**
     * 入口方法
     *
     * @param param       输入参数
     * @param dialogState
     * @return FunctionResult
     */
    public abstract FunctionResult exec(String param, DialogState dialogState);

    /**
     * 用户自定义ranking函数入口函数
     */
    public FunctionResult execRankingFunc(DialogState dialogState, List<DialogResultElement> dialogResultElements) {
        return null;
    }

    /**
     * 用户自定义的init函数入口
     *
     * @return
     */
    public FunctionResult execInitFunc() {
        return null;
    }

    /**
     * 用户自定义的代理函数
     *
     * @param dialogRequest
     * @return
     */
    public FunctionResult execProxyFunc(DialogRequest dialogRequest) {
        return null;
    }
}
