package commons.log.holder;

import commons.log.LogType;
import commons.log.LoggerAdapter;
import commons.log.LoggerAdapterFactory;

/**
 * Controller层日志记录器
 * 
 * @author BaileyFu
 * @version v1.0
 * @date 2018年11月17日
 */
public interface ControllerLoggerHolder {
	LoggerAdapter LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_CONTROLLER);
}
