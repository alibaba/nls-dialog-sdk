package com.alibaba.idst.nls.uds.nlu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.idst.nls.uds.nlu.time.TimeX;
import com.alibaba.idst.nlu.response.common.NluResultElement;
import com.alibaba.idst.nlu.response.slot.SlotProcessor;
import com.alibaba.idst.nlu.response.utils.SlotConstants;

import org.apache.commons.lang3.StringUtils;

public class NluSlotProcessor extends SlotProcessor {
    public static final String SYS_PIX = "sys.";
    public static final String TIMEX = "timex";

    @Override
    protected void handleSlotJsonObject(NluResultElement element, String slotKey, JSONObject jsonObject, String type) {
        if (StringUtils.equals(type, SlotConstants.SLOT_TYPE_DATETIME)) {
            if (jsonObject.containsKey(TIMEX)) {
                TimeX timex = new TimeX();
                if (!timex.build(jsonObject)) {
                    System.out.println("SlotProcessor slot has TimeX field but build timex fail, json : " +
                        JSON.toJSONString(jsonObject));
                } else {
                    element.putSlot(SYS_PIX + slotKey, timex);
                }
            }
        }
    }
}
