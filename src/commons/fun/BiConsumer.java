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
public interface BiConsumer<T, U> {
	void accept(T t, U u) throws Exception;

	/**
	 * 转换成java.util.function.BiConsumer<br/>
	 * 不能传导异常
	 * 
	 * @return
	 */
	default java.util.function.BiConsumer<T, U> convert() {
		return (t, u) -> {
			try {
				accept(t, u);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
	default BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) throws Exception {
		Objects.requireNonNull(after);
		return (l, r) -> {
			accept(l, r);
			after.accept(l, r);
		};
	}
}
