package commons.variable;

/**
 * Action计时基类
 * 
 * @author bailey.fu
 * @date Oct 13, 2009
 * @version 1.3
 * @description 时间间隔控制
 */
public abstract class BasedIntervalVariable implements IntervalVariable {

	/** 最后Action时间 */
	private long lastActivateTime;
	/** 是否更新最后Action时间 */
	private boolean changedLastActivateTimeAfterVariable;

	public BasedIntervalVariable() {
		lastActivateTime = 0l;
		changedLastActivateTimeAfterVariable = false;
	}

	public BasedIntervalVariable(long lastActivateTime) {
		this.lastActivateTime = lastActivateTime;
		changedLastActivateTimeAfterVariable = false;
	}

	public BasedIntervalVariable(boolean changedLastActivateTimeAfterVariable) {
		lastActivateTime = 0l;
		this.changedLastActivateTimeAfterVariable = changedLastActivateTimeAfterVariable;
	}

	public BasedIntervalVariable(long lastActivateTime, boolean changedLastActivateTimeAfterVariable) {
		this.lastActivateTime = lastActivateTime;
		this.changedLastActivateTimeAfterVariable = changedLastActivateTimeAfterVariable;
	}

	public long getLastActivateTime() {
		return lastActivateTime;
	}

	public void setLastActivateTime(long lastActivateTime) {
		this.lastActivateTime = lastActivateTime;
	}

	public boolean isChangedLastActivateTimeAfterVariable() {
		return changedLastActivateTimeAfterVariable;
	}

	public void setChangedLastActivateTimeAfterVariable(boolean changedLastActivateTimeAfterVariable) {
		this.changedLastActivateTimeAfterVariable = changedLastActivateTimeAfterVariable;
	}

	public boolean onTime(long interval) {
		return onTime(interval, System.currentTimeMillis());
	}

	public boolean onTime(long interval, long compareTime) {
		if ((lastActivateTime + interval) <= compareTime) {
			if (changedLastActivateTimeAfterVariable)
				lastActivateTime = compareTime;
			return true;
		}
		return false;
	}

	public void reset() {
		lastActivateTime = 0l;
	}
}
