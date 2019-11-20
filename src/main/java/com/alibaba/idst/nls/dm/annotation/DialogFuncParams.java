package com.alibaba.idst.nls.dm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hanpu.mwx@alibaba-inc.com
 * @date 2019-01-31
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DialogFuncParams {
    DialogFuncParam[] value();
}
