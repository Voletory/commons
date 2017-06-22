package commons.config;

/**
 * 配置文件装载错误
 * 
 * @author bailey.fu
 * @date Oct 21, 2009
 * @version 1.0
 * @see ConfigurationException
 * @description
 */
public class FileLoadException extends ConfigurationException {
	private static final long serialVersionUID = 1L;
	private static String DEFAULT_MESSAGE = "FileLoad Exception";

	public FileLoadException() {
		super(DEFAULT_MESSAGE);
	}

	public FileLoadException(String message) {
		super(message);
	}

	public FileLoadException(Exception cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	public FileLoadException(String message, Exception cause) {
		super(message, cause);
	}

}
