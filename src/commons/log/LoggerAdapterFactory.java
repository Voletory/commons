package com.lz.components.common.log;

/**
 * LoggerAdapter工厂类
 * 
 * @author fuli
 * @date 下午7:41:59
 * @version 1.0.0 Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
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
