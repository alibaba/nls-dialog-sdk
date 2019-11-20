package com.alibaba.idst.nls.dm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jianghaitao
 * @date 2018/10/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DialogFunc {
    /**
     * description about this function
     *
     * @return
     */
    String desc() default "dialog function";

    /**
     * chinese name
     */
    String cnName() default "";

    /**
     * function scope, possible value are "action", "condition", "all"
     */
    String scope() default "all";

    /**
     * how to serialize function, possible value "string", "json"
     *
     * @return
     */
    String serializeTo() default "string";
}
