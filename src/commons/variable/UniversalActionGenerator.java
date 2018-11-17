package commons.variable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.util.Asserts;

import commons.fun.NAFunction;
import commons.log.holder.CommonLoggerHolder;

/**
 * 通用Action执行器</p>
 * 可能会有x秒的误差,适用于时间精度大于等于秒的场景
 * 
 * @author fuli
 * @date 2018年11月8日
 * @version 1.0.0
 */
public final class UniversalActionGenerator implements CommonLoggerHolder{
	private static UniversalActionGenerator INSTANCE = new UniversalActionGenerator();
	private ActionGenerator actionGenerator;
	private Map<String,ActionEntity> actionEntityMap;
	private UniversalActionGenerator() {
		actionEntityMap=new HashMap<>();
	};
	private void putAction(String actionName, long interval, TimeUnit timeUnit, NAFunction action) {
		actionEntityMap.put(actionName, new ActionEntity(timeUnit.toMillis(interval), action));
		synchronized (INSTANCE) {
			if (actionGenerator == null) {
				actionGenerator = new ActionGenerator(1,TimeUnit.SECONDS,()->{
					try{
						actionEntityMap.values().parallelStream().forEach((x)->x.doAction());
					}catch(Exception e){
						LOGGER.error("UniversalActionGenerator run error!", e);
					}
				});
				actionGenerator.setName("UniversalActionGenerator");
				actionGenerator.start();
			} else if (!actionGenerator.isRunning()) {
				actionGenerator.restart();
			}
		}
	}
	private void removeAction(String actionName) {
		actionEntityMap.remove(actionName);
		synchronized (INSTANCE) {
			// 无任务时关闭generator
			if (actionEntityMap.size() == 0) {
				actionGenerator.stop();
			}
		}
	}
	private void stop() {
		synchronized (INSTANCE) {
			if (actionGenerator != null) {
				actionGenerator.stop();
			}
		}
	}
	private class ActionEntity{
		ActionTimer actionTimer;
		long actionInterval;
		NAFunction action;
		private ActionEntity(long actionInterval,NAFunction action) {
			actionTimer = new ActionTimer(true);
			this.actionInterval = actionInterval;
			this.action = action;
		}
		private void doAction() {
			if (actionTimer.onTime(actionInterval)) {
				action.apply();
			}
		}
	}
	/**
	 * 装载要执行的Action
	 * </p>
	 * 每秒扫描Action列表,执行满足间隔条件的Action</p>
	 * 相同的actionName会被覆盖
	 * @param actionName
	 * @param interval
	 * @param timeUnit
	 *            不能小于TimeUnit.SECONDS
	 * @param action 不要抛异常
	 */
	public static void loadAction(String actionName, long interval, TimeUnit timeUnit, NAFunction action) {
		Asserts.check(interval > 0, "interval must be positive!");
		Asserts.check(
				timeUnit != TimeUnit.NANOSECONDS && timeUnit != TimeUnit.MILLISECONDS
						&& timeUnit != TimeUnit.MICROSECONDS, "the timeunit must greater than equals to TimeUnit.SECONDS");
		INSTANCE.putAction(actionName, interval, timeUnit, action);
	}

	/**
	 * 卸载指定Action
	 * 
	 * @param actionName
	 */
	public static void unloadAction(String actionName) {
		INSTANCE.removeAction(actionName);
	}
	/**
	 * 服务停止时调用此方法
	 */
	public static void shutdown() {
		INSTANCE.stop();
	}
}
