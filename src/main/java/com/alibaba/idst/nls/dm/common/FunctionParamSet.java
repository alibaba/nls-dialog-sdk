package com.alibaba.idst.nls.dm.common;

import lombok.Builder;
import lombok.Data;

/**
 * toolkit需要使用到的function的参数集合，目前用户写的function有两个种类，继承自FunctionBase的function
 * 和继承自AbstractFetch的nlg function.
 * @author jianghaitao
 * @date 2019/11/6
 */
@Data
@Builder
public class FunctionParamSet {
    //抽像函数的入参
    private String param;
    //抽像函数需要传入的dialog state
    private DialogState dialogState;
    //函数的名字
    private String funcName;
    //nlg fetch的时候需要先set name info
    private NameOntology.NameInfo nameInfo;
    //函数的类型，主要包含两类，nlg和dialog func
    private DialogFunctionType dialogFunctionType;
}
