package commons.fun;

/**
 * 单条件断言
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 13:32
 * @param <T>
 */
@FunctionalInterface
public interface Condition<R> {
	R apply(Supplier<Boolean> pred, R r) throws Exception;

	default Condition<R> Elseif(Supplier<Boolean> elsePred, R rElse) throws Exception {
		return (ifPred, r) -> ifPred.get() ? r : apply(elsePred, rElse);
	}

	default Condition<R> Elseif(boolean elsePred, R rElse) throws Exception {
		return (ifPred, r) -> ifPred.get() ? r : apply(() -> elsePred, rElse);
	}

	default Condition<R> Else(R rElse) throws Exception {
		return (pred, r) -> {
			R result = apply(pred, r);
			return result == null ? rElse : result;
		};
	}

	default Condition<R> Else(R rElse, R condOfMiss) throws Exception {
		return (pred, r) -> {
			R result = apply(pred, r);
			return result.equals(condOfMiss) ? rElse : result;
		};
	}
}
