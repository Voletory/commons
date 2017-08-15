package commons.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;

import commons.fun.NAFunction;

/**
 * 异步调用方式的封装
 * 
 * @author fuli
 * @version 1.0
 * @date 2017-08-15 14:14
 */
public final class Async {
	public static <T> CompletableFuture<T> runAsync(Supplier<T> supplier) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	public static <T> CompletableFuture<T> runAsync(commons.fun.Supplier<T> supplier) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
	}

	public static <T> CompletableFuture<T> runAsync(Supplier<T> supplier, Logger logger) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				logger.error("Async running error:", e);
			}
			return null;
		});
	}
	public static <T> CompletableFuture<T> runAsync(commons.fun.Supplier<T> supplier, Logger logger) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				logger.error("Async running error:", e);
			}
			return null;
		});
	}

	public static <T> CompletableFuture<T> runAsync(Supplier<T> supplier, Consumer<Exception> consumer) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				consumer.accept(e);
			}
			return null;
		});
	}
	public static <T> CompletableFuture<T> runAsync(commons.fun.Supplier<T> supplier, Consumer<Exception> consumer) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return supplier.get();
			} catch (Exception e) {
				consumer.accept(e);
			}
			return null;
		});
	}

	public static <T> CompletableFuture<T> runAsync(NAFunction naf) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				naf.apply();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
	}

	public static <T> CompletableFuture<T> runAsync(NAFunction naf, Logger logger) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				naf.apply();
			} catch (Exception e) {
				logger.error("Async running error:", e);
			}
			return null;
		});
	}

	public static <T> CompletableFuture<T> runAsync(NAFunction naf, Consumer<Exception> consumer) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				naf.apply();
			} catch (Exception e) {
				consumer.accept(e);
			}
			return null;
		});
	}

	public static <T> Consumer<T> runConsumer(Consumer<T> consumer) {
		return (x) -> {
			try {
				consumer.accept(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
	public static <T> Consumer<T> runConsumer(commons.fun.Consumer<T> consumer) {
		return (x) -> {
			try {
				consumer.accept(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

	public static <T> Consumer<T> runConsumer(Consumer<T> consumer, Logger logger) {
		return (x) -> {
			try {
				consumer.accept(x);
			} catch (Exception e) {
				logger.error("Async running error:", e);
			}
		};
	}
	public static <T> Consumer<T> runConsumer(commons.fun.Consumer<T> consumer, Logger logger) {
		return (x) -> {
			try {
				consumer.accept(x);
			} catch (Exception e) {
				logger.error("Async running error:", e);
			}
		};
	}

	public static <T> Consumer<T> runConsumer(Consumer<T> consumer, Consumer<Exception> exLogger) {
		return (x) -> {
			try {
				consumer.accept(x);
			} catch (Exception e) {
				exLogger.accept(e);
			}
		};
	}
	public static <T> Consumer<T> runConsumer(commons.fun.Consumer<T> consumer, Consumer<Exception> exLogger) {
		return (x) -> {
			try {
				consumer.accept(x);
			} catch (Exception e) {
				exLogger.accept(e);
			}
		};
	}
}
