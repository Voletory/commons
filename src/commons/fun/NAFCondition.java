package commons.fun;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-04-05 16:34
 */
@FunctionalInterface
public interface NAFCondition {
	void apply(Supplier<Boolean> pred, NAFunction naf) throws Exception;

	default NAFCondition Elseif(Supplier<Boolean> elsePred, NAFunction nafElse) throws Exception {
		return (ifPred, naf) -> {
			if (ifPred.get()) {
				naf.apply();
			} else {
				apply(elsePred, nafElse);
			}
		};
	}

	default NAFCondition Elseif(boolean elsePred, NAFunction nafElse) throws Exception {
		return (ifPred, naf) -> {
			if (ifPred.get()) {
				naf.apply();
			} else {
				apply(() -> elsePred, nafElse);
			}
		};
	}

	default NAFCondition Else(NAFunction nafElse) throws Exception {
		return (ifPred, naf) -> {
			apply(ifPred, naf);
			if (!ifPred.get()) {
				nafElse.apply();
			}
		};
	}

	default NAFCondition Finally(NAFunction nafFinally) throws Exception {
		return (pred, naf) -> {
			apply(pred, naf);
			nafFinally.apply();
		};
	}
}
