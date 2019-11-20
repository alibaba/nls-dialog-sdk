package com.alibaba.idst.nls.dm.function;

import com.alibaba.idst.nls.dm.common.io.DialogResultElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niannian.ynn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResult {
    private String retCode;
    private String retValue;
    private DialogResultElement dialogResult;

    public boolean isOK() {
        return FunctionBase.RET_CODE_OK.equals(retCode);
    }

    public boolean isTrue() {
        return FunctionBase.RET_VALUE_TRUE.equals(retValue);
    }
}
