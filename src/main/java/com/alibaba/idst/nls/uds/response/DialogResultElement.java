package com.alibaba.idst.nls.uds.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.idst.nls.uds.model.ParamItem;
import com.alibaba.idst.nlu.response.common.NluResultElement;

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
public class DialogResultElement {

    @JSONField(serialize = false)
    private NluResultElement nluResultElement;

    private String action;

    @JSONField(name = "action_params")
    private List<ParamItem> actionParams;

    @JSONField(name = "spoken_text")
    private String spokenText;

    @JSONField(name = "display_text")
    private String displayText;

    private String optional;

}
