package commons.variable;

/**
 * Action计时接口
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.0
 * @description 时间间隔控制接口
 */
public interface IntervalVariable {

	/**
	 * 最后发生Action的时间
	 * 
	 * @return
	 */
	public long getLastActivateTime();

	/**
	 * 设置最后Action的时间
	 * 
	 * @param lastActivateTime
	 */
	public void setLastActivateTime(long lastActivateTime);

	/**
	 * 是否在Action后更新最后变动时间
	 * 
	 * @return
	 */
	public boolean isChangedLastActivateTimeAfterVariable();

	/**
	 * 设置是否在Action后更新最后变动时间
	 * 
	 * @param changedLastActivateTimeAfterVariable
	 * @return
	 */
	public void setChangedLastActivateTimeAfterVariable(boolean changedLastActivateTimeAfterVariable);

	/**
	 * 是否应该发生Action
	 * 
	 * @param interval
	 *            Action间隔
	 * @return
	 */
	public boolean onTime(long interval);

	/**
	 * 是否应该发生Action
	 * 
	 * @param interval
	 *            Action间隔
	 * @param compareTime
	 *            与最后Action时间相比较
	 * @return
	 */
	public boolean onTime(long interval, long compareTime);
}
