package commons.config;

/**
 * 配置文件错误消息描述
 * 
 * @author bailey.fu
 * @date Nov 20, 2009
 * @version 1.0
 * @description
 */
public enum AnalyzeError {
	/** 根节点不存在 ( 2 parameter ) */
	ROOT_NOT_EXIST(-1001, "根节点不存在"),
	/** 节点或元素不存在 ( 3 parameter ) */
	NODE_NOT_EXIST(-1002, "节点或元素不存在"),
	/** 参数值无效 ( 4 parameter ) */
	VALUE_INEFFICACY(-1003, "参数值无效"),
	/** 参数值是必须的 ( 3 parameter ) */
	VALUE_NEEDS(-1004, "参数值是必须的"),
	/** 参数值无效(附加说明)( 5 parameter ) */
	VALUE_INEFFICACY_REPORT(-1005, "参数值无效(附加说明)");

	public final int ERROR_CODE;
	public final String ERROR_DESC;

	private AnalyzeError(int ERROR_CODE, String ERROR_DESC) {
		this.ERROR_CODE = ERROR_CODE;
		this.ERROR_DESC = ERROR_DESC;
	}

	public AnalyzeError valueOf(int ERROR_CODE) {
		AnalyzeError[] values = values();
		for (AnalyzeError ae : values) {
			if (ae.ERROR_CODE == ERROR_CODE)
				return ae;
		}
		return null;
	}

}
