package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.idst.nls.dm.common.io.Cache;
import com.alibaba.idst.nls.dm.common.io.DialogRequest;
import com.alibaba.idst.nls.dm.common.io.DialogResultElement;
import com.alibaba.idst.nls.dm.common.io.NluResultElement;

import lombok.Data;

/**
 * @author niannian.ynn 包括一轮(一次请求／响应)对话的相关信息
 */

@Data
public class DialogTurn {
    public static final int INVADID_TASK_ID = -1;
    /**
     * 使用的entity
     */
    private final List<Entity> usedEntity = new ArrayList<>();
    /**
     * 原始请求
     */
    private DialogRequest dmRequest;
    /**
     * 转换后的NLU结果
     */
    private NluResult nluResult;
    /**
     * 使用的Nlu结果
     */
    private NluResultElement usedNlu;
    /**
     * selectFn选择的task
     */
    private int selectedTaskId = INVADID_TASK_ID;
    /**
     * 进入的state
     */
    private String selectStateName;
    /**
     * 返回值
     */
    private DialogResultElement dmResponse;
    /**
     * nlg resource cache
     */
    private Cache cache = new Cache();

    /**
     * nlg result
     **/
    private String nlgResult;
}
