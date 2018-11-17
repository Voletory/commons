package commons.fun;

/**
 * 无参无返回
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 16:07
 */
@FunctionalInterface
public interface NAFunction {
	void apply() throws RuntimeException;
}
