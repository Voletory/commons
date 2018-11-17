package com.lz.components.common.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;

import com.lz.components.common.code.ExceptionCode;
import com.lz.components.common.exception.LzRuntimeException;

/**
 * 请求参数验证器
 * 
 * @author fuli
 * @date 2018年6月5日
 * @version 1.0.0 Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public class ParamsValidator {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 验证请求参数
	 * 
	 * @param target
	 */
	public static void validate(Object target) throws LzRuntimeException{
		Set<ConstraintViolation<Object>> set = validator.validate(target);
		if (set != null && set.size() > 0) {
			for (ConstraintViolation<Object> cv : set) {
				if (StringUtils.isNotBlank(cv.getMessage())) {
					throw new LzRuntimeException(ExceptionCode.PARAM_ERROR, cv.getMessage());
				}
			}
		}
	}
}
