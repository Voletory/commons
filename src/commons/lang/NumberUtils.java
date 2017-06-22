package commons.lang;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author bailey_fu
 * @data 2013-12-9
 * @version 1.0
 * @Description
 */
public final class NumberUtils {

	private static byte defaultByte = 0;
	private static int defaultInt = 0;
	private static long defaultLong = 0l;
	private static double defaultDouble = 0d;

	public static byte defaultByte(String src, byte limitMin, byte limitMax) {
		byte result = defaultByte;
		if (!StringUtils.isBlank(src)) {
			try {
				result = Byte.valueOf(src.toString().trim());
			} catch (Exception e) {
			}
		}
		return (result < limitMin) ? limitMin : ((result > limitMax) ? limitMax : result);
	}

	public static int defaultInt(String src, int limitMin, int limitMax) {
		int result = defaultInt;
		if (!StringUtils.isBlank(src)) {
			try {
				result = Integer.valueOf(src.toString().trim());
			} catch (Exception e) {
			}
		}
		return (result < limitMin) ? limitMin : ((result > limitMax) ? limitMax : result);
	}

	public static long defaultLong(String src, long limitMin, long limitMax) {
		long result = defaultLong;
		if (!StringUtils.isBlank(src)) {
			try {
				result = Long.valueOf(src.toString().trim());
			} catch (Exception e) {
			}
		}
		return (result < limitMin) ? limitMin : ((result > limitMax) ? limitMax : result);
	}

	public static double defaultDouble(String src, double limitMin, double limitMax) {
		double result = defaultDouble;
		if (!StringUtils.isBlank(src)) {
			try {
				result = Double.valueOf(src.toString().trim());
			} catch (Exception e) {
			}
		}
		return (result < limitMin) ? limitMin : ((result > limitMax) ? limitMax : result);
	}

	public static BigDecimal number2BigDecimal(Number number) {
		return number instanceof BigDecimal ? (BigDecimal) number : BigDecimal.valueOf(number.doubleValue());
	}
}
