package commons.funs;

import commons.fun.Supplier;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 14:49
 */
public class Suppliers {
	public static Supplier<Boolean> Null(Object obj) {
		return () -> obj == null;
	}

	public static Supplier<Boolean> noNull(Object obj) {
		return () -> obj != null;
	}

	public static Supplier<Boolean> equals(Number t, Number v) {
		return () -> Predicates.bothNoNull.test(t, v) ? t.doubleValue() == v.doubleValue() : false;
	}

	public static Supplier<Boolean> equals(Object t, Object v) {
		return () -> Predicates.bothNoNull.test(t, v) ? t.equals(v) : false;
	}
}
