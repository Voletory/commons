package commons.util;

import java.util.Random;

/**
 * 随机数工具类
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.0
 * @description
 */
public final class RandomUtils {

	private static Random random = new java.util.Random();

	private RandomUtils() {
	}

	public static int getRandomInt() {
		return random.nextInt();
	}

	public static Long getRandomLong() {
		return new Long(random.nextLong());
	}

	public static int getPositiveInt(int src) {
		if (src > 0)
			return random.nextInt(src);
		else
			return src;
	}

	/**
	 * 随机产生一个长度为length且大小在range范围内的数字
	 * 
	 * @param range
	 * @param length
	 * @return
	 */
	public static int getLimitRandom(int range, int length) {
		String num = new Integer(random.nextInt(range)).toString();
		if (num.length() < length) {
			while (num.length() != length) {
				num = num + "0";
			}
		} else if (num.length() > length) {
			while (num.length() != length) {
				num = num.substring(0, num.length() - 1);
			}
		}
		return Integer.parseInt(num);
	}

	/**
	 * 随机产生一个长度为length的数字
	 * 
	 * @param length
	 * @return
	 */
	public static int getLimitRandom(int length) {
		String num = new Integer(Math.abs(random.nextInt())).toString();
		if (num.length() < length) {
			while (num.length() != length) {
				num = num + random.nextInt(10);
			}

		} else if (num.length() > length) {
			num = num.substring(0, length);
		}
		return Integer.parseInt(num);
	}

	public static int getLimitInt(int src, int limitMin, int limitMax) {
		if (src <= limitMin)
			return limitMin;
		if (src >= limitMax)
			return limitMax;
		int i = random.nextInt(src);
		while (i < limitMin || i > limitMax) {
			i = random.nextInt(src);
		}
		return i;
	}

	public static int getLimitInt(int limitMin, int limitMax) {
		int result = random.nextInt(limitMax);
		while (result < limitMin)
			result = random.nextInt(limitMax);
		return result;
	}

	public static int nextInt(int number) {
		return random.nextInt(number);
	}
}
