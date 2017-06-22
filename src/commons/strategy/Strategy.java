package commons.strategy;

/**
 * 策略接口
 * 
 * @author bailey.fu
 * @date Oct 27, 2009
 * @version 1.0
 * @description
 */
public interface Strategy {

	/**
	 * 验证策略参数;或给予默认正确值
	 * 
	 * @throws StrategyException
	 */
	public void validate() throws StrategyException;

}
