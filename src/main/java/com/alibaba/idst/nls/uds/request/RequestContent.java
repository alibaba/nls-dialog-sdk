package com.alibaba.idst.nls.uds.request;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.idst.nls.uds.model.ParamItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对话内容
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestContent {

    @JSONField(name = "session_id")
    private String sessionId;

    @JSONField(name = "query")
    private String query;

    @JSONField(name = "query_params")
    private List<ParamItem> queryParams;

}
