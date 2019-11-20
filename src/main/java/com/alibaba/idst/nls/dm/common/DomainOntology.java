package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public class DomainOntology {
    @SerializedName("intents")
    public final List<IntentDef> intentDefs = new ArrayList<>();
    public NameOntology nameOntology = new NameOntology();

    @Deprecated
    public static DomainOntology parseFromFile(String conf) {
        // 从文件内容(string)生成DomainOntology数据结构
        return new Gson().fromJson(conf, DomainOntology.class);
    }

    public static DomainOntology parseFromFileNew(String conf) {
        // 从文件内容(string)生成DomainOntology数据结构
        return new Gson().fromJson(conf, DomainOntology.class);
    }

    private int searchIntentDef(String domain, String intent) {
        for (int i = 0; i < intentDefs.size(); i++) {
            if (intentDefs.get(i).getDomain().equals(domain) && intentDefs.get(i).getIntent().equals(intent)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isDomainUseDialog(String domain, String intent) {
        int i = searchIntentDef(domain, intent);
        if (i == -1) {
            return false;
        }
        return intentDefs.get(i).isUseDialog();
    }

    public IntentDef getIntentByName(String domain, String intent) {
        for (IntentDef intentDef : intentDefs) {
            if (intentDef.getDomain().equalsIgnoreCase(domain) && intentDef.getIntent().equalsIgnoreCase(intent)) {
                return intentDef;
            }
        }
        return null;
    }
}
