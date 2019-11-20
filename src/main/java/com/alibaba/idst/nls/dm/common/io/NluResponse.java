package com.alibaba.idst.nls.dm.common.io;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public class NluResponse {
    private final List<NluResultElement> elements = new ArrayList<>();
    /**
     * 存放完整多意图结果
     */
    private final List<List<NluResultElement>> multiIntentsElements = new ArrayList<>();
    @SerializedName("error_code")
    private String errorCode;
    @SerializedName("error_message")
    private String errorMessage;
    private String query;
    private String version;
    @SerializedName("request_id")
    private String requestId;
    private TaggerResult tagger;
    private boolean isMultiIntents;
}
