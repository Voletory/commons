package com.lz.components.common.log.holder;

import com.lz.components.common.log.LogType;
import com.lz.components.common.log.LoggerAdapter;
import com.lz.components.common.log.LoggerAdapterFactory;

/**
 * Biz层日志记录器
 * 
 * @author fuli
 * @date 2018年5月24日
 * @version 1.0.0 Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public interface BizLoggerHolder {
	LoggerAdapter LOGGER = LoggerAdapterFactory.getLogger(LogType.SYS_BIZ);
}
