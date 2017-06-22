package commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录到xcommons.log
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-09 10:17
 */
public class XLogger {
	private static final Logger logger = LoggerFactory.getLogger(LOG_TYPE.X_COMMON.LOGGER_NAME);
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
