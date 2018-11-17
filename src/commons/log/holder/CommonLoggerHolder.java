package commons.log.holder;

import commons.log.LogType;
import commons.log.LoggerAdapter;
import commons.log.LoggerAdapterFactory;

/**
 * common层日志记录器
 * 
 * @author BaileyFu
 * @version v1.0
 * @date 2018年11月17日
 */
public interface CommonLoggerHolder {
	LoggerAdapter LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_COMMON);
}
