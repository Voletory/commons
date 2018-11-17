package com.lz.components.common.validation;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lz.components.common.log.holder.BizLoggerHolder;

/**
 * 验证注解处理
 * 
 * @author fuli
 * @version 1.0
 * @date 2017-12-15 13:35
 */
@Aspect
@Order(1)
@Component("validationAspect")
public class ValidationAspect implements BizLoggerHolder {
	@Pointcut("@annotation(com.lz.components.common.validation.RequireValidate)")
	public void handleValidation() {
	}
	@Around("handleValidation()")
	public Object handleBizException(ProceedingJoinPoint pJoinPoint) throws Throwable {
		Object[] args = pJoinPoint.getArgs();
		Method method = ((MethodSignature) pJoinPoint.getSignature()).getMethod();
		ParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();
		String[] argsName = pnd.getParameterNames(method);
		if (args.length > 0) {
			RequireValidate requireValidate = method.getAnnotation(RequireValidate.class);
			String[] paramNames = requireValidate.paramNames();
			if (paramNames.length > 0) {
				OUTTER: for (int i = 0; i < paramNames.length; i++) {
					for (int j = 0; j < argsName.length; j++) {
						if (argsName[j].equals(paramNames[i])) {
							ParamsValidator.validate(args[j]);
							continue OUTTER;
						}
					}
				}
			} else {
				// 默认检查第一个参数
				ParamsValidator.validate(args[0]);
			}
		}
		try {
			return pJoinPoint.proceed(args);
		} catch (Exception e) {
			throw e;
		}
	}
}
