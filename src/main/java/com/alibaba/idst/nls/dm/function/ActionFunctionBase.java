package com.alibaba.idst.nls.dm.function;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hanpu.mwx@alibaba-inc.com
 * @date 2019-04-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ActionFunctionBase extends FunctionBase {
    private String nlg;

    /**
     * generate nlg from pattern, and set result into nlg
     */
    //public abstract void computeNlg(String nlgPattern, DialogState dialogState) throws DMException;
}
