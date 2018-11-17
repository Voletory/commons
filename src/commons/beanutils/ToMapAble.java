package commons.beanutils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;


/**
 * Bean转Map<String,String>
 * 
 * @author fuli
 * @date 2018年6月12日
 * @version 1.0.0
 */
public interface ToMapAble {
	default Map<String, String> toStringMap(String...excludeFields){
		return toStringMap(false, excludeFields);
	}
	default Map<String, String> toStringMap(boolean ignoreBlankOrNull,String...excludeFields) {
		Map<String, String> map = new HashMap<>();
		try {
			Class<?> clazz = this.getClass();
			while (clazz != Object.class) {
				Field[] fields = clazz.getDeclaredFields();
				OUTTER: for (Field field : fields) {
					if (excludeFields != null && excludeFields.length > 0) {
						for (String excludeFile : excludeFields) {
							if (field.getName().equals(excludeFile)) {
								continue OUTTER;
							}
						}
					}
					field.setAccessible(true);
					if (field.getModifiers() < Modifier.STATIC) {
						Object value = field.get(this);
						String strValue = null;
						if (value == null) {
							strValue = StringUtils.EMPTY;
						} else if (value instanceof Date) {
							strValue = DateFormatUtils.format(((Date) value), "yyyy-MM-dd HH:mm:ss");
						}else{
							strValue=value.toString();
						}
						if (StringUtils.isBlank(strValue) && ignoreBlankOrNull) {
							continue OUTTER;
						}
						map.put(field.getName(), strValue);
					}
				}
				clazz = clazz.getSuperclass();
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}
}
