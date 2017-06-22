package commons.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;

import commons.funs.Predicates;

/**
 * 正则表达式工具
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.3
 * @description 字符格式化工具类
 */
public final class MatchingUtil {

	public static final String PATTERN_NUMERAL = "[+-]?[0-9]*?[.]?[0-9]*";
	public static final String PATTERN_DECIMAL = "[+-][0][.][0-9]*";
	public static final String PATTERN_IP4 = "(([0-9]{1}|[1-9]{1}[0-9]{1}|1[0-9]{2}|2[0-4]{1}[0-9]{1}|25[0-5]{1})\\.){3}(([0-9]{1}|[1-9]{1}[0-9]{1}|1[0-9]{2}|2[0-4]{1}[0-9]{1}|25[0-5]{1}))";
	public static final String PATTERN_IP6 = "(([0-9]{1}|[1-9]{1}[0-9]{1}|1[0-9]{2}|2[0-4]{1}[0-9]{1}|25[0-5]{1})\\.){5}(([0-9]{1}|[1-9]{1}[0-9]{1}|1[0-9]{2}|2[0-4]{1}[0-9]{1}|25[0-5]{1}))";
	public static final String PATTERN_MAIL_CHINA = "(.+@[0-9|a-z|A-Z]{1,15})\\.(cn|((com|net|org)+(\\.cn)?))";
	public static final String PATTERN_MAIL_INTERNATIONAL = "(.+@[0-9|a-z|A-Z]{1,15})(\\.([a-z]|[A-Z]|[0-9]){1,5}){1,3}";
	public static final String PATTERN_MOBILE = "1[0-9]{10}";
	public static final String PATTERN_DATE = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";
	public static final String PATTERN_URL = ".*(http){0}.*\\.[a-z|0-9]{1,5}.*";
	public static final String PATTERN_HANZI = "[\\u4E00-\\u9FA5]+";
	public static final String PATTERN_IDCARD = "(^\\d{15}$)|(^\\d{17}(\\d|X)$)";

	private static Map<String, Pattern> map = new HashMap<String, Pattern>();
	static {
		map.put(PATTERN_NUMERAL, Pattern.compile(PATTERN_NUMERAL));
		map.put(PATTERN_DECIMAL, Pattern.compile(PATTERN_DECIMAL));
		map.put(PATTERN_IP4, Pattern.compile(PATTERN_IP4));
		map.put(PATTERN_IP6, Pattern.compile(PATTERN_IP6));
		map.put(PATTERN_MAIL_CHINA, Pattern.compile(PATTERN_MAIL_CHINA));
		map.put(PATTERN_MAIL_INTERNATIONAL, Pattern.compile(PATTERN_MAIL_INTERNATIONAL));
		map.put(PATTERN_MOBILE, Pattern.compile(PATTERN_MOBILE));
		map.put(PATTERN_DATE, Pattern.compile(PATTERN_DATE));
		map.put(PATTERN_URL, Pattern.compile(PATTERN_URL));
		map.put(PATTERN_HANZI, Pattern.compile(PATTERN_HANZI));
		map.put(PATTERN_IDCARD, Pattern.compile(PATTERN_IDCARD));
	}

	/**
	 * 是否匹配
	 * 
	 * @param src
	 * @param pattern
	 * @return
	 */
	public static boolean isMatch(String src, String regex) {
		if (src == null || regex == null)
			return false;
		Pattern pattern = map.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
			map.put(regex, pattern);
		}
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	/**
	 * 是否包含
	 * 
	 * @param src
	 * @param pattern
	 * @return
	 */
	public static boolean isFind(String src, String regex) {
		if (src == null || regex == null)
			return false;
		Pattern pattern = map.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
		}
		Matcher matcher = pattern.matcher(src);
		return matcher.find();
	}

	/**
	 * 替换所有
	 * 
	 * @param src
	 * @param pattern
	 * @return
	 */
	public static String replaceAll(String src, String regex, String replacement) {
		if (src == null || regex == null)
			return src;
		Pattern pattern = map.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
		}
		Matcher matcher = pattern.matcher(src);
		return matcher.replaceAll(replacement);
	}

	/**
	 * 替换第一个
	 * 
	 * @param src
	 * @param pattern
	 * @return
	 */
	public static String replaceFirst(String src, String regex, String replacement) {
		if (src == null || regex == null)
			return src;
		Pattern pattern = map.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
		}
		Matcher matcher = pattern.matcher(src);
		return matcher.replaceFirst(replacement);
	}

	/**
	 * 非Null非空
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isAble(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		return true;
	}

	/**
	 * 是否数字
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isNumeral(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_NUMERAL);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	/**
	 * 是否小数
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isDecimalFraction(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_DECIMAL);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	/**
	 * 是否符合IP4
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isIP4(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_IP4);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	/**
	 * 是否符合IP6
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isIP6(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_IP6);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	/**
	 * 是否国内邮箱地址<br/>
	 * 可识别com,net,org
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isMail(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_MAIL_CHINA);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	/**
	 * 是否邮箱地址<br/>
	 * 识别所有邮箱,最多三级域名
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isMail_INTL(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_MAIL_INTERNATIONAL);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	/**
	 * 是否手机号码
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isMobile(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_MOBILE);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	public static boolean isDate(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_DATE);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}

	public static boolean hasURL(String src) {
		if (src == null || src.trim().equals(""))
			return false;
		Pattern pattern = map.get(PATTERN_URL);
		Matcher matcher = pattern.matcher(src.replaceAll(" ", "").toLowerCase());
		return matcher.matches();
	}

	public static String hasURL(String src, String target) {
		if (src == null || src.trim().equals(""))
			return src;
		Pattern pattern = map.get(PATTERN_URL);
		Matcher matcher = pattern.matcher(src.replaceAll(" ", "").toLowerCase());
		return matcher.replaceAll(target);
	}

	/**
	 * 是否合法身份证
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean isLegalID(String idCard) {
		// 身份证15位时，次序为 省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
		// 身份证18位时，次序为 省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
		return Predicates.noEmpty.convert()
								.and((ic) -> map.get(PATTERN_IDCARD).matcher(ic).matches())
								.and((ic) -> PROVINCE.get(ic.substring(0, 2)) != null)
								.and(MatchingUtil::vrfBirthday)
								.and(MatchingUtil::vrfParity)
								.test(idCard.trim().toUpperCase().replaceAll(" ", ""));
	}
	public static void main(String[] args){
		System.out.println(isLegalID("420881198408290713"));
	}
	private static boolean vrfBirthday(String idCard){
		String birthdayStr = idCard.length() == 18 ? idCard.substring(6, 14) : ("19" + idCard.substring(6, 12));
		Calendar birthday = Calendar.getInstance();
		try {
			birthday.setTime(DateUtils.parseDate(birthdayStr, "yyyyMMdd"));
		} catch (ParseException e) {
			return false;
		}
		return birthday.before(Calendar.getInstance());
	}
	private static final int[] IDCARD_VRFPARITY_ARR_INT=new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
	private static final String[] IDCARD_VRFPARITY_ARR_STR=new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
	private static boolean vrfParity(String idCard){
		if(idCard.length()==18){
	        char[] idCardCharArray=idCard.toCharArray();
	        int parityCode = 0;
	        for (int i = 0; i < 17; i++) {
	        	parityCode += Character.getNumericValue(idCardCharArray[i]) * IDCARD_VRFPARITY_ARR_INT[i];
	        }
	        return idCard.substring(17, 18).equals(IDCARD_VRFPARITY_ARR_STR[parityCode % 11]);
		}
		return true;
	}
	static Map<String, String> PROVINCE = new HashMap<String, String>();
	static {
		PROVINCE.put("11", "北京");
		PROVINCE.put("12", "天津");
		PROVINCE.put("13", "河北");
		PROVINCE.put("14", "山西");
		PROVINCE.put("15", "内蒙古");
		PROVINCE.put("21", "辽宁");
		PROVINCE.put("22", "吉林");
		PROVINCE.put("23", "黑龙江");
		PROVINCE.put("31", "上海");
		PROVINCE.put("32", "江苏");
		PROVINCE.put("33", "浙江");
		PROVINCE.put("34", "安徽");
		PROVINCE.put("35", "福建");
		PROVINCE.put("36", "江西");
		PROVINCE.put("37", "山东");
		PROVINCE.put("41", "河南");
		PROVINCE.put("42", "湖北");
		PROVINCE.put("43", "湖南");
		PROVINCE.put("44", "广东");
		PROVINCE.put("45", "广西");
		PROVINCE.put("46", "海南");
		PROVINCE.put("50", "重庆");
		PROVINCE.put("51", "四川");
		PROVINCE.put("52", "贵州");
		PROVINCE.put("53", "云南");
		PROVINCE.put("54", "西藏");
		PROVINCE.put("61", "陕西");
		PROVINCE.put("62", "甘肃");
		PROVINCE.put("63", "青海");
		PROVINCE.put("64", "宁夏");
		PROVINCE.put("65", "新疆");
		PROVINCE.put("71", "台湾");
		PROVINCE.put("81", "香港");
		PROVINCE.put("82", "澳门");
		PROVINCE.put("91", "国外");
	}
}
