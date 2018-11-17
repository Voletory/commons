/**
 * X
 * Copyright (c) 2014-2014 X company,Inc.All Rights Reserved.
 */
package commons.beanutils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;

/**
 * 对象属性复制工具类
 * 
 * @author bailey_fu
 * @data 2014-2-17
 * @version $Id: BeanCopierUtils.java, v 0.1 2014-2-17 下午04:46:18 bailey_fu Exp
 *          $
 * @Description
 */
public class BeanCopierUtils {
	static LoggerAdapter LOGGER=LoggerAdapterFactory.getLogger(LogType.SYS_COMMON);
	private static final Map<String, BeanCopier> BEANCOPIER_MAP = new HashMap<String, BeanCopier>();
	private BeanCopierUtils() {
	}

	/**
	 * copy properties
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {
		if (source == null || target == null) {
			return;
		}
		String beanKey = generateKey(source.getClass(), target.getClass());
		BeanCopier copier = BEANCOPIER_MAP.get(beanKey);
		if (copier == null) {
			copier = BeanCopier.create(source.getClass(), target.getClass(), false);
			BEANCOPIER_MAP.put(beanKey, copier);
		}
		copier.copy(source, target, null);
	}

	/**
	 * 根据两个class名组装成key
	 * 
	 * @param class1
	 * @param class2
	 * @return
	 */
	private static String generateKey(Class<?> class1, Class<?> class2) {
		return new StringBuilder().append(class1.toString()).append(class2.toString()).toString();
	}

	/**
	 * 拷贝对象
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws BusinessRuntimeException
	 */
	public static <T> T copyOne2One(Object source, Class<T> target) throws LzRuntimeException {
		if (source == null || target == null) {
			return null;
		}
		T instance = null;
		try {
			instance = target.newInstance();
			copyProperties(source, instance);
		} catch (Exception e) {
			LOGGER.error("对象拷贝异常:source=[{}] target=[{}]", source, target);
			throw new LzRuntimeException(ExceptionCode.FAILED, e);
		}
		return instance;
	}

	/**
	 * 拷贝集合
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws BusinessRuntimeException
	 */
	public static <T> List<T> copyList2List(List<?> source, Class<T> target) throws LzRuntimeException {
		if (source == null || target == null) {
			return null;
		}
		if (source.isEmpty()) {
			return new ArrayList<T>();
		}
		List<T> result = new ArrayList<T>();
		for (Object obj : source) {
			result.add(copyOne2One(obj, target));
		}
		return result;
	}
	
	public static <T> T[] copyArray2Array(Object[] source, Class<T> target) throws LzRuntimeException {
		if (source == null || source.length == 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(target, source.length);
		for (int i = 0; i < source.length; i++) {
			result[i] = copyOne2One(source[i], target);
		}
		return result;
	}

	/**
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyAttribute(Object source, Object target) {
		if (source == null || target == null)
			return;
		String key = new StringBuilder().append(source.getClass().toString()).append(target.getClass().toString()).toString();
		BeanCopier beanCopier = BEANCOPIER_MAP.get(key);
		if (beanCopier == null) {
			beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
			BEANCOPIER_MAP.put(key, beanCopier);
		}
		beanCopier.copy(source, target, null);
	}
	public static void copyParentAttribute(Object source, Object target) throws RuntimeException{
		if (source == null || target == null)
			return;
		try{
			Class<?> clazz=target.getClass();
			while (clazz != Object.class) {
				clazz = clazz.getSuperclass();
				for(Field f:clazz.getDeclaredFields()){
					f.setAccessible(true);
					f.set(target, f.get(source));
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
