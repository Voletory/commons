package commons.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import commons.log.CLogger;

/**
 * Properties文件处理
 * 
 * @author bailey_fu
 * @data 2012-12-27
 * @version 1.0
 * @Description
 */
public final class PropHandler {

	private static PropHandler propHandler = new PropHandler();

	public Map<String, Properties> propMap = new HashMap<String, Properties>();

	private PropHandler() {
	}

	public synchronized static void addProperties(String filePath) {
		if (propHandler.propMap.get(filePath) == null) {
			reloadProperties(filePath);
		}
	}

	public static void reloadProperties(String filePath) {
		Properties properties = new Properties();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filePath));
			properties.load(is);
			is.close();
			propHandler.propMap.put(filePath, properties);
		} catch (Exception e) {
			CLogger.error("PropHandler reloadProperties failed. cause : {}", e);
		}
	}

	public static Properties getProperties(String filePath) {
		addProperties(filePath);
		return propHandler.propMap.get(filePath);
	}

	public static String getValue(String filePath, String key) {
		addProperties(filePath);
		try {
			return propHandler.propMap.get(filePath).getProperty(key);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @return 查找到的第一个值
	 */
	public static String getValue(String key) {
		String value = null;
		try {
			for (Properties prop : propHandler.propMap.values()) {
				value = prop.getProperty(key);
				if (value != null) {
					break;
				}
			}
		} catch (Exception e) {
		}
		return value;
	}

}
