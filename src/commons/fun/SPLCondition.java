package commons.fun;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 15:47
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface SPLCondition<R> {
	R apply(Supplier<Boolean> pred, Supplier<R> sup) throws Exception;

	default SPLCondition<R> Elseif(Supplier<Boolean> elsePred, Supplier<R> supElse) throws Exception {
		return (ifPred, r) -> ifPred.get() ? r.get() : apply(elsePred, supElse);
	}

	default SPLCondition<R> Elseif(boolean elsePred, Supplier<R> supElse) throws Exception {
		return (ifPred, r) -> ifPred.get() ? r.get() : apply(() -> elsePred, supElse);
	}

	default SPLCondition<R> Else(Supplier<R> supElse) throws Exception {
		return (pred, r) -> {
			R result = apply(pred, r);
			return result == null ? supElse.get() : result;
		};
	}

	default SPLCondition<R> Else(R rElse, Supplier<R> condOfMiss) throws Exception {
		return (pred, r) -> {
			R result = apply(pred, r);
			return result.equals(condOfMiss.get()) ? rElse : result;
		};
	}
}
