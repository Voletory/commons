package com.lz.components.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Biz请求参数检查
 * 
 * @author fuli
 * @date 2018年6月5日
 * @version 1.0.0 Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequireValidate {
	/**
	 * 需要检查的参数名;默认检查第一个参数
	 * 
	 * @return
	 */
	String[] paramNames() default {};
}
