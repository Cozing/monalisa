package com.cozing.monalisa.reinforce.annotation.inject;


import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc: 绑定资源ID，相当于findViewById();
 *
 * Created by wanghuquan on 2018/2/9.
 */


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    @IdRes int value();
}