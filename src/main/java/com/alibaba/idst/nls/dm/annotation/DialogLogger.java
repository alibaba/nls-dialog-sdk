package com.alibaba.idst.nls.dm.annotation;

import org.apache.logging.log4j.Logger;

/**
 * @author jianghaitao
 * @date 2018/11/12
 */
public interface DialogLogger {
    Logger getLogger();

    /**
     * 函数中内容服务的access日志
     *
     * @param requestId
     * @param appkey
     * @param latency
     * @param funcName
     * @param query
     * @param response
     */
    void access(String requestId, String appkey, long latency, String funcName, String query, String response);

    void debug(String message);

    void warn(String message);

    void info(String message);

    void error(String message);

    void error(String var1, Throwable var2);
}
