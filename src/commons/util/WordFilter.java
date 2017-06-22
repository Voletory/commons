package commons.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commons.log.CLogger;

/**
 * 字符过滤工具类
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.0
 * @description [禁止字符、非法字符转义]过滤器
 */
public final class WordFilter {
	public static final int NAME = 1; // 1.禁止词

	public static final int SEARCH = 2; // 2.搜索屏蔽词

	public static final int INPUT = 3; // 3.输入过滤

	public static final int NOTSEARCH = 1; // 不可搜索

	public static final int NOTSEARCHHOT = 2; // 不可热门

	// 数组格式:敏感字词/替换准备字词/用户ID/分类属性
	public static int maxNum = 2000;

	private static String[][] names = null; // 禁止词

	private static String[][] searchs = null; // 搜索屏蔽词

	private static String[][] inputs = null; // 输入过滤

	public static void loadCheck(String[][]... datas) {
		if (datas.length > 0) {
			names = datas[0];
		}
		if (datas.length > 1) {
			searchs = datas[1];
		}
		if (datas.length > 2) {
			inputs = datas[2];
		}
	}

	/**
	 * 对用户名字进行过滤
	 * 
	 * @param uid
	 *            String
	 * @param name
	 *            String
	 * @return String
	 */
	public static String filterName(String uid, String name) {
		// 超管不过滤
		if (uid.equals("1860") || uid.equals("1001") || uid.equals("12345678")) {
			return name;
		}
		return name;

	}

	/**
	 * 这个方法用来把'<' '>' ' '替换成'&lt;' '&gt;' '&nbsp;'
	 * 
	 * @param in
	 *            将被替换的字符串
	 * @return 返回被替换了的字符串
	 */
	public static String toHTMLString(String in) {
		// 检查input是不是null或则是空字符串，如果是就直接返回input
		if (in == null || in.length() == 0) {
			return in;
		}
		in = in.replaceAll("&amp;", "&").replaceAll("&", "&amp;");
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append("&#39;");
			} else if (c == '\"') {
				out.append("&#34;");
			} else if (c == '<') {
				out.append("&lt;");
			} else if (c == '>') {
				out.append("&gt;");
			} else if (c == '%') {
				out.append("&#37;");
			} else if (c == '$') {
				out.append("＄");
			} else if (c >= 0 && c < 32) {
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public static String checkUnpass(String in) {
		if (in == null || in.length() == 0) {
			return in;
		}
		in = in.replaceAll("&amp;", "&").replaceAll("&", "&amp;");
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append("&#39;");
			} else if (c == '\"') {
				out.append("&#34;");
			} else if (c == '%') {
				out.append("&#37;");
			} else if (c == '$') {
				out.append("＄");
			} else if (c >= 0 && c < 32) {
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	/***************************************************************************
	 * 对过滤词汇进行预读取
	 */
	/***************************************************************************
	 * 去z
	 **************************************************************************/
	private static String removeZ(String url) {
		if (url == null || url.equals("")) {
			return url;
		}
		boolean bl = false;
		if (url.indexOf("&amp;") != -1) {
			bl = true;
			url = url.replace("&amp;", "&");
		}
		String compile = "([\\?&])z=[^&]*&{0,}";
		Pattern p = Pattern.compile(compile);
		Matcher m = p.matcher(url);
		if (m.find()) {
			url = m.replaceAll(m.group(1));
		}
		if (bl) {
			url = url.replace("&", "&amp;");
		}
		return url;
	}

	/***************************************************************************
	 * gui 2008-01-14 添加 匹配规则: 1.大小写无关 2.敏感字符中使用正则表达式无效 3.以下全、半角字符无关(以及空格)
	 * 全角！＂＃＄％＆＇（）＊＋，－．／０到９：；＜＝＞？＠Ａ到Ｚ［＼］＾＿｀ａ到ｚ｛｜｝～
	 * 半角!"#$%&'()*+,-./0到9:;<=>?@A到Z[\]^_`a到z{|}~
	 * 4.建议定义:允许每个敏感字符可以间隔N个某些指定的字符,扩大限制范围，减少敏感词汇
	 * 
	 * @author gui
	 * 
	 **************************************************************************/

	/**
	 * 替换敏感字符,如有,则会将相关的全角字符替换成半角
	 * 
	 * @param src
	 *            String 待处理字符串
	 * @param oldstr
	 *            String 查找规则
	 * @param newstr
	 *            String 预定义的新的替换字符串
	 * @return String
	 */
	private static String filter(String src, String oldstr, String newstr) {
		String newsrc = SBC2DBCcase(src);
		src = removeZ(src);
		try {
			Pattern p = Pattern.compile(oldstr, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(newsrc);
			if (m.find()) {
				return m.replaceAll(newstr);
			}
		} catch (Exception e) {
			CLogger.error("WordFilter filter failed. cause : {}", e);
		}
		return src;
	}

	/**
	 * 查找待处理字符串中是否包含了敏感字符
	 * 
	 * @param src
	 *            String 待处理字符串
	 * @param oldstr
	 *            String 查找规则
	 * @return String
	 */
	private static boolean filter(String src, String oldstr) {
		String newsrc = SBC2DBCcase(src);
		try {
			Pattern p = Pattern.compile(oldstr, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(newsrc);
			return m.find();
		} catch (Exception e) {
			CLogger.error("WordFilter filter(String src, String oldstr) failed. cause : {}", e);
		}
		return false;
	}

	/**
	 * 是否允许使用(禁止字词)
	 * 
	 * @param uid
	 *            String 用户ID
	 * @param name
	 *            String 用户呢称
	 * @return boolean true 允许使用; false 禁止使用
	 */
	public static boolean isAllowForbid(String name) {
		if (names == null || name == null || name.trim().equals("")) {
			return true;
		}
		String[] result = null;
		for (int i = 0; i < names.length; i++) {
			result = names[i];
			if (result != null && !result[0].equals("")) {
				if (filter(name, result[0])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 搜索词汇过滤 仅过滤不可搜索词汇
	 * 
	 * @param src
	 *            String 待处理的搜索字词
	 * @return String 处理后的搜索字词
	 */
	public static String filterSearch(String src) {
		if (searchs == null || src == null || src.equals("")) {
			return src;
		}
		String[] result = null;
		for (int i = 0; i < searchs.length; i++) {
			result = searchs[i];
			if (result != null && !result[0].equals("") && result[3].equals(Integer.toString(NOTSEARCH))) {
				if (filter(src, result[0])) {
					src = filter(src, result[0], result[1]);
				}
			}
		}
		return src;
	}

	/**
	 * 是否要在热门搜索列表中显示
	 * 
	 * @param src
	 *            String 待处理的搜索字词
	 * @return boolean true 不显示,false 显示
	 */
	public static boolean isNotHotSearch(String src) {
		if (searchs == null || src == null || src.equals("")) {
			return false;
		}
		String[] result = null;
		for (int i = 0; i < searchs.length; i++) {
			result = searchs[i];
			if (result != null && !result[0].equals("")) {
				if (filter(src, result[0])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 输入内容过滤
	 * 
	 * @param src
	 *            String 待处理的输入字符串
	 * @return String 处理后的字符串
	 */
	public static String filterInput(String src) {
		if (inputs == null || src == null || src.equals("")) {
			return src;
		}
		String[] result = null;
		for (int i = 0; i < inputs.length; i++) {
			result = inputs[i];
			if (result != null && !result[0].equals("")) {
				if (filter(src, result[0])) {
					src = filter(src, result[0], result[1]);
				}
			}
		}
		return src;
	}

	/**
	 * 是否允许使用(输入内容过滤)
	 * 
	 * @param src
	 *            String 待处理的输入字符串
	 * @return boolean true:允许;false:内容有敏感词汇
	 */
	public static boolean isAllowInput(String src) {
		if (inputs == null || src == null || src.equals("")) {
			return true;
		}
		String[] result = null;
		for (int i = 0; i < inputs.length; i++) {
			result = inputs[i];
			if (result != null && !result[0].equals("")) {
				if (filter(src, result[0])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 全角、半角转换 全角65281-65374 对应 半角33-126 相差:65248 其它情况:空格(全角-半角:12288-32)
	 * 
	 * @param str
	 *            String 待转字符串
	 * @return String 处理后的字符串
	 */
	private static HashMap<Integer, Character> sbc2dbc = new HashMap<Integer, Character>(); // 全角对应半角

	public static String SBC2DBCcase(String str) {
		if (str == null || str.equals("")) {
			return str;
		}
		StringBuffer bu = new StringBuffer();
		char[] ch = str.toCharArray();
		int tmp = 0;
		for (int i = 0; i < ch.length; i++) {
			tmp = (int) ch[i];
			if (tmp >= 65281 && tmp <= 65374) {
				bu.append((char) (tmp - 65248));
			} else if (sbc2dbc.containsKey(tmp)) {
				bu.append(sbc2dbc.get(tmp));
			} else {
				bu.append(ch[i]);
			}
		}
		return bu.toString();
	}

	/**
	 * 半角、全角转换 全角65281-65374 对应 半角33-126 相差:65248 其它情况:空格(全角-半角:12288-32)
	 * 
	 * @param str
	 *            String 待转字符串
	 * @return String 处理后的字符串
	 */
	private static HashMap<Integer, Character> dbc2sbc = new HashMap<Integer, Character>(); // 半角对应全角

	public static String DBC2SBCcase(String str) {
		if (str == null || str.equals("")) {
			return str;
		}
		StringBuffer bu = new StringBuffer();
		char[] ch = str.toCharArray();
		int tmp = 0;
		for (int i = 0; i < ch.length; i++) {
			tmp = (int) ch[i];
			if (tmp >= 32 && tmp <= 126) {
				bu.append((char) (tmp + 65248));
			} else if (sbc2dbc.containsKey(tmp)) {
				bu.append(dbc2sbc.get(tmp));
			} else {
				bu.append(ch[i]);
			}
		}
		return bu.toString();
	}

	/**
	 * 转义正则表达式中有特殊含义的字符
	 * 
	 * @param compile
	 * @return
	 */
	static String tranCompile(String compile) {
		if (compile == null || compile.equals("")) {
			return compile;
		}
		compile = compile.replace("\\", "\\\\");
		compile = compile.replace("[", "\\[");
		compile = compile.replace("]", "\\]");
		compile = compile.replace("*", "\\*");
		compile = compile.replace(".", "\\.");
		compile = compile.replace("(", "\\(");
		compile = compile.replace(")", "\\)");
		compile = compile.replace("+", "\\+");
		compile = compile.replace("}", "\\}");
		compile = compile.replace("{", "\\{");

		return compile;
	}
}
