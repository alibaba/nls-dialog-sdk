package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.idst.nls.dm.common.io.NluResponse;

import lombok.Data;

/**
 * @author niannian.ynn DM所需要的NLU结果
 */
@Data
public class NluResult {
    /**
     * 原始nlu结果
     */
    private final NluResponse nluResponse;
    /**
     * top N intent 序列，支持多意图
     */
    private final List<IntentSequence> intentSequences = new ArrayList<>();
    /**
     * 实体词信息，tag与ner的结果
     */
    private final List<Entity> entities = new ArrayList<>();

    /**
     * 记录plugin的一些信息，可以辅助排序使用
     */
    private List<String> TriggerPluginList;

    public NluResult(NluResponse nluResponse) {
        this.nluResponse = nluResponse;
    }

    /**
     * 判断top1NLU结果是否多意图结果
     */
    public boolean isMultiIntents() {
        if (!intentSequences.isEmpty()) {
            return intentSequences.get(0).getIntents().size() > 1;
        }
        return false;
    }
}
