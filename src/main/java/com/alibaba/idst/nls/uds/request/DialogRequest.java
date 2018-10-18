package com.alibaba.idst.nls.uds.request;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogRequest {

    @JSONField(name = "request_id")
    private String requestId;

    @JSONField(name = "app_key")
    private String appKey;

    /**
     * 对话内容
     */
    @JSONField(name = "content")
    private RequestContent content;

    /**
     * 对话环境
     */
    @JSONField(name = "context")
    private RequestContext context;

    private String version;

}
