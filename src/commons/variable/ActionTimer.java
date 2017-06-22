package commons.variable;

/**
 * Action计时类<br/>
 * 
 * @author bailey.fu
 * @date Oct 13, 2009
 * @version 1.2
 * @description 时间间隔控制,可指定目标时间
 */
public class ActionTimer extends BasedIntervalVariable {

	/** 目标时间 */
	private long targetTime;

	public ActionTimer() {
		super();
		targetTime = 0l;
	}

	public ActionTimer(long lastActivateTime) {
		super(lastActivateTime);
		targetTime = 0l;
	}

	public ActionTimer(boolean changedLastActivateTimeAfterVariable) {
		super(changedLastActivateTimeAfterVariable);
		targetTime = 0l;
	}

	public ActionTimer(long lastActivateTime, boolean changedLastActivateTimeAfterVariable) {
		super(lastActivateTime, changedLastActivateTimeAfterVariable);
		targetTime = 0l;
	}

	/** 设置目标时间 */
	public void setTargetTime(long targetTime) {
		this.targetTime = targetTime;
	}

	/**
	 * 当前时间与目标时间的差
	 * 
	 * @return -1:当前时间大于目标时间<br/>
	 *         0:当前时间等于目标时间<br/>
	 *         1:当前时间小于目标时间
	 */
	public int inTime() {
		long currentTime = System.currentTimeMillis();
		return currentTime > targetTime ? -1 : currentTime == targetTime ? 0 : 1;
	}

	/**
	 * 是否已等于或超过目标时间
	 * 
	 * @return true:当前时间大于或等于目标时间<br/>
	 *         false:当前时间小于目标时间
	 */
	public boolean isTime() {
		return inTime() != 1;
	}

}
