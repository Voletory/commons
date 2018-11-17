package com.lz.components.common.log;

import com.lz.components.common.log.agent.Slf4jAgent;

/**
 * 日志记录适配器
 * 
 * @author fuli
 * @date 下午7:36:32
 * @version 1.0.0 Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public class LoggerAdapter {
	private final LogAgent[] loggers;
	private final int defaultIndex;

	public LoggerAdapter(String logTypeName) {
		loggers = new LogAgent[LogRoute.values().length];
		//SLF4J作为默认日志记录器
		defaultIndex = LogRoute.SLF4J.index;
		initLogger(logTypeName);
	}
	public LoggerAdapter(LogType logType) {
		loggers = new LogAgent[LogRoute.values().length];
		//SLF4J作为默认日志记录器
		defaultIndex = LogRoute.SLF4J.index;
		initLogger(logType.logName);
	}
	private void initLogger(String logTypeName) {
		loggers[LogRoute.SLF4J.index] = new Slf4jAgent(logTypeName);
	}
	public boolean isDebugEnabled() {
		return loggers[defaultIndex].isDebugEnabled();
	}
	public boolean isDebugEnabled(LogRoute logRoute) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
			return false;
		}
		return loggers[logRoute.index].isDebugEnabled();
	}
	
	public void debug(String msg) {
		loggers[defaultIndex].debug(msg);
	}
	public void debug(String format, Object... arguments) {
		loggers[defaultIndex].debug(format, arguments);
	}
	/**
	 * 指定日志路由
	 * @param logRoute
	 * @param msg
	 */
	public void debug(LogRoute logRoute,String msg) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].debug(msg);
		}
	}
	public void debug(LogRoute logRoute,String format, Object... arguments) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].debug(format, arguments);
		}
	}

	public void info(String msg) {
		loggers[defaultIndex].info(msg);
	}
	public void info(String format, Object... arguments) {
		loggers[defaultIndex].info(format, arguments);
	}
	/**
	 * 指定日志路由
	 * @param logRoute
	 * @param msg
	 */
	public void info(LogRoute logRoute,String msg) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].info(msg);
		}
	}
	public void info(LogRoute logRoute,String format, Object... arguments) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].info(format, arguments);
		}
	}

	public void warn(String msg) {
		loggers[defaultIndex].warn(msg);
	}
	public void warn(String format, Object... arguments) {
		loggers[defaultIndex].warn(format, arguments);
	}
	/**
	 * 指定日志路由
	 * @param logRoute
	 * @param msg
	 */
	public void warn(LogRoute logRoute,String msg) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].warn(msg);
		}
	}
	public void warn(LogRoute logRoute,String format, Object... arguments) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].warn(format, arguments);
		}
	}

	/**
	 * error级别的日志除了输出到指定日志位置外,还会输出到sys.error位置
	 */
	public void error(String msg, Throwable e) {
		loggers[defaultIndex].error(msg, e);
		loggers[defaultIndex].logError(msg, e);
	}
	public void error(String format, Object... arguments) {
		loggers[defaultIndex].error(format, arguments);
		loggers[defaultIndex].logError(format, arguments);
	}
	public void error(LogRoute logRoute,String msg, Throwable e) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].error(msg, e);
			loggers[logRoute.index].logError(msg, e);
		}
	}
	public void error(LogRoute logRoute,String format, Object... arguments) {
		if (logRoute == null||loggers[logRoute.index] == null) {
			loggers[defaultIndex].warn("No LogRoute : "+logRoute+" found!");
		}else {
			loggers[logRoute.index].error(format, arguments);
			loggers[logRoute.index].logError(format, arguments);
		}
	}
}
