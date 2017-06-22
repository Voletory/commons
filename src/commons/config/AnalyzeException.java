package commons.config;

/**
 * 解析异常
 * 
 * @author ful
 * @date Oct 19, 2009
 * @version 1.0
 * @see ConfigurationException
 * @description 各应用自行填写
 */
public class AnalyzeException extends ConfigurationException {
	private static final long serialVersionUID = 1L;
	private static String DEFAULT_MESSAGE = "Analyze Exception";

	public AnalyzeException() {
		super(DEFAULT_MESSAGE);
	}

	public AnalyzeException(String message) {
		super(message);
	}

	public AnalyzeException(Exception cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	public AnalyzeException(String message, Exception cause) {
		super(message, cause);
	}
}
