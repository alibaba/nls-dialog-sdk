package com.alibaba.idst.nls.dm.common.io;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author jianghaitao
 * @date 2019-06-21
 */
@Data
public class SlotItem {
    @SerializedName("raw_value")
    private String rawValue;

    @SerializedName("norm_value")
    private String normValue;

    private String tag;
}
