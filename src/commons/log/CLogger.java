package commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 输出到控制台
 * 
 * @author bailey_fu
 * @data 2014-1-7
 * @version 1.0
 * @Description
 */
public class CLogger {
	private static final Logger logger = LoggerFactory.getLogger(LOG_TYPE.CONSOLE.LOGGER_NAME);

	public static void trace(String message, Object... objs) {
		logger.trace(message, objs);
	}
	public static void trace(String message, Throwable ta) {
		logger.trace(message, ta);
	}
	
	public static void debug(String message, Object... objs) {
		logger.debug(message, objs);
	}
	public static void debug(String message, Throwable ta) {
		logger.debug(message, ta);
	}

	public static void info(String message, Object... objs) {
		logger.info(message, objs);
	}
	public static void info(String message, Throwable ta) {
		logger.info(message, ta);
	}
	
	public static void warn(String message, Object... objs) {
		logger.warn(message, objs);
	}
	public static void warn(String message, Throwable ta) {
		logger.warn(message, ta);
	}

	public static void error(String message, Object... objs) {
		logger.error(message, objs);
	}
	public static void error(String message, Throwable ta) {
		logger.error(message, ta);
	}
}
