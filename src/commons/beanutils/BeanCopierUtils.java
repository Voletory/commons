/**
 * X
 * Copyright (c) 2014-2014 X company,Inc.All Rights Reserved.
 */
package commons.beanutils;

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
	private static final Map<String, BeanCopier> BEANCOPIER_MAP = new HashMap<String, BeanCopier>();

	private BeanCopierUtils() {
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
}
