package commons.log.holder;

import commons.log.LogType;
import commons.log.LoggerAdapter;
import commons.log.LoggerAdapterFactory;

/**
 * Service层日志记录器
 * 
 * @author fuli
 * @date 2018年5月24日
 * @version 1.0.0
 */
public interface ServiceLoggerHolder {
	LoggerAdapter LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_SERVICE);
}
