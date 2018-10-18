package com.alibaba.idst.nls.uds.context;

import java.util.List;
import java.util.Map;

import com.alibaba.idst.nls.uds.request.DialogRequest;
import com.alibaba.idst.nlu.response.common.Slot;

public interface DialogSession {

    /**
     * 获得appKey
     * @return
     */
    String getAppKey();

    /**
     * 获取当前sessionId
     *
     * @return
     */
    String getSessionId();

    /**
     * 获得客户端请求参数
     *
     * @return
     */
    DialogRequest getDialogRequest();

    /**
     * 获取session中所有slots
     *
     * @return
     */
    Map<String, List<Slot>> getSlots();

    /**
     * 获取session中指定domain的slots
     *
     * @param domain
     * @return
     */
    Map<String, List<Slot>> getSlots(String domain);

}
