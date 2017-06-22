package commons.lang;

import java.math.BigDecimal;

/**
 * 数学计算工具
 * 
 * @author bailey_fu
 * @data 2011-9-28
 * @version 1.0
 * @Description
 */
public final class Math {

	/**
	 * 小数值阶乘(递归算法)
	 * 
	 * @param number
	 *            (less than 32)
	 * @return
	 */
	public static long lowFactorial(int number) {
		if (number < 1 || number > 31) {
			return 0;
		} else if (number == 1) {
			return number;
		} else {
			return number * lowFactorial(number - 1);
		}
	}

	/**
	 * 大数值阶乘
	 * 
	 * @param power
	 *            (1Epower ; less than 5)
	 * @return
	 */
	public static BigDecimal bigFactorial(int power) {
		if (power < 1 || power > 5) {
			return null;
		}
		BigDecimal b = new BigDecimal(0);
		int max = 1;
		for (int step = 1; step <= power; step++) {
			max *= 10;
			BigDecimal big = new BigDecimal(1);

			for (int i = 1; i <= max; i++) {
				big = big.multiply(BigDecimal.valueOf(i));
			}
			b = b.add(big);
		}
		return b;
	}

	/**
	 * 组合(小数值)
	 * 
	 * @param n
	 *            (less than 32)
	 * @param r
	 *            (less than n)
	 * @return
	 */
	public static long lowCombination(int n, int r) {
		if (n < 1 || r < 1 || n < r) {
			return 0;
		} else if (n == r) {
			return 1;
		}
		return lowFactorial(n) / (lowFactorial(r) * lowFactorial(n - r));
	}

	/**
	 * 排列(小数值)
	 * 
	 * @param n
	 *            (less than 32)
	 * @param r
	 *            (less than n)
	 * @return
	 */
	public static long lowArrangement(int n, int r) {
		if (n < 1 || r < 1 || n < r) {
			return 0;
		} else if (n == r) {
			return lowFactorial(n);
		}
		return lowFactorial(n) / lowFactorial(n - r);
	}
}
