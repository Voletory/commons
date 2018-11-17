package com.lz.components.common.log.holder;

import com.lz.components.common.log.LogType;
import com.lz.components.common.log.LoggerAdapter;
import com.lz.components.common.log.LoggerAdapterFactory;

public interface CommonLoggerHolder {
	LoggerAdapter LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_COMMON);
}
