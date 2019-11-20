package com.alibaba.idst.nls.dm.function;

import com.alibaba.idst.nls.dm.common.DialogState;
import com.alibaba.idst.nls.dm.common.NameOntology;
import com.alibaba.idst.nls.dm.common.SdmException;

/**
 * @author jianghaitao
 * @date 2019/9/19
 */
public interface FetchExecutor {
    NameResult fetchByName(NameOntology.NameInfo nameInfo, String param, DialogState dialogState, String appkey,
                           String fetchFuncName)throws SdmException;
}
