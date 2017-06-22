package commons.config;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import commons.util.PathUtil;

/**
 * XML配置基类
 * 
 * @author bailey.fu
 * @date Oct 27, 2009
 * @version 1.0
 * @description 负责初始化File到XML的document
 */
public abstract class XMLConfiguration extends BasedConfiguration {

	private String fileName;
	protected Document document;

	public XMLConfiguration(String fileName) throws ConfigurationException {
		this.fileName = fileName;
		loadFile();
	}

	public String getFileName() {
		return fileName;
	}

	protected File buildConfigFile() {
		String[] classPaths = PathUtil.getClassPath();
		File temp = null;
		for (String classPath : classPaths) {
			classPath = classPath.endsWith(".jar") ? classPath.substring(0, classPath.lastIndexOf(PathUtil.FILE_SEPARATOR)) : classPath;
			temp = new File(classPath + PathUtil.FILE_SEPARATOR + fileName);
			if (temp.isFile())
				return temp;
		}
		String rootPath = PathUtil.getRootPath(XMLConfiguration.class);
		if (rootPath.indexOf("WEB-INF") != -1) {
			temp = new File(rootPath.substring(0, rootPath.lastIndexOf("WEB-INF") + 8) + fileName);
			if (temp.isFile())
				return temp;
		}
		temp = new File(rootPath + fileName);
		if (temp.isFile())
			return temp;
		return null;
	}

	protected void init() throws ConfigurationException {
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(configFile);
		} catch (DocumentException de) {
			throw new FileLoadException(de);
		}
	}

	public Document getDocument() {
		return document;
	}

}
