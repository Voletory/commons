package commons.fun;

import commons.funs.Functions;

/**
 * 算术运算
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-24 16:48
 */
@FunctionalInterface
public interface Arithmetic {

	Number apply(Number t, Number u) throws Exception;

	/**
	 * 返回的Function的apply方法参数将被忽略
	 * 
	 * @param t
	 * @param u
	 * @return
	 * @throws Exception
	 */
	default Function<Number, Number> apply2Fun(Number t, Number u) throws Exception {
		return (x) -> apply(t, u);
	}
	default Arithmetic add(Number target) throws Exception {
		return (t, u) -> Functions.addition.apply(apply(t, u), target);
	}
	default Arithmetic subtract(Number target) throws Exception {
		return (t, u) -> Functions.subtraction.apply(apply(t, u), target);
	}
	default Arithmetic multiply(Number target) throws Exception {
		return (t, u) -> Functions.multiplication.apply(apply(t, u), target);
	}
	default Arithmetic divide(Number target) throws Exception {
		return (t, u) -> Functions.division.apply(apply(t, u), target);
	}
}
