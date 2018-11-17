package com.lz.components.common.log;

/**
 * 日志路由定义
 * 
 * @author fuli
 * @date 上午10:09:21
 * @version 1.0.0 Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public enum LogRoute {

	SLF4J(0);
	int index;

	private LogRoute(int index) {
		this.index = index;
	}
}
