package commons.fun;

import java.util.Objects;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-27 10:30
 * @param <T>
 */
@FunctionalInterface
public interface Consumer<T> {
	void accept(T t) throws Exception;

	/**
	 * 转换成java.util.function.Consumer<br/>
	 * 不能传导异常
	 * 
	 * @return
	 */
	default java.util.function.Consumer<T> convert() {
		return (t) -> {
			try {
				accept(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

	default Consumer<T> andThen(Consumer<? super T> after) throws Exception {
		Objects.requireNonNull(after);
		return (T t) -> {
			accept(t);
			after.accept(t);
		};
	}
}
