package commons.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import commons.fun.NAFunction;
import commons.log.LogType;
import commons.log.LoggerAdapter;
import commons.log.LoggerAdapterFactory;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;

/**
 * 异步调用执行器
 * 
 * @author fuli
 * @date 2018年8月13日
 * @version 1.2.0 
 * @desc 新增callback
 */
public class AsyncInvoker{
	private static AsyncInvoker INSTANCE;
	private LoggerAdapter LOGGER;
	private FlowableEmitter<NAFunction> emitter;
	private Subscription subscription;
	public AsyncInvoker(){
		this.LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_COMMON);
		init(BackpressureStrategy.BUFFER);
	}
	public AsyncInvoker(BackpressureStrategy backpressureStrategy){
		this.LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_COMMON);
		init(backpressureStrategy);
	}
	public AsyncInvoker(LoggerAdapter LOGGER){
		this.LOGGER = LOGGER;
		init(BackpressureStrategy.BUFFER);
	}
	public AsyncInvoker(BackpressureStrategy backpressureStrategy,LoggerAdapter LOGGER){
		this.LOGGER = LOGGER;
		init(backpressureStrategy);
	}
	void init(BackpressureStrategy backpressureStrategy) {
		Flowable.<NAFunction>create((emitter) -> this.emitter = emitter, backpressureStrategy)
		.observeOn(Schedulers.io()).subscribe(new Subscriber<NAFunction>() {
			@Override
			public void onComplete() {
			}
			@Override
			public void onError(Throwable e) {
				LOGGER.error("AsyncInvoker Logger emit error !", e);
			}
			@Override
			public void onNext(NAFunction naf) {
				try {
					naf.apply();
				} catch (Exception e) {
					LOGGER.error("AsyncInvoker emit error !", e);
				}
			}
			@Override
			public void onSubscribe(Subscription sub) {
				subscription = sub;
			}
		});
	}
	static {
		INSTANCE = new AsyncInvoker();
	}
	public void execute(NAFunction action) {
		execute(action,(e)->{
			LOGGER.error("AsyncInvoker execute error!", e);
		});
	}

	public void execute(NAFunction action, LoggerAdapter LOGGER) {
		execute(action,(e)->{
			LOGGER.error("AsyncInvoker execute error!", e);
		});
	}
	
	public void execute(NAFunction action, Consumer<Exception> exceptionHandler) {
		emitter.onNext(() -> {
			try {
				action.apply();
			} catch (Exception e) {
				exceptionHandler.accept(e);
			}
		});
		subscription.request(Long.MAX_VALUE);
	}
	
	public void execute(Supplier<Object> action, Consumer<Object> callback) {
		execute(action,callback,(e)->{
			LOGGER.error("AsyncInvoker execute error!", e);
		});
	}
	
	public void execute(Supplier<Object> action, Consumer<Object> callback,LoggerAdapter LOGGER) {
		execute(action,callback,(e)->{
			LOGGER.error("AsyncInvoker execute error!", e);
		});
	}
	
	public void execute(Supplier<Object> action, Consumer<Object> callback,Consumer<Exception> exceptionHandler) {
		emitter.onNext(() -> {
			try {
				callback.accept(action.get());
			} catch (Exception e) {
				exceptionHandler.accept(e);
			}
		});
		subscription.request(Long.MAX_VALUE);
	}
	
	public static void run(NAFunction action) {
		INSTANCE.execute(action);
	}

	public static void run(NAFunction action, LoggerAdapter LOGGER) {
		INSTANCE.execute(action, LOGGER);
	}
	
	public static void run(NAFunction action, Consumer<Exception> exceptionHandler) {
		INSTANCE.execute(action, exceptionHandler);
	}
	
	public static void run(Supplier<Object> action,Consumer<Object> callback) {
		INSTANCE.execute(action,callback);
	}

	public static void run(Supplier<Object> action,Consumer<Object> callback, LoggerAdapter LOGGER) {
		INSTANCE.execute(action,callback, LOGGER);
	}
	
	public static void run(Supplier<Object> action,Consumer<Object> callback, Consumer<Exception> exceptionHandler) {
		INSTANCE.execute(action, callback,exceptionHandler);
	}
}
