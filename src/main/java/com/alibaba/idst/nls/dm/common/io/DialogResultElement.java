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
public class DialogResultElement {
    @SerializedName("semantics")
    List<Semantic> semantics;
    @SerializedName("nlu_results")
    private NluResultElement nluResultElement;
    private String action;
    @SerializedName("action_params")
    private List<ParamItem> actionParams;
    @SerializedName("spoken_text")
    private String spokenText;
    @SerializedName("display_text")
    private String displayText;
    private String optional;

    @SerializedName("skill_name")
    private String skillName;

    private Integer dialogStateId;

    public DialogResultElement setActionExt(String action) {
        this.action = action;
        return this;
    }

    public DialogResultElement setActionParamsExt(List<ParamItem> actionParams) {
        this.actionParams = actionParams;
        return this;
    }

    public DialogResultElement setSpokenTextExt(String spokenText) {
        this.spokenText = spokenText;
        return this;
    }

    public DialogResultElement setDisplayTextExt(String displayText) {
        this.displayText = displayText;
        return this;
    }

    public DialogResultElement setOptionalExt(String optional) {
        this.optional = optional;
        return this;
    }
}
