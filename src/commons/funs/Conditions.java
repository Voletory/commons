package commons.funs;

import commons.fun.BiFunCondition;
import commons.fun.CSMCondition;
import commons.fun.Condition;
import commons.fun.FunCondition;
import commons.fun.NAFCondition;
import commons.fun.SPLCondition;

/**
 * 条件判断
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 14:14
 */
public class Conditions {
	/**
	 * 未命中则返回Null
	 */
	public static Condition<Object> If = (s, r) -> s.get() ? r : null;
	/**
	 * 未命中则返回Null
	 */
	public static SPLCondition<Object> SPLIf = (s, r) -> s.get() ? r.get() : null;
	/**
	 * 未命中则什么都不做
	 */
	public static CSMCondition<Object> CSMIf = (s, c, t) -> {
		if (s.get()) {
			c.accept(t);
		}
	};
	/**
	 * 未命中则什么都不做
	 */
	public static NAFCondition NAFIf = (s, n) -> {
		if (s.get()) {
			n.apply();
		}
	};
	/**
	 * 未命中则返回Null
	 */
	public static FunCondition<Object, Object> FUNIf = (s, f, t) -> s.get() ? f.apply(t) : null;
	/**
	 * 未命中则返回Null
	 */
	public static BiFunCondition<Object, Object, Object> BiFUNIf = (s, f, t, u) -> s.get() ? f.apply(t, u) : null;
}
