package commons.config;

/**
 * 配置异常
 * 
 * @author bailey.fu
 * @date Oct 19, 2009
 * @version 1.0
 * @see AnalyzeException FileLoadException
 * @description 子类:"文件装载异常"和"解析异常"
 */
public class ConfigurationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Exception cause) {
		super(message, cause);
	}

}
