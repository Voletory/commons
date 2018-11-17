package com.lz.components.common.util.variable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lz.components.common.util.function.NAFunction;

import io.reactivex.BackpressureStrategy;

public abstract class ActionHandler{
	protected long executeTimes = 0;
	/**
	 * 执行Action的总次数
	 * 
	 * @return
	 */
	long actionTimes() {
		return executeTimes;
	}
	abstract void init();
	abstract void destory();
	abstract void execute(NAFunction action)throws RuntimeException;

	/**
	 * 目前仅支持BUFFER和LATEST
	 * 
	 * @param backpressureStrategy
	 * @return
	 */
	public static ActionHandler makeActionHandler(BackpressureStrategy backpressureStrategy) {
		if (BackpressureStrategy.BUFFER == backpressureStrategy) {
			return new BUFFER();
		}
		return new LATEST();
	}

	private static class BUFFER extends ActionHandler {
		@Override
		void init() {
		}
		@Override
		void destory() {
		}
		@Override
		void execute(NAFunction action) throws RuntimeException{
			action.apply();
			executeTimes++;
		}
	}
	private static class LATEST extends ActionHandler {
		private Future<Long> executeResult;
		private ExecutorService executorService;
		@Override
		void init() {
			executorService = Executors.newSingleThreadExecutor();
		}
		@Override
		void destory() {
			executorService.shutdown();
			executorService = null;
		}
		@Override
		void execute(NAFunction action) throws RuntimeException{
			if (executeResult == null || executeResult.isDone()) {
				executeResult = executorService.submit(() -> {
					action.apply();
					return executeTimes++;
				});
			}
		}
	}
}
