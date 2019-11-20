package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.idst.nls.dm.common.io.DialogRequest;
import com.alibaba.idst.nls.dm.common.io.DialogResultElement;

import lombok.Data;

/**
 * @author hanpu.mwx@alibaba-inc.com
 * @date 2019-05-07
 */
@Data
public class ContextNluRequest {
    /**
     * json string
     *
     * possible key and value types(define http://gitlab.alibaba-inc.com/tvlife/tv_speech) for tian gong:
     * - systemInfo:  com.yunos.tv.speech.biz.SystemInfo
     * - packageInfo: Map<String, String>
     * - sceneExtInfo: com.yunos.tv.speech.biz.bo.SceneExtInfoBO
     * - sceneInfo: com.yunos.tv.speech.biz.bo.SceneInfoBO
     */
    String inputContext;

    DialogTask preTask;
    List<DialogResultElement> outputHistory;

    public ContextNluRequest(DialogState state) {
        DialogTurn currentTurn = state.getCurrentTurn();
        DialogRequest request = currentTurn.getDmRequest();
        inputContext = request.getContext().getOptional();
        if (null == inputContext) {
            inputContext = request.getContent().getOptional();
        }

        preTask = state.getActiveTask();

        outputHistory = new ArrayList<>();
        List<DialogTurn> turns = state.getDialogTurnList();
        if (turns.size() > 1) {
            outputHistory.add(
                turns.get(0).getDmResponse()
            );
        }
    }
}
