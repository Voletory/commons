package commons.funs;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import commons.fun.Arithmetic;
import commons.fun.BiFunction;
import commons.fun.Function;
import commons.lang.NumberUtils;

/**
 * 常用方法
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-24 13:32
 */
public class Functions {
	public static Function<Object, Object> identity = t -> t;
	public static <T>Function<T,T> identity(){
		return t->t;
	}

	public static Function<String, String> emptyIfNull = (t) -> t == null ? StringUtils.EMPTY : t;

	public static BiFunction<Number, Number, Number> minBy=(a, b) -> a.doubleValue() < b.doubleValue() ? a : b;

	public static <T> BiFunction<T, T, T> minBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
	}

	public static BiFunction<Number, Number, Number> maxBy = (a, b) -> a.doubleValue() > b.doubleValue() ? a : b;

	public static <T> BiFunction<T, T, T> maxBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
	}

	public static Arithmetic first = (t, u) -> t;
	public static Arithmetic second = (t, u) -> u;
	public static Arithmetic addition = (t, u) -> {
		Objects.requireNonNull(t);
		Objects.requireNonNull(u);
		return (t instanceof BigDecimal || u instanceof BigDecimal) ? NumberUtils.number2BigDecimal(t).add(NumberUtils.number2BigDecimal(u))
				: t.doubleValue() + u.doubleValue();
	};
	public static Arithmetic subtraction = (t, u) -> {
		Objects.requireNonNull(t);
		Objects.requireNonNull(u);
		return (t instanceof BigDecimal || u instanceof BigDecimal) ? NumberUtils.number2BigDecimal(t).subtract(NumberUtils.number2BigDecimal(u))
				: t.doubleValue() - u.doubleValue();
	};
	public static Arithmetic multiplication = (t, u) -> {
		Objects.requireNonNull(t);
		Objects.requireNonNull(u);
		return (t instanceof BigDecimal || u instanceof BigDecimal) ? NumberUtils.number2BigDecimal(t).multiply(NumberUtils.number2BigDecimal(u))
				: t.doubleValue() * u.doubleValue();
	};
	public static Arithmetic division = (t, u) -> {
		Objects.requireNonNull(t);
		Objects.requireNonNull(u);
		return (t instanceof BigDecimal || u instanceof BigDecimal) ? NumberUtils.number2BigDecimal(t).divide(NumberUtils.number2BigDecimal(u))
				: t.doubleValue() / u.doubleValue();
	};
}
