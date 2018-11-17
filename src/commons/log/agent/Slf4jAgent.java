package com.lz.components.common.log.agent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lz.components.common.log.LogAgent;
import com.lz.components.common.log.LogRoute;
import com.lz.components.common.log.LogType;

/**
 * 默认日志记录代理 SLF4J实现;该类并不强制使用logback,依然是根据classpath的具体jar包来选择具体实现
 * 
 * @author fuli
 * @date 下午7:54:49
 * @version 1.0.0 Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public class Slf4jAgent implements LogAgent {
	private final Logger logger;
	private final Logger errorLogger;

	public LogRoute getLogRoute() {
		return LogRoute.SLF4J;
	}

	public Slf4jAgent(String name) {
		logger = LoggerFactory.getLogger(name);
		if (LogType.SYS_ERROR.logName.equals(name)) {
			errorLogger = logger;
		} else {
			errorLogger = LoggerFactory.getLogger(LogType.SYS_ERROR.logName);
		}
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(String format, Object... arguments) {
		logger.debug(format, arguments);
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void info(String format, Object... arguments) {
		logger.info(format, arguments);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void warn(String format, Object... arguments) {
		logger.warn(format, arguments);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void error(String msg, Throwable e) {
		logger.error(msg, e);
	}

	public void error(String format, Object... arguments) {
		logger.error(format, arguments);
	}

	public void logError(String msg, Throwable e) {
		errorLogger.error(msg, e);
	}

	public void logError(String format, Object... arguments) {
		errorLogger.error(format, arguments);
	}
}
