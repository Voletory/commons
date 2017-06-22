package commons.fun;

import java.util.Objects;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-27 10:30
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface BiPredicate<T, U> {
	boolean test(T t, U u) throws Exception;

	/**
	 * 转换成java.util.function.BiPredicate<br/>
	 * 异常返回false
	 * 
	 * @return
	 */
	default java.util.function.BiPredicate<T, U> convert() {
		return (t, u) -> {
			try {
				return test(t, u);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		};
	}
	default BiPredicate<T, U> and(BiPredicate<? super T, ? super U> other) throws Exception {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) && other.test(t, u);
	}
	default BiPredicate<T, U> negate() throws Exception {
		return (T t, U u) -> !test(t, u);
	}
	default BiPredicate<T, U> or(BiPredicate<? super T, ? super U> other) throws Exception {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) || other.test(t, u);
	}
}
