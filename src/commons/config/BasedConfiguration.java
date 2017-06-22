package commons.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration基类
 * 
 * @author bailey.fu
 * @date Oct 27, 2009
 * @version 1.1
 * @description
 */
public abstract class BasedConfiguration implements Configuration {

	private String fullFileName;
	protected File configFile;

	/**
	 * 指定配置文件
	 * 
	 * @return
	 */
	abstract protected File buildConfigFile();

	/**
	 * 获取文件名称
	 * 
	 * @return
	 */
	abstract public String getFileName();

	public String getFullFileName() {
		return fullFileName;
	}

	protected void loadFile() throws ConfigurationException {
		configFile = buildConfigFile();
		if (configFile == null)
			throw new FileLoadException("unknow file : " + getFileName());
		fullFileName = configFile.getAbsolutePath();
		if (configFile == null || !configFile.exists())
			throw new FileLoadException("file : " + fullFileName + " is not exist");
		else if (!configFile.isFile())
			throw new FileLoadException("file : " + fullFileName + " is not useable");
		else if (!configFile.getName().endsWith(".xml") && !configFile.getName().endsWith(".properties"))
			throw new FileLoadException("file : " + fullFileName + " is inefficacy ; require XML or Properties");
		else if (!configFile.canRead())
			throw new FileLoadException("file : " + fullFileName + " is cannot read");
		init();
	}

	protected AnalyzeException buildAnalyzeException() {
		return new AnalyzeException();
	}

	protected AnalyzeException buildAnalyzeException(String errorMessage) {
		return new AnalyzeException(errorMessage);
	}

	protected AnalyzeException buildAnalyzeException(AnalyzeError analyzeError, Object[] errorMessages) {
		if (analyzeError == null || errorMessages == null || errorMessages.length == 0)
			return buildAnalyzeException();
		String errorMessage = basicMessages.get(analyzeError.ERROR_CODE);
		for (int i = 0; i < errorMessages.length; i++) {
			errorMessage = errorMessage.replaceFirst("\\?", errorMessages[i].toString());
		}
		return new AnalyzeException(errorMessage);
	}

	private static Map<Integer, String> basicMessages = new HashMap<Integer, String>();
	static {
		basicMessages.put(AnalyzeError.ROOT_NOT_EXIST.ERROR_CODE, " ? : '?' is not exist .");
		basicMessages.put(AnalyzeError.NODE_NOT_EXIST.ERROR_CODE, " ? : ? of Node or Element '?' is not exist .");
		basicMessages.put(AnalyzeError.VALUE_INEFFICACY.ERROR_CODE, " ? : ? of Node or Element '?' value : '?' is inefficacy .");
		basicMessages.put(AnalyzeError.VALUE_NEEDS.ERROR_CODE, " ? : ? of Node or Element '?' value is needs , can not empty .");
		basicMessages.put(AnalyzeError.VALUE_INEFFICACY_REPORT.ERROR_CODE, " ? : ? of Node or Element '?' value : '?' is inefficacy . ?");
	}

	/**
	 * 初始化
	 * 
	 * @throws ConfigurationException
	 */
	abstract protected void init() throws ConfigurationException;

}
