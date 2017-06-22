package commons.fun;

import java.util.Objects;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-27 10:10
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface Function<T, R> {
	R apply(T t) throws Exception;

	/**
	 * 返回的Arithmetic的apply方法参数将被忽略
	 * 
	 * @param t
	 * @return
	 */
	default Arithmetic apply2Arith(T t) throws Exception {
		return (v1, v2) -> (Number) apply(t);
	}

	/**
	 * 转换成java.util.function.Function<br/>
	 * 异常则返回Null
	 * 
	 * @return
	 */
	default java.util.function.Function<T, R> convert() {
		return (t) -> {
			try {
				return apply(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		};
	}

	default <V> Function<V, R> compose(Function<? super V, ? extends T> before) throws Exception {
		Objects.requireNonNull(before);
		return (V v) -> apply(before.apply(v));
	}

	default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) throws Exception {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
}
