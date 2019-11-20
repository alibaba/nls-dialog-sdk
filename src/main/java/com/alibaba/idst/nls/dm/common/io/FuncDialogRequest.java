package com.alibaba.idst.nls.dm.common.io;

import lombok.Data;

/**
 * @author jianghaitao
 * @date 2019-07-05
 */
@Data
public class FuncDialogRequest {
    private String funcName;
    private String param;
    private String requestId;
    private String sessionId;
    private String dialogId;
    private String sessionKeys;
    private String nameInfo;
    private String multiFuncRun;
    private String useDialog;
    private String historyTaskCnt;
    private String selectedStateName;
    private String ngName;
    private String ngValue;
    private String defaultFuncName;
    private String defaultFuncParam;
    private String selectionFuncName;
    private String selectionFuncParam;
    private String rankingFuncName;

    public boolean isMultiFuncRun(){
        return "true".equals(multiFuncRun);
    }

    public boolean isUseDialog(){
        return "true".equals(useDialog);
    }

    public Integer getHistoryTaskCount(){
        return Integer.valueOf(historyTaskCnt);
    }
}
