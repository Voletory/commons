package commons.fun;

@FunctionalInterface
public interface FunCondition<T, R> {
	R apply(Supplier<Boolean> pred, Function<T, R> fun, T t) throws Exception;

	default FunCondition<T, R> Elseif(Supplier<Boolean> elsePred, Function<T, R> funElse, T tElse) throws Exception {
		return (ifPred, c, t) -> ifPred.get() ? c.apply(t) : apply(elsePred, funElse, tElse);
	}
	default FunCondition<T, R> Elseif(boolean elsePred, Function<T, R> funElse, T tElse) throws Exception {
		return (ifPred, c, t) -> ifPred.get() ? c.apply(t) : apply(() -> elsePred, funElse, tElse);
	}
	default FunCondition<T, R> Else(Function<T, R> funElse, T tElse) throws Exception {
		return (ifPred, f, t) -> {
			R result = apply(ifPred, f, t);
			return result == null ? funElse.apply(tElse) : result;
		};
	}
	default FunCondition<T, R> Finally(Function<T, R> funFinally, T tFinally) throws Exception {
		return (pred, r, t) -> {
			funFinally.apply(tFinally);
			return apply(pred, r, t);
		};
	}
}
