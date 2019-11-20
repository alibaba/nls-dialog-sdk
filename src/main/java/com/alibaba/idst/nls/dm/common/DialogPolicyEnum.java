package com.alibaba.idst.nls.dm.common;

import lombok.Getter;

/**
 * @author jianghaitao
 * @date 2019-05-27
 * 用户对话策略的枚举类型
 */
@Getter
public enum DialogPolicyEnum {
    /**
     * 对话优先
     */
    DIALOG_POLICY_DIALOG_FIRST(1),

    /**
     * 问答优先
     */
    DIALOG_POLICY_QA_FIRST(2),

    /**
     * 用用户的函数排序
     */
    DIALOG_POLICY_CUSTOMIZED(3);

    private int value;

    private DialogPolicyEnum(int value) {
        this.value = value;
    }
}
