package commons.log.holder;

import commons.log.LogType;
import commons.log.LoggerAdapter;
import commons.log.LoggerAdapterFactory;

/**
 * Error日志记录器
 * 
 * @author fuli
 * @date 2018年7月2日
 * @version 1.0.0
 */
public interface ErrorLoggerHolder {
	LoggerAdapter LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_ERROR);
}
