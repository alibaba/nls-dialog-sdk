package com.alibaba.idst.nls.dm.common;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.alibaba.idst.nls.dm.common.io.Cache;
import com.alibaba.idst.nls.dm.common.io.DebugModeEnum;
import com.alibaba.idst.nls.dm.common.io.DialogRequest;
import com.alibaba.idst.nls.dm.common.io.DialogResultElement;
import com.alibaba.idst.nls.dm.common.io.ParamItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

/**
 * @author niannian.ynn
 * 核心数据结构
 */
@Data
public class DialogState {
    public static final String MULTI_INTENT_STATES_RESULT_KEY = "multi_intents_executed_states_result";
    private static final Gson GSON = new Gson();
    private final String sessionId;
    /**
     * Domain Ontology存在state中方便后面的Function访问
     */
    private final DomainOntology domainOntology;
    /**
     * 存储每一轮的输入与输出
     */
    private final List<DialogTurn> dialogTurnList = new ArrayList<>();
    /**
     * 所有的任务都放在这个list中，当某个任务被激活后该Task对象被移到List的头部(index=0)
     */
    private final List<DialogTask> tasks = new ArrayList<>();
    /**
     * 全局flag（0 - 运行正常,1 - 任务执行失败，2 - 有新插入task需要执行）
     */
    private DialogStatus status = DialogStatus.NORMAL;
    /**
     * session级别的数据存储区
     */
    private ConcurrentMap<String, String> sessionContext = new ConcurrentHashMap<>();
    /**
     * nlg session level nlg resource cache
     */
    private Cache cache = new Cache();
    /**
     * 多函数并发运行模式需要用到
     */
    private Integer id;
    public DialogState(String sessionId, DomainOntology domainOntology) {
        this.sessionId = sessionId;
        this.domainOntology = domainOntology;
        this.id = 0;
    }

    /**
     * 获取当前激活的任务
     */
    public DialogTask getActiveTask() {
        if (tasks.size() > 0) {
            return tasks.get(0);
        }
        return null;
    }

    public String getAppKey() {
        if (dialogTurnList.size() > 0) {
            if (dialogTurnList.get(dialogTurnList.size() - 1).getDmRequest() != null) {
                return dialogTurnList.get(dialogTurnList.size() - 1).getDmRequest().getAppKey();
            }
        }
        return null;
    }

    /**
     * get current dialog turn information
     *
     * @return
     */
    public DialogTurn getCurrentTurn() {
        if (dialogTurnList.size() > 0) {
            return dialogTurnList.get(dialogTurnList.size() - 1);
        }
        return null;
    }

    public DialogTurn getLastTurn() {
        if (dialogTurnList.size() > 1) {
            return dialogTurnList.get(dialogTurnList.size() - 2);
        }
        return null;
    }

    /**
     * Add dialogTuen
     */
    public DialogTurn newDialogTurn(DialogRequest request) {
        DialogTurn turn = new DialogTurn();
        turn.setDmRequest(request);
        turn.setDmResponse(DialogResultElement.builder().build());
        dialogTurnList.add(turn);
        return turn;
    }

    /**
     * 检查是否处于debug模式，如果是则保存当前State到response中
     */
    public void dumpStateToResponse() {
        DialogTurn turn = getCurrentTurn();
        if (turn != null) {
            // 判断是否处于debug模式
            if (turn.getDmRequest() != null && turn.getDmResponse() != null) {
                DebugModeEnum mode = turn.getDmRequest().getDebugMode();
                if (mode != null) {
                    List<ParamItem> itemList = Objects.isNull(turn.getDmResponse().getActionParams()) ? new ArrayList<>()
                        : turn.getDmResponse().getActionParams();

                    // 先删除所有的debug信息
                    for (DebugModeEnum m : DebugModeEnum.values()) {
                        for (int i = 0; i < itemList.size(); i++) {
                            if (m.name().equalsIgnoreCase(itemList.get(i).getValue())) {
                                itemList.remove(i);
                                break;
                            }
                        }
                    }

                    // 序列化当前的state并保存
                    ParamItem debugItem = new ParamItem();
                    debugItem.setName(mode.name());
                    debugItem.setValue(GSON.toJson(this));
                    itemList.add(debugItem);
                    turn.getDmResponse().setActionParams(itemList);
                }
            }
        }
    }

    public DialogState cloneState() {
        String str = GSON.toJson(this);
        return GSON.fromJson(str, this.getClass());
    }

    public void saveMultiIntentsResult(List<DialogState> states) {
        String str = GSON.toJson(states);
        sessionContext.put(MULTI_INTENT_STATES_RESULT_KEY, str);
    }

    public List<DialogState> fetchMultiIntentsResult() {
        String str = sessionContext.get(MULTI_INTENT_STATES_RESULT_KEY);
        if (str != null) {
            try {
                Type listType = new TypeToken<ArrayList<DialogState>>() {}.getType();
                return GSON.fromJson(str, listType);
            } catch (Exception e) {

            }
        }
        return null;
    }

    public static enum DialogStatus {
        // 需要再次调用XML Engine
        SHOULD_RECALL,
        // 正常状态
        NORMAL
    }
}
