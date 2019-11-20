package com.alibaba.idst.nls.dm.common;

import lombok.Data;

/**
 * @author niannian.ynn
 * 实体
 */
@Data
public class Entity {
    private int offset;
    private int count;
    private String raw;
    private String norm;
    private String dictTag;
    private String nerTag;
    private float score;
}
