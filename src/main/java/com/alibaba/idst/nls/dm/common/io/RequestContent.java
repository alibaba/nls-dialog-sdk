package com.alibaba.idst.nls.dm.common.io;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niannian.ynn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestContent {
    @SerializedName("session_id")
    private String sessionId;
    private String query;
    @SerializedName("query_params")
    private List<ParamItem> queryParams;
    private String optional;
}
