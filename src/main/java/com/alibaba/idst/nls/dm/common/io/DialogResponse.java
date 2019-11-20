package com.alibaba.idst.nls.dm.common.io;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianghaitao
 * @date 2018/11/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogResponse {
    @SerializedName("error_code")
    private Integer errorCode;

    @SerializedName("error_message")
    private String errorMessage;

    private String version;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("query")
    private String query;
    private List<DialogResultElement> results;
    private String optional;
}
