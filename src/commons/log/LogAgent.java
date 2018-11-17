package commons.log;

/**
 * 日志代理接口
 * 
 * @author fuli
 * @date 下午7:54:42
 * @version 1.0.0
 */
public interface LogAgent {
	public LogRoute getLogRoute();
	public boolean isDebugEnabled();

	public void debug(String format, Object... arguments);

	public void debug(String msg);

	public void info(String format, Object... arguments);

	public void info(String msg);

	public void warn(String format, Object... arguments);

	public void warn(String msg);

	public void error(String msg, Throwable e);

	public void error(String format, Object... arguments);
	/**
	 * 记录错误日志到特定位置
	 */
	public void logError(String msg, Throwable e) ;
	public void logError(String format, Object... arguments) ;
}
