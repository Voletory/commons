package commons.log;

/**
 * 通用日志类型
 * 
 * @author fuli
 * @date 下午7:34:52
 * @version 1.0.0
 * Copyright 本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public enum LogType {
	SYS_CONTROLLER("sys.controller"), 
	SYS_BIZ("sys.biz"), 
	SYS_INTEGRATION("sys.integration"), 
	SYS_SERVICE("sys.service"),
	SYS_THIRD("sys.third"),
	SYS_COMMON("sys.common"), 
	SYS_DAL("sys.dal"),
	SYS_ERROR("sys.error");

	public final String logName;
	LogType(String logName) {
		this.logName = logName;
	}
}
