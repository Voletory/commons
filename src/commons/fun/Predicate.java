package commons.fun;

import java.util.Objects;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-27 10:10
 * @param <T>
 */
@FunctionalInterface
public interface Predicate<T> {
	boolean test(T t) throws Exception;

	/**
	 * 转换成java.util.function.Predicate<br/>
	 * 异常返回false
	 * 
	 * @return
	 */
	default java.util.function.Predicate<T> convert() {
		return (t) -> {
			try {
				return test(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		};
	}

	default Predicate<T> and(Predicate<? super T> other) throws Exception {
		Objects.requireNonNull(other);
		return (t) -> test(t) && other.test(t);
	}

	default Predicate<T> negate() throws Exception {
		return (t) -> !test(t);
	}

	default Predicate<T> or(Predicate<? super T> other) throws Exception {
		Objects.requireNonNull(other);
		return (t) -> test(t) || other.test(t);
	}

	static <T> Predicate<T> isEqual(Object targetRef) {
		return (null == targetRef) ? Objects::isNull : object -> targetRef.equals(object);
	}
}
