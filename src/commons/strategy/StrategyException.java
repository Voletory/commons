package commons.strategy;


/**
 * 策略异常
 * 
 * @author bailey.fu
 * @date Oct 27, 2009
 * @version 1.0
 * @description
 */
public class StrategyException extends Exception {
	private static final long serialVersionUID = 1L;
	private static String DEFAULT_MESSAGE = "Strategy Exception";

	public StrategyException() {
		super(DEFAULT_MESSAGE);
	}

	public StrategyException(
								String message) {
		super(message);
	}

	public StrategyException(
								Exception cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	public StrategyException(
								String message,
								Exception cause) {
		super(message, cause);
	}
}
