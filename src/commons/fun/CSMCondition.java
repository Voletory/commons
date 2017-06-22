package commons.fun;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 16:02
 */
@FunctionalInterface
public interface CSMCondition<T> {
	void apply(Supplier<Boolean> pred, Consumer<T> cons, T t) throws Exception;

	default CSMCondition<T> Elseif(Supplier<Boolean> elsePred, Consumer<T> consElse, T tElse) throws Exception {
		return (ifPred, c, t) -> {
			if (ifPred.get()) {
				c.accept(t);
			} else {
				apply(elsePred, consElse, tElse);
			}
		};
	}

	default CSMCondition<T> Elseif(boolean elsePred, Consumer<T> consElse, T tElse) throws Exception {
		return (ifPred, c, t) -> {
			if (ifPred.get()) {
				c.accept(t);
			} else {
				apply(() -> elsePred, consElse, tElse);
			}
		};
	}

	default CSMCondition<T> Else(Consumer<T> consElse, T tElse) throws Exception {
		return (ifPred, c, t) -> {
			apply(ifPred, c, t);
			if (!ifPred.get()) {
				consElse.accept(tElse);
			}
		};
	}

	default CSMCondition<T> Finally(Consumer<T> consFinally, T tFinally) throws Exception {
		return (pred, r, t) -> {
			apply(pred, r, t);
			consFinally.accept(tFinally);
		};
	}
}
