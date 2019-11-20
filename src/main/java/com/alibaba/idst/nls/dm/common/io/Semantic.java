package com.alibaba.idst.nls.dm.common.io;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author jianghaitao
 * @date 2019-06-21
 */
@Data
public class Semantic {
    private String domain;
    private String intent;
    private String source;
    private double score;
    private Map<String, List<SlotItem>> slots;
}
