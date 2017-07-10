package commons.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @update 2017-07-03 11:04
 * @version 2.0
 * @description
 */
public final class RandomUtils {
	/**
	 * 避免 Random 实例（包括 Math.random() 实例）被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一seed 导致的性能下降
	 * 更改为ThreadLocalRandom
	 */
	@Deprecated
	private static Random random = new java.util.Random();

	private RandomUtils() {
	}

	@Deprecated
	public static int getRandomInt() {
		return random.nextInt();
	}

	@Deprecated
	public static Long getRandomLong() {
		return random.nextLong();
	}

	public static int getPositiveInt(int src) {
		if (src > 0)
			return ThreadLocalRandom.current().nextInt(src);
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
		String num = String.valueOf(ThreadLocalRandom.current().nextInt(range));
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
		ThreadLocalRandom random = ThreadLocalRandom.current();
		String num = String.valueOf(Math.abs(random.nextInt()));
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
		ThreadLocalRandom random = ThreadLocalRandom.current();
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
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int result = random.nextInt(limitMax);
		while (result < limitMin)
			result = random.nextInt(limitMax);
		return result;
	}

	public static int nextInt(int number) {
		return ThreadLocalRandom.current().nextInt(number);
	}
}
