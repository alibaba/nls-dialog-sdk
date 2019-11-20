package com.alibaba.idst.nls.dm.function;

/**
 * @author jianghaitao
 * @date 2019/9/19
 */
import java.util.ArrayList;

import com.alibaba.idst.nls.dm.common.DialogState;
import com.alibaba.idst.nls.dm.common.DialogTurn;
import com.alibaba.idst.nls.dm.common.NameOntology;
import com.alibaba.idst.nls.dm.common.io.Cache;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author lijunfeng
 * @date 2019/02/01
 */
@Log4j2
@Data
public abstract class AbstractFetch {
    public static final String TYPE_STRING = "STRING";
    public static final String TYPE_NUMBER = "NUMBER";
    public static final String TYPE_ARRAY = "ARRAY";
    public NameOntology.NameInfo nameInfo;

    /**
     * fetch
     * @return object
     */


    public String getFromCache(String param, DialogState dialogState)
    {
        int cacheExpireSeconds = nameInfo.cacheExpiredSeconds;
        String funcName = this.getClass().getName();
        String k = funcName + "`" + param;
        if (cacheExpireSeconds <= 0) {
            DialogTurn currentTurn = dialogState.getDialogTurnList().isEmpty() ? null : dialogState.getDialogTurnList().get(dialogState.getDialogTurnList().size() - 1);
            if (currentTurn == null) {
                return null;
            } else {
                Cache cache = currentTurn.getCache();
                if(cache.hasKey(k)) {
                    return cache.getContent(k);
                } else {
                    return null;
                }
            }
        } else {
            Cache cache = dialogState.getCache();
            if(cache.hasKey(k) && !cache.isExpired(k, cacheExpireSeconds)){
                return cache.getContent(k);
            } else {
                return null;
            }
        }
    }

    public void updateCache(String param, String content, DialogState dialogState)
    {
        int cacheExpireSeconds = nameInfo.cacheExpiredSeconds;
        String funcName = this.getClass().getName();
        String k = funcName + "`" + param;
        if (cacheExpireSeconds <= 0) {
            DialogTurn currentTurn = dialogState.getDialogTurnList().isEmpty() ? null :
                dialogState.getDialogTurnList().get(dialogState.getDialogTurnList().size() - 1);
            if (currentTurn != null) {
                Cache cache = currentTurn.getCache();
                cache.put(k, content);
            }
        } else {
            Cache cache = dialogState.getCache();
            cache.put(k, content);
        }
    }

    public static ArrayList<String> extractParams(String param)
    {
        if (param == null) {
            return null;
        }
        param = param.trim();
        ArrayList<String> ret = new ArrayList<String>();
        String[] params = param.split(",");
        for (int i = 0 ; i < params.length ; ++i) {
            ret.add(params[i].trim());
        }
        return ret;
    }

    public static String getParamAt(ArrayList<String> params, int index)
    {
        if(params == null) {
            return null;
        }
        try {
            String ret = params.get(index);
            return ret;
        } catch (java.lang.IndexOutOfBoundsException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public abstract NameResult fetch(String param, DialogState dialogState);
}

