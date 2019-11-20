package com.alibaba.idst.nls.dm.common.io;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public class TaggerResult {

    private final List<Tag> tags = new ArrayList<>();

    @Data
    public static class Tag {
        private int count;
        private int offset;
        private String norm;
        @SerializedName("raw_text")
        private String raw;
        private String tag;
        private double score;
    }
}
