package commons.variable;

import java.util.concurrent.TimeUnit;

import org.apache.http.util.Asserts;

import commons.fun.NAFunction;
import commons.log.holder.CommonLoggerHolder;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 定时触发动作
 * 
 * @author fuli
 * @date 2018年11月6日
 * @version 1.0.0
 */
public class ActionGenerator implements CommonLoggerHolder{
	private static final String STATUS_INIT = "I";
	private static final String STATUS_RUNNING = "R";
	private static final String STATUS_STOP = "S";
	private ObservableEmitter<Long> emitter;
	private Observable<Long> obva;
	private Disposable emitterDis;
	private String name;
	private String status;
	private long interval;
	private TimeUnit intervalTimeUnit;
	private NAFunction action;
	private ActionHandler actionHandler;
	
	public ActionGenerator(long interval, TimeUnit timeUnit, NAFunction action) {
		Asserts.check(interval > 0, "interval must be positive!");
		Asserts.notNull(timeUnit, "timeUnit");
		name = this.toString();
		status = STATUS_INIT;
		this.interval = interval;
		this.intervalTimeUnit = timeUnit;
		this.action = action;
	}
	/**
	 * 默认LATEST
	 */
	public void start(){
		start(BackpressureStrategy.LATEST);
	}
	public synchronized void start(BackpressureStrategy backpressureStrategy){
		if (status == STATUS_INIT) {
			status = STATUS_RUNNING;
			actionHandler = ActionHandler.makeActionHandler(BackpressureStrategy.LATEST);
			actionHandler.init();
			this.obva = Observable.create((ObservableOnSubscribe<Long>) e -> {
				emitter = e;
				emitterDis = Observable.interval(interval, intervalTimeUnit).subscribe(e::onNext);
			});
			this.obva.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).subscribe(this::doAction, (e) -> {
				LOGGER.error("the ActionGenerator." + name + " doAction error", e);
			}, () -> {
				LOGGER.info("the ActionGenerator." + name + " has completed!");
			});
			LOGGER.info("the ActionGenerator." + name + " has started !");
		}
	}
	private long lastActionValue = 0;
	private void doAction(long t) {
		lastActionValue = t;
		actionHandler.execute(action);
	}
	public synchronized void stop(){
		if (status == STATUS_RUNNING) {
			emitterDis.dispose();
			emitter.onComplete();
			actionHandler.destory();
			emitterDis = null;
			emitter = null;
			obva = null;
			status = STATUS_STOP;
			LOGGER.info("the ActionGenerator." + name + " has stopped !");
		}
	}

	public synchronized boolean restart() {
		if (status == STATUS_STOP) {
			status = STATUS_INIT;
			start();
			return true;
		}
		return false;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 触发总次数
	 * @return
	 */
	public long triggerTimes(){
		return lastActionValue;
	}
	/**
	 * 执行Action的总次数
	 * 
	 * @return
	 */
	public long actionTimes() {
		return actionHandler.actionTimes();
	}
	public boolean isRunning() {
		return status == STATUS_RUNNING;
	}
}
