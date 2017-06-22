package commons.util;

import java.io.File;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

/**
 * 路径工具类
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.1
 * @description 类路径以及文件路径
 */
public final class PathUtil {
	/** 文件分隔符 */
	public static final String FILE_SEPARATOR = File.separator;
	/** 字符换行符 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	/** 空格 */
	public static final String SPACEBAR = "\u0020";

	private static final String WINDOWS_MARK = ":\\";

	private PathUtil() {
	}

	/**
	 * 获取系统分隔符
	 * 
	 * @return
	 */
	public static String getSeparator() {
		return FILE_SEPARATOR;
	}

	/**
	 * 获取文件所在绝对路径
	 * 
	 * @param fileClazz
	 * @return
	 */
	public static String getFilePath(Class<?> fileClazz) {
		String strClassName = fileClazz.getName();
		String strPackageName = StringUtils.EMPTY;
		if (fileClazz.getPackage() != null) {
			strPackageName = fileClazz.getPackage().getName();
		}
		String strClassFileName = null;
		if (!StringUtils.EMPTY.equals(strPackageName)) {
			strClassFileName = strClassName.substring(strPackageName.length() + 1, strClassName.length());
		} else {
			strClassFileName = strClassName;
		}
		URL url = null;
		url = fileClazz.getResource(strClassFileName + ".class");
		String strURL = url.toString();
		strURL = strURL.substring(strURL.indexOf('/') + 1, strURL.lastIndexOf('/') + 1).replaceAll("%20", " ");
		return FILE_SEPARATOR.equals("/") ? strURL.replaceAll("\\\\", "/") : strURL.replaceAll("/", "\\\\");
	}

	/**
	 * 获取文件所在的根路径
	 * 
	 * @param fileClazz
	 * @return
	 */
	public static String getRootPath(Class<?> fileClazz) {
		String path = FILE_SEPARATOR.equals("/") ? fileClazz.getClassLoader().getResource("").getPath().substring(1).replaceAll("\\\\", "/")
				: fileClazz.getClassLoader().getResource("").getPath().substring(1).replaceAll("/", "\\\\");
		return checkForOS(path);
	}

	/**
	 * 获取项目的根路径
	 * 
	 * @param fileClazz
	 * @return
	 */
	public static String getProjectPath(Class<?> fileClazz) {
		String path = getRootPath(fileClazz);
		int local = path.lastIndexOf(FILE_SEPARATOR + "bin" + FILE_SEPARATOR);
		if (local != -1)
			path = path.substring(0, local + 1);
		local = path.lastIndexOf(FILE_SEPARATOR + "classes" + FILE_SEPARATOR);
		if (local != -1)
			path = path.substring(0, local + 1);
		local = path.lastIndexOf(FILE_SEPARATOR + "WEB-INF" + FILE_SEPARATOR);
		if (local != -1)
			path = path.substring(0, local + 1);
		return checkForOS(path);
	}

	/**
	 * 非Windows系统,在路径前加'/'
	 * 
	 * @param path
	 * @return
	 */
	private static String checkForOS(String path) {
		if (path.indexOf(WINDOWS_MARK) == -1) {
			path = path.startsWith(FILE_SEPARATOR) ? path : FILE_SEPARATOR + path;
		}
		return path;
	}

	/**
	 * 获取当前所有的类路径
	 * 
	 * @return
	 */
	public static String[] getClassPath() {
		String classPaths = System.getProperty("java.class.path");
		/** linux换成windows */
		classPaths.replaceAll(":", ";");
		return classPaths.split(";");
	}
}
