package com.alibaba.idst.nls.dm.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.idst.nls.dm.common.NameOntology;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;

/**
 * @author jianghaitao
 * @date 2019/9/19
 */
@Log4j2
@Data
public class NameResult {
    static final String DELM = "````";
    public static final String ARRAY_DELM = "`";
    NameOntology.NameInfo nameInfo;
    String strValue;

    public NameResult(String val, NameOntology.NameInfo nameInfo) {
        setStrValue(val);
        setNameInfo(nameInfo);
    }

    public String serializeToString() {
        return JSON.toJSONString(nameInfo) + DELM + strValue;
    }

    public boolean deserializeFromString(String str) {
        nameInfo = null;
        strValue = null;
        String[] strs = str.split(DELM);
        if (strs.length < 2) {
            return false;
        } else {
            //to do
            nameInfo = JSON.parseObject(strs[0], NameOntology.NameInfo.class);
            strValue = strs[1];
            return true;
        }
    }

    private String adjustNumUnit(float num, NameOntology.Unit unit)    {
        String rawStrValue = String.valueOf(num);

        int dotPos = rawStrValue.indexOf('.');

        String digitPart = "";
        String intPart = rawStrValue;
        if (dotPos != -1) {
            digitPart = rawStrValue.substring(dotPos + 1);
            intPart = rawStrValue.substring(0, dotPos);

        }


        int k = digitPart.length();
        do {
            --k;
        } while (k >= 0 && digitPart.charAt(k)=='0');

        digitPart = digitPart.substring(0, k+1);

        if(digitPart.isEmpty()) {
            rawStrValue = intPart;
        } else {
            rawStrValue = intPart + "." + digitPart;
        }

        if (unit != null && unit.negativeReplace != null) {
            rawStrValue = rawStrValue.replaceFirst("-", nameInfo.unit.negativeReplace);
        }

        if (unit != null && unit.name != null) {
            rawStrValue = rawStrValue + unit.name;
        }

        return rawStrValue;
    }

    private String adjustArray()
    {
        List<String> array = Arrays.asList(strValue.split(ARRAY_DELM));
        int len = array.size();
        int maxLen = Math.min(len,3);
        String ret = String.join("和", array.subList(0,maxLen));
        if (len > 3) {
            ret += ", 等";
        }
        return ret;
    }

    public String adjust()
    {
        String type = nameInfo.getType();
        if (type.equalsIgnoreCase(AbstractFetch.TYPE_STRING)) {
            return strValue;
        } else if (type.equalsIgnoreCase(AbstractFetch.TYPE_NUMBER)) {
            try {
                float num = Float.parseFloat(strValue);
                NameOntology.Unit unit = nameInfo.getUnit();
                return adjustNumUnit(num, unit);
            } catch (NumberFormatException e) {
                log.info("illegal number format of {}", strValue);
                return "#NULL";
            }
        } else if (type.equalsIgnoreCase(AbstractFetch.TYPE_ARRAY)) {
            return adjustArray();
        }
        else {
            return "#NULL";
        }
    }
}
