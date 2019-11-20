package com.alibaba.idst.nls.dm.common.io;

import java.util.ArrayList;

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
public class DialogRequest {
    public static final String DEBUG_KEY = "DEBUG_STATE";
    public static final String END_OF_SENTENCE_KEY = "end_of_sentence";

    @SerializedName("request_id")
    private String requestId;
    @SerializedName("app_key")
    private String appKey;
    private RequestContent content;
    private RequestContext context;
    private String version;

    public boolean isEosRequest() {
        if (content != null) {
            if (content.getQueryParams() != null) {
                for (ParamItem item : content.getQueryParams()) {
                    if (END_OF_SENTENCE_KEY.equalsIgnoreCase(item.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public DebugModeEnum getDebugMode() {
        if (content != null && content.getQueryParams() != null) {
            for (ParamItem paramItem : content.getQueryParams()) {
                if (DEBUG_KEY.equalsIgnoreCase(paramItem.getName())) {
                    try {
                        return DebugModeEnum.valueOf(paramItem.getValue());
                    } catch (IllegalArgumentException e) {
                        // nothing to do here
                    }
                }
            }
        }
        return null;
    }

    public void setDebugMode(DebugModeEnum mode) {
        if (content == null) {
            content = RequestContent.builder().build();
        }
        if (content.getQueryParams() == null) {
            content.setQueryParams(new ArrayList<>());
        }
        ParamItem paramItem = new ParamItem();
        paramItem.setName(DEBUG_KEY);
        paramItem.setValue(mode.name());
        content.getQueryParams().add(paramItem);
    }
}
