package com.alibaba.idst.nls.uds.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.idst.nls.uds.request.DialogRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DialogResponse {

    @JSONField(name = "error_code")
    private Integer errorCode;

    @JSONField(name = "error_message")
    private String errorMessage;

    private String version;

    @JSONField(name = "request_id")
    private String requestId;

    @JSONField(name = "session_id")
    private String sessionId;

    @JSONField(name = "query")
    private String query;

    private List<DialogResultElement> results;

    private String optional;

    public DialogResponse(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public DialogResponse(Integer errorCode, String errorMessage, DialogRequest request) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.version = request.getVersion();
        this.requestId = request.getRequestId();
        this.sessionId = request.getContent().getSessionId();
        this.query = request.getContent().getQuery();
    }

    public void copy(DialogRequest request) {
        this.version = request.getVersion();
        this.requestId = request.getRequestId();
        this.sessionId = request.getContent().getSessionId();
        this.query = request.getContent().getQuery();
    }
}