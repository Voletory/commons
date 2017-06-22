package commons.fun;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-27 10:30
 * @param <T>
 */
@FunctionalInterface
public interface Supplier<T> {
	T get()throws Exception;
}
