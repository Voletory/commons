package commons.log;

/**
 * LoggerAdapter工厂类
 * 
 * @author fuli
 * @date 下午7:41:59
 * @version 1.0.0
 */
public final class LoggerAdapterFactory {
	private LoggerAdapterFactory() {
	}

	public static LoggerAdapter getLogger(String name) {
		return new LoggerAdapter(name);
	}

	public static LoggerAdapter getLogger(LogType logType) {
		return new LoggerAdapter(logType.logName);
	}
}
