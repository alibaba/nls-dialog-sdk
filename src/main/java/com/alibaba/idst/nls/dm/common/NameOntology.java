package com.alibaba.idst.nls.dm.common;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lijunfeng
 * @date 2019/02/01
 */
@Slf4j
public class NameOntology {

    public Map<String, NameInfo> nameDict = new HashMap<>();

    public NameOntology() {}

    public NameOntology(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            Gson gson = new Gson();
            OntologyData ontologyData = gson.fromJson(content, OntologyData.class);
            for (NameInfo nameInfo : ontologyData.getNameList()) {
                String name = nameInfo.getName();
                if (nameDict.containsKey(name)) {
                    log.info("a duplicated name key[{}] detected, pass..", name);
                    continue;
                }
                nameDict.put(name, nameInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean reloadFromString(String content) {
        try {
            Gson gson = new Gson();
            OntologyData ontologyData = gson.fromJson(content, OntologyData.class);
            nameDict.clear();
            for (NameInfo nameInfo : ontologyData.getNameList()) {
                String name = nameInfo.getName();
                if (nameDict.containsKey(name)) {
                    log.info("a duplicated name key[{}] detected, pass..", name);
                    continue;
                }
                nameDict.put(name, nameInfo);
            }
        } catch (Exception e) {
            log.info("ReloadFromString failed {}", e.getMessage());
            return false;
        }
        return true;
    }

    public boolean addFromString(String content) {
        try {
            Gson gson = new Gson();
            OntologyData ontologyData = gson.fromJson(content, OntologyData.class);
            for (NameInfo nameInfo : ontologyData.getNameList()) {
                String name = nameInfo.getName();
                if (nameDict.containsKey(name)) {
                    log.info("a duplicated name key[{}] detected, pass..", name);
                    continue;
                }
                nameDict.put(name, nameInfo);
            }
        } catch (Exception e) {
            log.info("ReloadFromString failed {}", e.getMessage());
            return false;
        }
        return true;
    }

    public boolean check(String name) {
        return nameDict.containsKey(name);
    }

    public NameInfo getNameInfo(String name) {
        if (check(name)) {
            return nameDict.get(name);
        } else {
            return null;
        }
    }

    @Data
    public static class UnitTransfer {
        @SerializedName("name")
        public String name;
        @SerializedName("scale")
        public float scale;
    }

    @Data
    public static class Unit {
        @SerializedName("base_name")
        public String name;
        @SerializedName("negative_replace")
        public String negativeReplace;
        @SerializedName("transfers")
        public ArrayList<UnitTransfer> transfers;
    }

    @Data
    public static class NameInfo {
        @SerializedName("name")
        public String name;
        @SerializedName("type")
        public String type;
        @SerializedName("package")
        public String pack;
        @SerializedName("func")
        public String funcName;
        @SerializedName("para")
        public String para;
        @SerializedName("unit")
        public Unit unit;
        @SerializedName("cache_expired_seconds")
        public int cacheExpiredSeconds = 0;

        public String getNormType() {
            return getType().toUpperCase();
        }

    }

    @Data
    public static class OntologyData {
        @SerializedName("app")
        public String appName;
        @SerializedName("name_list")
        public List<NameInfo> nameList;
    }
}
