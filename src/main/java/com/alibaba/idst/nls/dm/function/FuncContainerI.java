package com.alibaba.idst.nls.dm.function;

import java.util.Map;

/**
 * @author hanpu.mwx@alibaba-inc.com
 * @date 2019-04-10
 */
public interface FuncContainerI {
    boolean load(String appKey, boolean isWarmup);

    boolean unload(String appKey);

    Map<String, Class> getSystemFuncMap();

    Class getDialogFunc(String appkey, String funcName);

    Class getProxyFunc(String appkey, String proxyType);

    Object createInstance(Class clazz, String appkey);

    ClassLoader getClassLoaderByAppkey(String appkey);
}
