package commons.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字格式化类
 * 
 * @author bailey.fu
 * @date Feb 5, 2010
 * @version 1.0
 * @description
 */
public final class NumberFormat {

	private static Map<String, DecimalFormat> dfMap = new HashMap<String, DecimalFormat>();

	private static DecimalFormat getDecimalFormat(String format) {
		if (format == null || format.trim().equals(""))
			return null;
		DecimalFormat decimalFormat = dfMap.get(format);
		if (decimalFormat == null) {
			decimalFormat = new DecimalFormat(format);
			dfMap.put(format, decimalFormat);
		}
		return decimalFormat;
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
