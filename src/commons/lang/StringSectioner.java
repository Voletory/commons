package commons.lang;

import java.util.ArrayList;
import java.util.List;

import commons.log.XLogger;

/**
 * 字符串分割
 * 
 * @author bailey_fu
 * @data 2014-1-6
 * @version 1.0
 * @Description
 */
public class StringSectioner {
	private int pageSize;// 每页的字数
	private String content;// 进行分页的内容
	private String ubbCode;// 需要进行解析的ubb代码
	private List<Integer> pagePos = new ArrayList<Integer>();// 存放每页的断点位置

	/**
	 * 初始化设置
	 * 
	 * @param content
	 *            原始字符串
	 * @param length
	 *            要截取的长度
	 */
	public StringSectioner(String content, int pageSize) {
		this.pageSize = pageSize;
		this.content = content;
	}

	/**
	 * 初始化设置
	 * 
	 * @param content
	 *            原始字符串
	 * @param pageSize
	 *            要截取的长度
	 * @param ubbCode
	 *            文章中用到的ubb代码
	 */
	public StringSectioner(String content, int pageSize, String ubbCode) {
		this.pageSize = pageSize;
		this.content = content;
		this.ubbCode = ubbCode;
	}

	/**
	 * 获取某一页的内容，如果是最后一页，则截取剩余部分
	 * 
	 * @param pageNum
	 *            页码
	 * @return String
	 */
	public String getString(int pageNum) {
		try {
			return this.markContentAndCut(this.content, this.pageSize, pageNum, this.ubbCode);
		} catch (Exception e) {
			XLogger.error("StringSectioner getString failed. cause : {}", e);
		}
		return this.content;
	}

	/**
	 * 判断某一页是否为最后一页，如果是，返回true，如果否，返回false
	 * 
	 * @param pageNum
	 *            页码
	 * @return boolean
	 */
	public boolean reachToEnd(int pageNum) {
		if (pagePos.size() - 1 <= pageNum) {
			return true;
		}
		return false;
	}

	/**
	 * 判断某一页是否为第一页，如果是，返回true,如果否,返回false
	 * 
	 * @param pageNum
	 *            页码
	 * @return boolean
	 */
	public boolean reachToHead(int pageNum) {
		if (pageNum <= 1) {
			return true;
		}
		return false;
	}

	/**
	 * 获取最大页码
	 */
	public int getMaxPage() {
		return pagePos.size() - 1;
	}

	/**
	 * 对需要进行分页的字符串标注分页分隔符
	 * 
	 * @param content
	 *            要进行分页的字符串
	 * @param pageSize
	 *            每页文章的长度
	 * @param pageNum
	 *            页码
	 * @param ubbCode
	 *            每页包含的可以解析的ubb代码
	 * @return
	 */
	public String markContentAndCut(String content, int pageSize, int pageNum, String ubbCode) {
		pagePos.clear();
		pagePos.add(0);
		// 字符串长度小于每页长度，直接返回不作任何的操作包括标注
		if (content.length() <= pageSize) {
			return content;
		}
		int length = content.length();
		int startPos = 0;// 当前指针所在位置，直接定位到每页的最大长度
		int endPos = 0;
		do {
			endPos = startPos + pageSize;
			if (endPos > length) {
				endPos = length;
				pagePos.add(endPos);
				break;
			}
			boolean isnormal = true;// 是否按照普通文本正常截取，0：正常截取，1：非正常
			String curStr = content.substring(startPos, endPos);
			String[] ubb = ubbCode.split("\\|");
			if (ubb != null && ubb.length > 0) {
				for (int i = 0; i < ubb.length; i++) {
					int tailFirst = curStr.lastIndexOf("[/" + ubb[i] + "]");// 判断该段文本中出现[/ubb]的位置
					int headFirst = curStr.lastIndexOf("[" + ubb[i]);// 判断该段文本中出现[ubb]的位置
					// 字符串中不包含ubbcode,正常截断字符串
					if (headFirst == -1 && tailFirst == -1) {
						continue;
					}
					// 正常截取，没有截断ubbcode，按照正常方式截取
					if (headFirst != -1 && tailFirst != -1 && headFirst < tailFirst) {
						continue;
					}
					// 只存在ubb结尾，或者ubb被截断
					if (tailFirst == -1 || tailFirst < headFirst) {
						// 以本段结尾位置减去[url]的最大长度6为起点，找到ubb结尾的位置，并将该位置记录下来，作为分页的起点
						String nextStr = content.substring(endPos - 6);
						// 找到ubb结尾出现的第一个位置，并将该位置作为分页的终点
						int tailPos = nextStr.indexOf("[/" + ubb[i] + "]");
						if (tailPos != -1) {
							isnormal = false;
							endPos = endPos - 6 + tailPos + ubb[i].length() + 3;
							startPos = endPos + 1;
							pagePos.add(endPos);// 将结束位置加入分页位置列表
						}
					}
				}
			}
			if (isnormal) {
				endPos = startPos + pageSize;
				pagePos.add(endPos);
				startPos = endPos + 1;
			}
		} while (endPos < length);
		if (pagePos.size() - 1 < pageNum) {
			pageNum = pagePos.size() - 1;
		}
		startPos = (int) pagePos.get(pageNum - 1);
		endPos = (int) pagePos.get(pageNum);
		return content.substring(startPos, endPos);
	}
}
