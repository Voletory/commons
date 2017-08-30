package commons.funs;

import org.apache.commons.lang3.StringUtils;

import commons.fun.BiPredicate;
import commons.fun.Predicate;

/**
 * 常用断言
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-23 14:03
 */
public final class Predicates {

	public static Predicate<Object> True = (obj) -> true;
	public static Predicate<Object> False = (obj) -> false;

	public static Predicate<Object> noNull = (obj) -> obj != null;
	public static <T> Predicate<T> noNull() {
		return (obj) -> obj != null;
	}
	public static Predicate<String> noEmpty = (str) -> StringUtils.isNotEmpty(str);

	public static BiPredicate<Object, Object> bothNoNull = (t, u) -> t != null && u != null;
	public static <T, U> BiPredicate<T, U> bothNoNull() {
		return (tObj, uObj) -> tObj != null && uObj != null;
	}
	public static BiPredicate<String, String> bothNoEmpty = (t, u) -> StringUtils.isNotEmpty(t) && StringUtils.isNotEmpty(u);
	public static BiPredicate<Object, Object> equal = (t, u) -> bothNoNull.test(t, u) && t.equals(u);
	public static <T> BiPredicate<T, T> equal() {
		return (t1, t2) -> bothNoNull.test(t1, t2) && t1.equals(t2);
	}
	/** 按double对比 */
	public static BiPredicate<Number, Number> equalNumber = (t, u) -> bothNoNull.test(t, u) && t.doubleValue() == u.doubleValue();
	
	public static BiPredicate<Object, Class<?>> instanceOf = (t, c) -> bothNoNull.test(t, c) && c.isAssignableFrom(t.getClass());
	
	public static void main(String[] args) throws Exception{
		boolean a=bothNoNull().test(null,"");
		System.out.println(a);
		a=noNull.test("");
		System.out.println(equal().test("A", "A"));
	}
}
