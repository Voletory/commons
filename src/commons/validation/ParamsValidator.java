package commons.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;


/**
 * 请求参数验证器
 * 
 * @author fuli
 * @date 2018年6月5日
 * @version 1.0.0
 */
public class ParamsValidator {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 验证请求参数
	 * 
	 * @param target
	 */
	public static void validate(Object target){
		Set<ConstraintViolation<Object>> set = validator.validate(target);
		if (set != null && set.size() > 0) {
			for (ConstraintViolation<Object> cv : set) {
				if (StringUtils.isNotBlank(cv.getMessage())) {
					throw new RuntimeException("请求参数异常:"+cv.getMessage());
				}
			}
		}
	}
}
