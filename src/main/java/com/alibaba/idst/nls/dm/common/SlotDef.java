package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author niannian.ynn Slot的静态定义（slot运行时的内容例如slot vlaue不放在此处）
 */
@Data
public class SlotDef {
    /**
     * 词槽对应的类型
     */
    private final List<String> type = new ArrayList<>();
    /**
     * 期待的结果（在askFilling阶段使用所见即所得获取结果）
     */
    @SerializedName("except_values")
    private final List<String> exceptValues = new ArrayList<>();
    /**
     * 词槽名
     */
    private String name;
    @SerializedName("is_inherit")
    private boolean isInherit = false;
}
