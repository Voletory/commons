package commons.fun;

@FunctionalInterface
public interface BiFunCondition<T, U, R> {
	R apply(Supplier<Boolean> pred, BiFunction<T, U, R> fun, T t, U u) throws Exception;

	default BiFunCondition<T, U, R> Elseif(Supplier<Boolean> elsePred, BiFunction<T, U, R> funElse, T tElse, U uElse) throws Exception {
		return (ifPred, bf, t, u) -> ifPred.get() ? bf.apply(t, u) : apply(elsePred, funElse, tElse, uElse);
	}
	default BiFunCondition<T, U, R> Elseif(boolean elsePred, BiFunction<T, U, R> funElse, T tElse, U uElse) throws Exception {
		return (ifPred, bf, t, u) -> ifPred.get() ? bf.apply(t, u) : apply(() -> elsePred, funElse, tElse, uElse);
	}
	default BiFunCondition<T, U, R> Else(BiFunction<T, U, R> funElse, T tElse, U uElse) throws Exception {
		return (ifPred, bf, t, u) -> {
			R result = apply(ifPred, bf, t, u);
			return result == null ? funElse.apply(tElse, uElse) : result;
		};
	}
	default BiFunCondition<T, U, R> Finally(BiFunction<T, U, R> funFinally, T tFinally, U uFinally) throws Exception {
		return (pred, r, t, u) -> {
			funFinally.apply(tFinally, uFinally);
			return apply(pred, r, t, u);
		};
	}
}