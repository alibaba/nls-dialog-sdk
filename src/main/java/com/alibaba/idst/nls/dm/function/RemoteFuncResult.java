package com.alibaba.idst.nls.dm.function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianghaitao
 * @date 2019/9/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteFuncResult extends FunctionResult{
    private NameResult nameResult;
    private int multiFuncNum;
    private int selectedTaskId;
    private String selectStateName;
}
