package com.alibaba.idst.nls.dm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hanpu.mwx@alibaba-inc.com
 * @date 2019-01-31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DialogFuncParams.class)
public @interface DialogFuncParam {
    static final String CANDI_DOMAIN = "{schema.domains}";
    static final String CANDI_INTENT = "{schema.intents}";
    static final String CANDI_SLOT = "{schema.slots}";
    static final String CANDI_NLG = "{special.nlg}";

    /**
     * parameter name
     */
    String name() default "param";

    /**
     * description about this param
     */
    String desc() default "";

    /**
     * comment about this param
     */
    String comment() default "";

    /**
     * function param type, possible value "string", "string_array"
     */
    String type() default "string";

    /**
     * default value of this param, if missing, then function caller must set a value for it
     */
    String defaultValue() default "";

    /**
     * it's this param required? if set, will ignore defaultValue
     */
    boolean required() default false;

    /**
     * possible value for this param, if missing, no limit to value
     *
     * SPECIAL VALUES: - DialogFuncParam.CANDI_DOMAIN : all validate domain name under current app, note that it's a
     * constant variable define above, not an string with content "DialogFuncParam.CANDI_DOMAIN"
     * 如果需要给这个参数指定一种KV结构的数据，则candiValues = ["optionsList"]。然后在 optionsList 里指定值，值格式见 optionsList 注释。
     */
    String[] candiValues() default {};

    /**
     * 向前端传选择框数据时才需要，其值格式为：
     * 
     * <pre>
     * ["{'value':0, 'label':'当前轮'}, "{'value':1, 'label':'上一轮'}"]
     * </pre>
     * 
     * 以上代码在前端其HTML为
     * 
     * <pre>
     * &lt;select ...&gt;
     * &lt;option value="0"&gt;当前轮&lt;/option&gt;
     * &lt;option value="1"&gt;上一轮&lt;/option&gt;
     * &lt;/select>
     * </pre>
     * 
     * @return
     */
    String[] optionsList() default {};

    /**
     * how to serialize function param, possible value "string", "json"
     *
     * @return
     */
    String serializeTo() default "string";
}
