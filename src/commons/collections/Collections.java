package commons.collections;

import java.util.Collection;

/**
 * Collection工具类
 * 
 * @author bailey.fu
 * @date May 9, 2010
 * @version 1.0
 * @description
 */
public final class Collections {

	/** 获取指定返回内容 */
	public static Object[] getSubElement(Collection<?> source, int startPoint, int size) {
		startPoint = startPoint <= 0 ? 0 : startPoint;
		int endPoint = startPoint + size;
		endPoint = endPoint > source.size() ? source.size() : endPoint;
		return getArray(source.toArray(), startPoint, endPoint);
	}

	/** 获取指定返回内容(反向) */
	public static Object[] getSubElementByReroute(Collection<?> source, int startPoint, int size) {
		return getSubElementByReroute(source.toArray(), startPoint, size);
	}

	/** 获取指定返回内容(反向) */
	public static Object[] getSubElementByReroute(Object[] source, int startPoint, int size) {
		Object[] result = null;
		startPoint = startPoint <= 0 ? 0 : startPoint;
		int endPoint = startPoint + size;
		endPoint = endPoint > source.length ? source.length : endPoint;
		int length = source.length - 1;
		startPoint = length - startPoint;
		endPoint = length - endPoint;

		result = new Object[startPoint - endPoint];
		if (result.length > 0) {
			int temp = 0;
			for (; startPoint > endPoint; startPoint--, temp++) {
				result[temp] = source[startPoint];
			}
		}
		return result;
	}

	public static Object[] getArray(Collection<?> source, int startPoint, int endPoint) {
		return getArray(source.toArray(), startPoint, endPoint);
	}

	public static Object[] getArray(Object[] source, int startPoint, int endPoint) {
		endPoint = endPoint > source.length ? source.length : endPoint;
		Object[] result = new Object[endPoint - startPoint];
		int index = 0;
		for (; startPoint < endPoint; startPoint++, index++) {
			result[index] = source[startPoint];
		}
		return result;
	}
}
