package commons.log;

/**
 * 日志路由定义
 * 
 * @author fuli
 * @date 上午10:09:21
 * @version 1.0.0
 */
public enum LogRoute {

	SLF4J(0);
	int index;

	private LogRoute(int index) {
		this.index = index;
	}
}
