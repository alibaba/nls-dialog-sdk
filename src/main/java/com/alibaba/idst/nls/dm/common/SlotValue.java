package com.alibaba.idst.nls.dm.common;

import lombok.Data;

/**
 * @author dengchong.d
 * @date 2018/10/24
 */
@Data
public class SlotValue {
    /**
     * slot类型（例如：tagger的词典名）
     */
    private String tag;
    private String norm;
    private String raw;
    private double score;
    private String source;
    // ========= add for context nlu ==========
    public int offset;
    public int count;

    public SlotValue() {
    }

    public SlotValue(String norm, String raw, String tag){
        this.norm = norm;
        this.raw = raw;
        this.tag = tag;
    }

    public SlotValue(String norm, String raw, String tag, double score){
        this.norm = norm;
        this.raw = raw;
        this.tag = tag;
        this.score = score;
    }

    public SlotValue(String norm, String raw, String tag, double score, String source){
        this.norm = norm;
        this.raw = raw;
        this.tag = tag;
        this.score = score;
        this.source = source;
    }

    public SlotValue(String norm, String tag) {
        this.norm = norm;
        this.tag = tag;
    }

    public SlotValue(String norm) {
        this.norm = norm;
        this.raw = norm;
    }
}
