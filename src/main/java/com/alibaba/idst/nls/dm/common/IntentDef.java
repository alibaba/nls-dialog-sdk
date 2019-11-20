package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author niannian.ynn
 * Intent的定义（intent运行时的内容例如intent的概率不放在此处）
 */
@Data
public class IntentDef {
    @SerializedName("slots")
    private final List<SlotDef> slotDefs = new ArrayList<>();
    private String domain;
    private String intent;
    @SerializedName("use_dialog")
    private boolean useDialog = true;
}
