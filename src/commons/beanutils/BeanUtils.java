package commons.beanutils;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-06-13 16:53
 */
public class BeanUtils {

	public static String dump(Object bean) {
		return dump(bean, StringUtils.EMPTY, StringUtils.EMPTY);
	}

	public static String dump(Object bean, String pairSeparator) {
		return dump(bean, pairSeparator, StringUtils.EMPTY);
	}

	public static String dump(Object bean, String pairSeparator, String separator) {
		if (bean == null) {
			return null;
		}
		if (bean instanceof String || bean instanceof Number) {
			return bean.toString();
		} else if (bean instanceof Date) {
			return String.valueOf(((Date) bean).getTime());
		} else {
			try {
				StringBuffer temp = new StringBuffer();
				Map<String, Object> properties = ReflectionUtils.getProperties(bean);
				for (String field : properties.keySet()) {
					temp.append(field).append(pairSeparator).append(properties.get(field)).append(separator);
				}
				return temp.length() > 0 ? temp.substring(0, temp.length() - separator.length()) : StringUtils.EMPTY;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
