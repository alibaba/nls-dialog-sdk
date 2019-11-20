package com.alibaba.idst.nls.dm.common;

import lombok.Getter;

/**
 * @author jianghaitao
 * @date 2019-05-21
 */
@Getter
public enum DialogResultCodeEnum {
    /**
     *
     */
    SUCCESS("success", 150000),
    /**
     *
     */
    SDM_INVALID_ARGS("invalid arguments", 150101),
    SDM_ENGINE_ERROR("sdm engine error", 150102),
    SDM_ENGINE_MISSING("sdm engine not exists", 150103),
    SDM_MISSING_CONF("missing conf", 150104),
    SDM_OPERATE_OSS_FAIL("operate oss failed", 150105),
    /**
     *
     */
    NLU_SERVICE_ERROR("nlu service error", 150201),
    NLU_RESULT_ERROR("nlu result error", 150202),
    NLU_SELECTION_ERROR("nlu selection error", 150203),
    NLU_PREPARE_ERROR("error occurred before call nlu service", 150204),
    NLU_UNSUPPORT_ENCODE("unsupport sdm encode", 150205),
    STREAM_NLU_RESULT_ERROR("stream nlu result error", 150208),
    /**
     *
     */
    FUNC_NOT_FOUND("func not found", 150301),
    DIALOG_TIMEOUT("dialog timeout", 150302),
    NO_DIALOG_RESULT("no dialog result", 150303),
    INSTANCE_CREATE_FAIL("instance create fail", 150304),
    CALL_FUNCTION_FAIL("call function failed", 150305),
    /**
     * sdm fn
     */
    FUNC_TIMEOUT_ERROR("function time out", 150401),
    FUNC_INNER_ERROR("function inner error", 150402),
    FUNC_NO_RESULT_ERROR("function has no result", 150403),
    /**
     * Timeout
     */
    NLU_CONNECTION_TIMEOUT("nlu connection timeout", 150501),
    FUNC_CONNECTION_TIMEOUT("func connection timeout", 150502),
    QAS_CONNECTION_TIMEOUT("qas connection timeout", 150503),
    /**
     *
     */
    FAILED("failed", 159901);

    /**
     *
     */
    private String description;

    /**
     *
     */
    private int code;

    DialogResultCodeEnum(String description, int code) {
        this.description = description;
        this.code = code;
    }
}
