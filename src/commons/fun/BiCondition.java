package commons.fun;

/**
 * 单条件断言
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 13:39
 */
@FunctionalInterface
public interface BiCondition<T> {
	T If();
}
