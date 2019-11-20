package com.alibaba.idst.nls.dm.common;

import java.util.Objects;

import lombok.Getter;

/**
 * @author jianghaitao
 * @date 2019-07-08
 * fn的类型，bi统计时也需要依赖到，改动里面的值需要和bi同步
 */
@Getter
public enum DialogFunctionType {
    /**
     * ranking func类型，一般出现在并行运行时的ranking func
     */
    RANKING_FUNC("RANKING_FUNC"),
    /**
     * dialog func，普通的函数
     */
    DIALOG_FUNC("DIALOG_FUNC"),
    /**
     * condtion func, flow 流程中的entercondition中的func
     */
    CONDITION_FUNC("CONDITION_FUNC"),
    /**
     * selection func, 在所有函数之前进行选择的函数
     */
    SELECTION_FUNC("SELECTION_FUNC"),
    /**
     * pre init func, 在单个query对话过程中执行的init func
     */
    PRE_INIT_FUNC("PRE_INIT_FUNC"),
    /**
     * nlg func, nlg的函数，nlg的函数和其它几类接口不同，单独分类
     */
    NLG_FUNC("NLG_FUNC"),
    /**
     * task manager的函数
     */
    TASK_MANAGER_FUNC("TASK_MANAGER_FUNC"),
    /**
     * 更新ng module在session context中的函数
     */
    NG_MODULE_FUNC("NG_MODULE_FUNC"),
    /**
     * 去除ng module的函数
     */
    DESTORY_NG_MODULE("DESTORY_NG_MODULE"),
    /**
     * do remote dialog flow
     */
    FLOW("FLOW");

    private String value;

    private DialogFunctionType(String value) {
        this.value = value;
    }

    /**
     * 默认为普通的函数，普通的函数为DIALOG_FUNC
     *
     * @param value
     * @return
     */
    public static DialogFunctionType getByValue(String value) {
        if (Objects.isNull(value)) {
            return DIALOG_FUNC;
        }

        for (DialogFunctionType tmpEnum : values()) {
            if (value.equals(tmpEnum.getValue())) {
                return tmpEnum;
            }
        }
        return DIALOG_FUNC;
    }
}
