package commons.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import commons.util.PathUtil;

/**
 * propertys配置基类
 * 
 * @author bailey.fu
 * @date Oct 27, 2009
 * @version 1.0
 * @description 负责初始化参数集;将参数键值对放入Map<br/>
 *              已过时,改用系统提供的java.util.Properties
 */
@Deprecated
public abstract class PropertyConfiguration extends BasedConfiguration {

	private static final String EQUAL_MARK = "=";
	private static final String ANNOTATE_MARK_SLASH = "//";
	private static final String ANNOTATE_MARK_BRACKET = "<!--";

	private String fileName;
	protected Map<String, String> propertys;

	public PropertyConfiguration(String fileName) throws ConfigurationException {
		this.fileName = fileName;
		loadFile();
	}

	protected File buildConfigFile() {
		String[] classPaths = PathUtil.getClassPath();
		String separator = System.getProperty("file.separator");
		File temp = null;
		for (String classPath : classPaths) {
			classPath = classPath.endsWith(".jar") ? classPath.substring(0, classPath.lastIndexOf(separator)) : classPath;
			temp = new File(classPath + separator + fileName);
			if (temp.isFile())
				return temp;
		}
		String classPath = PathUtil.getRootPath(XMLConfiguration.class);
		if (classPath.indexOf("WEB-INF") != -1) {
			temp = new File(classPath.substring(0, classPath.lastIndexOf("WEB-INF") + 8) + fileName);
			if (temp.isFile())
				return temp;
		}
		temp = new File(classPath + fileName);
		if (temp.isFile())
			return temp;
		return null;
	}

	public String getFileName() {
		return fileName;
	}

	protected void init() throws ConfigurationException {
		propertys = new HashMap<String, String>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(configFile);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				line = line.replaceAll(" ", "");
				if (!line.equals("") && !line.startsWith(ANNOTATE_MARK_SLASH) && !line.startsWith(ANNOTATE_MARK_BRACKET)) {
					if (line.indexOf(EQUAL_MARK) == -1) {
						throw new AnalyzeException("line '" + line + "' format is error. example : 'key " + EQUAL_MARK + " value'");
					}
					String[] lineArray = line.split("=");
					String key = lineArray[0];
					String value = "";
					if (lineArray.length > 1)
						value = lineArray[1];
					propertys.put(key, value);
				}
			}
		} catch (Exception e) {
			throw new FileLoadException(e);
		} finally {
			if (fis != null)
				try {
					fis.close();
					isr.close();
					br.close();
				} catch (Exception e) {
				}
			;
		}
	}

	public Map<String, String> getPropertys() {
		return propertys;
	}

	public String getValue(String key) {
		return propertys.get(key);
	}
}
