package com.alibaba.idst.nls.dm.common;

import com.alibaba.idst.nls.dm.common.io.DialogRequest;
import com.alibaba.idst.nls.dm.common.io.QaAnswer;

import java.util.List;

/**
 * @author jianghaitao
 * @date 2019/10/15
 */
public interface QasClient {
    public List<QaAnswer>getQasResult(String appkey, DialogRequest dialogRequest);
}
