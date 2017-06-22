package commons.lang;

import org.apache.commons.lang3.math.NumberUtils;

import commons.codec.Charset;

/**
 * 
 * @author bailey_fu
 * @data 2013-12-9
 * @version 1.0
 * @Description
 */
public final class StringUtils {

	public static final String TRUE = new String("true");
	public static final String FALSE = new String("false");
	private static String BLANK = "";
	private static String defaultRegex = ",";
	private static String defaultMark = "|";

	public static String defaultString(Object target) {
		return target == null ? BLANK : target.toString();
	}

	public static String defaultString(Object target, String defaultStr) {
		return target == null ? defaultStr : target.toString();
	}

	public static Boolean getBoolean(String src, boolean... defaultValue) {
		if (src == null)
			return defaultValue.length > 0 ? defaultValue[0] : null;
		if (src.trim().toLowerCase().equals(TRUE)) {
			return true;
		}
		if (src.trim().toLowerCase().equals(FALSE)) {
			return false;
		}
		return defaultValue.length > 0 ? defaultValue[0] : null;
	}

	/**
	 * 
	 * @param obj
	 * @param codes
	 *            <br/>
	 *            单参数默认为targetCode<br/>
	 *            codes[0] : sourceCode<br/>
	 *            codes[1] : targetCode
	 * @return null返回"" , 无codes则返回obj.toString() , 异常则返回null
	 */
	public static String defaultStringEncoding(Object obj, Charset... codes) {
		if (obj == null)
			return org.apache.commons.lang3.StringUtils.EMPTY;
		if (codes.length > 0) {
			try {
				if (codes.length == 1) {
					return new String(obj.toString().getBytes(), codes[0].VALUE);
				} else if (codes[1] == null) {
					return new String(obj.toString().getBytes(codes[0].VALUE));
				} else {
					return new String(obj.toString().getBytes(codes[0].VALUE), codes[1].VALUE);
				}
			} catch (Exception e) {
				return null;
			}
		}
		return obj.toString();
	}

	public static String defaultDeleteForArray(String[] src, int index) {
		return deleteForArray(src, index, defaultMark);
	}

	public static String deleteForArray(String[] src, int index) {
		return deleteForArray(src, index, null);
	}

	/**
	 * 删除数组中某个元素并返回数组的字符串形式
	 * 
	 * @param src
	 * @param index
	 *            从0开始计算
	 * @param mark
	 *            元素之间的分隔符号
	 * @return
	 */
	public static String deleteForArray(String[] src, int index, String mark) {
		mark = org.apache.commons.lang3.StringUtils.defaultString(mark);
		int length = src.length;
		StringBuilder tem = new StringBuilder();
		for (int i = 0; i < length; i++) {
			String str = src[i];
			if (index != i) {
				tem.append(str).append(mark);
			}
		}
		return tem.length() > 0 ? (org.apache.commons.lang3.StringUtils.isEmpty(mark) ? tem.toString() : tem.substring(0, tem.length() - 1))
				: org.apache.commons.lang3.StringUtils.EMPTY;
	}

	/**
	 * 指定字符长度，不足则补充<br/>
	 * 默认：不足不补充
	 * 
	 * @param src
	 * @param limit
	 * @return
	 */
	public static String getLMTString(Object src, int limit) {
		return getLMTString(src, limit, true, true, null);
	}

	/**
	 * 指定字符长度，不足则补充<br/>
	 * 默认:补充在最后
	 * 
	 * @param src
	 * @param limit
	 *            限制的长度
	 * @param supplement
	 *            不足补充
	 * @return
	 */
	public static String getLMTString(Object src, int limit, String supplement) {
		return getLMTString(src, limit, true, true, supplement);
	}

	/**
	 * 指定字符长度，不足则补充
	 * 
	 * @param src
	 * @param limit
	 * @param isBack
	 *            是否补充在最后
	 * @param supplement
	 * @return
	 */
	public static String getLMTString(Object src, int limit, boolean isBack, String supplement) {
		return getLMTString(src, limit, true, isBack, supplement);
	}

	/**
	 * 指定字符长度，不足则补充,超过则截取
	 * 
	 * @param src
	 * @param limit
	 * @param frontBegin
	 *            若超过,是否从前往后截取
	 * @param isBack
	 * @param supplement
	 * @return
	 */
	public static String getLMTString(Object src, int limit, boolean frontBegin, boolean isBack, String supplement) {
		if (src == null)
			return null;
		String target = src.toString();
		int diffValue = target.length() - limit;
		if (diffValue == 0)
			return target;
		else if (diffValue > 0)
			return frontBegin ? target.substring(0, limit) : target.substring(diffValue, target.length());
		supplement = supplement == null ? "" : supplement;
		if (supplement.equals(""))
			return target;
		if (isBack) {
			do {
				target += supplement;
			} while (target.length() < limit);
		} else {
			do {
				target = supplement + target;
			} while (target.length() < limit);
		}
		return target.length() == limit ? target : target.substring(0, limit);
	}

	/**
	 * 按指定长度分解字符为字符数组<br/>
	 * 默认：忽略空字符，末尾不足不补充
	 * 
	 * @param src
	 * @param partLength
	 * @return
	 */
	public static String[] splitByRange(String src, int partLength) {
		return splitByRange(src, partLength, true, null);
	}

	/**
	 * 按指定长度分解字符为字符数组
	 * 
	 * @param src
	 *            源字符
	 * @param partLength
	 *            每部分长度
	 * @param ignore
	 *            是否忽略空字符
	 * @param supplement
	 *            末尾不足补充字符
	 * @return
	 */
	public static String[] splitByRange(String src, int partLength, boolean ignore, String supplement) {
		if (src == null)
			return null;
		src = src.trim();
		if (ignore == true)
			src = src.replaceAll(" ", "");
		partLength = partLength < 1 ? 1 : partLength;
		int arrayLength = src.length() % partLength == 0 ? src.length() / partLength : (src.length() / partLength + 1);
		String[] result = new String[arrayLength];
		int startLocation = 0;
		String tempString;
		int tempLocation;
		for (int index = 0; index < arrayLength - 1; index++) {
			tempLocation = startLocation + partLength;
			tempString = src.substring(startLocation, tempLocation);
			startLocation = tempLocation;

			result[index] = tempString;
		}
		tempString = src.substring(startLocation, src.length());
		int scarcityLength = partLength - tempString.length();
		if (scarcityLength > 0 && supplement != null && !supplement.equals("")) {
			StringBuilder temp = new StringBuilder();
			temp.append(tempString);
			for (int i = 0; i < scarcityLength; i++)
				temp.append(supplement);
			tempString = temp.toString().substring(0, partLength);
		}
		result[arrayLength - 1] = tempString;
		return result;
	}

	/**
	 * 按指定长度分解字符为整型数字数组<br/>
	 * 默认：按累加不足补0
	 * 
	 * @param src
	 * @param partLength
	 * @param defaultInt
	 * @return
	 */
	public static int[] splitByRange(String src, int partLength, int defaultInt) {
		return splitByRange(src, partLength, defaultInt, "0", false);
	}

	/**
	 * 按指定长度分解字符为整型数字数组
	 * 
	 * @param src
	 *            源字符
	 * @param partLength
	 *            每部分长度<br/>
	 *            不能超过Integer所支持的长度
	 * @param defaultInt
	 *            分解得到非数字时的默认数值
	 * @param supplement
	 *            末尾不足补充数字
	 * @param isAddition
	 *            是否按加法计算
	 * @return
	 */
	public static int[] splitByRange(String src, int partLength, int defaultInt, String supplement, boolean isAddition) {
		if (src == null)
			return null;
		src = src.replaceAll(" ", "");
		partLength = partLength < 1 ? 1 : (partLength > 9 ? 9 : partLength);
		int arrayLength = src.length() % partLength == 0 ? src.length() / partLength : (src.length() / partLength + 1);
		int[] result = new int[arrayLength];
		int startLocation = 0;
		String tempString;
		int tempLocation;
		for (int index = 0; index < arrayLength - 1; index++) {
			tempLocation = startLocation + partLength;
			tempString = src.substring(startLocation, tempLocation);
			startLocation = tempLocation;

			result[index] = NumberUtils.toInt(tempString, defaultInt);
		}
		tempString = src.substring(startLocation, src.length());
		int scarcityLength = partLength - tempString.length();
		if (scarcityLength > 0 && supplement != null && !supplement.equals("")) {
			if (isAddition) {
				int supplementInt = NumberUtils.toInt(supplement);
				tempString = String.valueOf(NumberUtils.toInt(tempString, defaultInt) + supplementInt);
			} else {
				StringBuilder temp = new StringBuilder();
				temp.append(tempString);
				for (int i = 0; i < scarcityLength; i++)
					temp.append(supplement);
				tempString = temp.toString().substring(0, partLength);
			}
			if (tempString.length() > partLength)
				tempString = tempString.substring(0, partLength);
		}
		result[arrayLength - 1] = NumberUtils.toInt(tempString, defaultInt);
		return result;

	}

	/**
	 * 按指定标记将字符分解成整数数组<br/>
	 * 默认：分隔符为[,]；非数字字符则数值为0
	 * 
	 * @param src
	 * @return
	 */
	public static int[] split(String src) {
		return split(src, defaultRegex, 0);
	}

	/**
	 * 按指定标记将字符分解成整数数组<br/>
	 * 默认：分隔符为[,]
	 * 
	 * @param src
	 * @param defaultInt
	 * @return
	 */
	public static int[] split(String src, int defaultInt) {
		return split(src, defaultRegex, defaultInt);
	}

	/**
	 * 按指定标记将字符分解成整数数组<br/>
	 * 默认：非数字字符则数值为0
	 * 
	 * @param src
	 * @param regex
	 * @return
	 */
	public static int[] split(String src, String regex) {
		return split(src, regex, 0);
	}

	/**
	 * 按指定标记将字符分解成整数数组
	 * 
	 * @param src
	 * @param regex
	 *            分解标记
	 * @param defaultInt
	 *            非数字默认数值
	 * @return
	 */
	public static int[] split(String src, String regex, int defaultInt) {
		if (src == null)
			return null;
		String[] temp = org.apache.commons.lang3.StringUtils.split(src, regex);
		int length = temp.length;
		int[] result = new int[length];
		for (int index = 0; index < length; index++) {
			result[index] = NumberUtils.toInt(temp[index].replaceAll(" ", ""), defaultInt);
		}
		return result;
	}

	public static String[] split4site(String src, int[] regexSites) {
		return split4site(src, defaultRegex, regexSites);
	}

	/**
	 * 根据指定字符位置划分
	 * 
	 * @param src
	 *            源
	 * @param regex
	 *            分解标记
	 * @param regexSites
	 *            标记位置
	 * @return
	 */
	public static String[] split4site(String src, String regex, int[] regexSites) {
		if (src == null)
			return null;
		if (regexSites == null || regexSites.length == 0 || src.indexOf(regex) == -1)
			return new String[] { src };
		String[] result = new String[regexSites.length + 1];
		String temp = src;
		String temp_ = null;
		int i = 0;
		int siteCounter = 1;
		OUTER: for (int site : regexSites) {
			if (site < 1)
				continue;
			int position = 0;
			int p = 0;
			temp_ = temp;
			do {
				position = temp.indexOf(regex);
				if (position == -1)
					break OUTER;
				p += position + 1;
				siteCounter++;
				temp = temp.substring(position + 1, temp.length());
			} while (siteCounter == site);
			result[i++] = temp_.substring(0, p - 1);
		}
		result[i] = temp;
		return result;
	}

	/**
	 * 按指定标记将字符分解成整数数组<br/>
	 * 默认：分隔符为[,]；非数字字符则数值为0
	 * 
	 * @param src
	 * @return
	 */
	public static long[] split2long(String src) {
		return split2long(src, defaultRegex, 0l);
	}

	/**
	 * 按指定标记将字符分解成整数数组<br/>
	 * 默认：分隔符为[,]
	 * 
	 * @param src
	 * @param defaultLong
	 * @return
	 */
	public static long[] split2long(String src, long defaultLong) {
		return split2long(src, defaultRegex, defaultLong);
	}

	/**
	 * 按指定标记将字符分解成整数数组<br/>
	 * 默认：非数字字符则数值为0
	 * 
	 * @param src
	 * @param regex
	 * @return
	 */
	public static long[] split2long(String src, String regex) {
		return split2long(src, regex, 0l);
	}

	/**
	 * 按指定标记将字符分解成整数数组
	 * 
	 * @param src
	 * @param regex
	 *            分解标记
	 * @param defaultLong
	 *            非数字默认数值
	 * @return
	 */
	public static long[] split2long(String src, String regex, long defaultLong) {
		if (src == null)
			return null;
		String[] temp = src.split(regex);
		int length = temp.length;
		long[] result = new long[length];
		for (int index = 0; index < length; index++) {
			result[index] = NumberUtils.toLong(temp[index].replaceAll(" ", ""), defaultLong);
		}
		return result;
	}

	/**
	 * 按指定标记将字符分解成Double数组<br/>
	 * 默认：分隔符为[,]；非数字字符则数值为0
	 * 
	 * @param src
	 * @return
	 */
	public static double[] split2double(String src) {
		return split2double(src, defaultRegex, 0d);
	}

	/**
	 * 按指定标记将字符分解成Double数组<br/>
	 * 默认：分隔符为[,]
	 * 
	 * @param src
	 * @param defaultLong
	 * @return
	 */
	public static double[] split2double(String src, double defaultDouble) {
		return split2double(src, defaultRegex, defaultDouble);
	}

	/**
	 * 按指定标记将字符分解成Double数组<br/>
	 * 默认：非数字字符则数值为0
	 * 
	 * @param src
	 * @param regex
	 * @return
	 */
	public static double[] split2double(String src, String regex) {
		return split2double(src, regex, 0d);
	}

	/**
	 * 按指定标记将字符分解成Double数组
	 * 
	 * @param src
	 * @param regex
	 *            分解标记
	 * @param defaultLong
	 *            非数字默认数值
	 * @return
	 */
	public static double[] split2double(String src, String regex, double defaultDouble) {
		if (src == null)
			return null;
		String[] temp = src.split(regex);
		int length = temp.length;
		double[] result = new double[length];
		for (int index = 0; index < length; index++) {
			result[index] = NumberUtils.toDouble(temp[index].replaceAll(" ", ""), defaultDouble);
		}
		return result;
	}

	/**
	 * 字符排重
	 * 
	 * @param src
	 * @return
	 */
	public static String removeDuplicate(String src) {
		StringBuilder temp = new StringBuilder();
		if (src != null) {
			char[] charArray = src.replaceAll(" ", "").trim().toCharArray();
			if (charArray.length > 0) {
				for (char c : charArray) {
					if (temp.indexOf(String.valueOf(c)) == -1) {
						temp.append(c);
					}
				}
			}
		}
		return temp.toString();
	}

	// public static String cn2Spell(String containedChinese) throws
	// BadHanyuPinyinOutputFormatCombination {
	// return cn2Spell(containedChinese, false, false);
	// }
	//
	// public static String cn2Spell(String containedChinese, boolean
	// toUpperCase) throws BadHanyuPinyinOutputFormatCombination {
	// return cn2Spell(containedChinese, false, toUpperCase);
	// }

	/**
	 * 包含的中文转换为拼音
	 * 
	 * @param containedChinese
	 * @param first2upperCase
	 *            首字母大写
	 * @param toUpperCase
	 *            是否大写
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	// public static String cn2Spell(String containedChinese, boolean
	// first2upperCase, boolean toUpperCase) throws
	// BadHanyuPinyinOutputFormatCombination {
	// StringBuilder pybf = new StringBuilder();
	// char[] arr = containedChinese.toCharArray();
	// HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	// defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	// defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	// defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	// if (toUpperCase) {
	// defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	// } else if (first2upperCase) {
	// for (int i = 0; i < arr.length; i++) {
	// if (String.valueOf(arr[i]).matches(MatchingUtil.PATTERN_HANZI)) {
	// String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
	// defaultFormat);
	// if (_t != null) {
	// for (String _s : _t) {
	// char[] ca = _s.toCharArray();
	// for (int j = 0; j < ca.length; j++) {
	// if (j == 0) {
	// pybf.append(String.valueOf(ca[j]).toUpperCase());
	// } else {
	// pybf.append(ca[j]);
	// }
	// }
	// }
	// }
	// } else {
	// pybf.append(arr[i]);
	// }
	// }
	// return pybf.toString();
	// }
	// for (int i = 0; i < arr.length; i++) {
	// if (String.valueOf(arr[i]).matches(MatchingUtil.PATTERN_HANZI)) {
	// pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i],
	// defaultFormat)[0]);
	// } else {
	// pybf.append(arr[i]);
	// }
	// }
	// return pybf.toString();
	// }
	//
	// public static String cn2SpellABrief(String containedChinese) throws
	// BadHanyuPinyinOutputFormatCombination {
	// return cn2SpellABrief(containedChinese, false, false);
	// }
	//
	// public static String cn2SpellABrief(String containedChinese, boolean
	// toUpperCase) throws BadHanyuPinyinOutputFormatCombination {
	// return cn2SpellABrief(containedChinese, false, toUpperCase);
	// }

	/**
	 * 只取拼音首字母
	 * 
	 * @param containedChinese
	 * @param first2upperCase
	 *            第一个大写
	 * @param toUpperCase
	 *            是否大写
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	// public static String cn2SpellABrief(String containedChinese, boolean
	// first2upperCase, boolean toUpperCase) throws
	// BadHanyuPinyinOutputFormatCombination {
	// StringBuilder pybf = new StringBuilder();
	// char[] arr = containedChinese.toCharArray();
	// HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	// defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	// defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	// defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	// if (toUpperCase) {
	// defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	// } else if (first2upperCase) {
	// boolean isFirst = true;
	// for (int i = 0; i < arr.length; i++) {
	// if (String.valueOf(arr[i]).matches(MatchingUtil.PATTERN_HANZI)) {
	// String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
	// defaultFormat);
	// if (_t != null) {
	// if (isFirst) {
	// pybf.append(String.valueOf(_t[0].toCharArray()[0]).toUpperCase());
	// } else {
	// pybf.append(String.valueOf(_t[0].toCharArray()[0]));
	// }
	// }
	// isFirst = false;
	// } else {
	// pybf.append(arr[i]);
	// }
	// }
	// return pybf.toString();
	// }
	// for (int i = 0; i < arr.length; i++) {
	// if (String.valueOf(arr[i]).matches(MatchingUtil.PATTERN_HANZI)) {
	// String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
	// defaultFormat);
	// if (_t != null) {
	// pybf.append(_t[0].toCharArray()[0]);
	// }
	// } else {
	// pybf.append(arr[i]);
	// }
	// }
	// return pybf.toString();
	// }
}
