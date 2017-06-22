package commons.fun;

import java.util.Objects;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-27 10:30
 * @param <T>
 * @param <U>
 * @param <R>
 */
@FunctionalInterface
public interface BiFunction<T, U, R> {
	R apply(T t, U u) throws Exception;

	/**
	 * 转换成java.util.function.BiFunction<br/>
	 * 异常则返回Null
	 * 
	 * @return
	 */
	default java.util.function.BiFunction<T, U, R> convert() {
		return (t, u) -> {
			try {
				return apply(t, u);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		};
	}
	default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) throws Exception {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}
}
