package commons.util;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang3.StringUtils;

/**
 * 数字格式化类
 * 
 * @author bailey.fu
 * @date Feb 5, 2010
 * @version 1.0
 * @description
 */
public final class NumberFormat {

	@SuppressWarnings("unchecked")
	private static Map<String, ThreadLocal<DecimalFormat>> dfMap = new LRUMap();
	private static DecimalFormat getDecimalFormat(String format) {
		if (StringUtils.isBlank(format)) {
			return null;
		}
		try {
			ThreadLocal<DecimalFormat> decimalFormat = dfMap.get(format);
			if (decimalFormat == null) {
				decimalFormat = new ThreadLocal<DecimalFormat>() {
					@Override
					protected DecimalFormat initialValue() {
						return new DecimalFormat(format);
					}
				};
				dfMap.put(format, decimalFormat);
			}
			return decimalFormat.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DecimalFormat(format);
	}

	/**
	 * 格式化数字
	 * 
	 * @param number
	 *            待格式化的数字
	 * @param format
	 *            格式
	 * @return
	 */
	public static String format(String number, String format) {
		String result = null;
		DecimalFormat decimalFormat = getDecimalFormat(format);
		try {
			result = decimalFormat.format(Double.valueOf(number));
		} catch (Exception e) {
		}
		return result;
	}

}
