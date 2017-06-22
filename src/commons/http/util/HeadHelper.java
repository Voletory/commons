package commons.http.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * Http请求头辅助类
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.0
 * @description
 */
public class HeadHelper {
	/** UA */
	private String userAgent;
	/** IMSI号码 */
	private String imsi;
	/** 手机号码 */
	private String mdn;
	private String ip;

	private HeadHelper() {
	}

	public String getIMSI() {
		if (imsi == null || imsi == "") {
			return null;
		} else {
			return imsi;
		}
	}

	public String getMDN() {
		if (mdn == null || mdn == "") {
			return null;
		} else {
			return mdn.trim();
		}
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getIP() {
		return ip;
	}

	public static HeadHelper buildHeadUtil(HttpServletRequest request) {
		HeadHelper headUtil = new HeadHelper();
		boolean isGetUA = false;
		boolean isGetIMSI = false;
		boolean isGetMDN = false;

		Enumeration<?> enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement().toString();
			// IP
			if (name.toLowerCase().equals("x-forwarded-for".toLowerCase())) {
				headUtil.ip = request.getHeader(name);
			}
			// 获得UA
			if (!isGetUA && name.toLowerCase().equals("User-Agent".toLowerCase())) {
				headUtil.userAgent = request.getHeader(name);
				isGetUA = true;
			}
			if (!isGetUA && name.toLowerCase().equals("UserAgent".toLowerCase())) {
				headUtil.userAgent = request.getHeader(name);
				isGetUA = true;
			}
			// 获得IMSI
			if (!isGetIMSI && name.toLowerCase().equals("x-IMSI".toLowerCase())) {
				headUtil.imsi = request.getHeader(name);
				isGetIMSI = true;
			}
			if (!isGetIMSI && name.toLowerCase().equals("IMSI".toLowerCase())) {
				headUtil.imsi = request.getHeader(name);
				isGetIMSI = true;
			}
			if (!isGetIMSI && name.toLowerCase().equals("x-nokia-imsi".toLowerCase())) {
				headUtil.imsi = request.getHeader(name);
				isGetIMSI = true;
			}
			// 获得手机号码
			if (!isGetMDN && name.toLowerCase().equals("x-MDN".toLowerCase())) {
				headUtil.mdn = request.getHeader(name);
				isGetMDN = true;
			}
			if (!isGetMDN && name.toLowerCase().equals("x-up-calling-line-id".toLowerCase())) {
				headUtil.mdn = request.getHeader(name);
				isGetMDN = true;
			}
			// if(!isgetMDN &&
			// name.toLowerCase().equals("x-msisdn".toLowerCase())){
			// mdn = request.getHeader(name);
			// isgetMDN = true;
			// }
			if (!isGetMDN && name.toLowerCase().equals("x-nokia-msisdn".toLowerCase())) {
				headUtil.mdn = request.getHeader(name);
				isGetMDN = true;
			}
			if (!isGetMDN && name.toLowerCase().equals("x-h3g-msisdn".toLowerCase())) {
				headUtil.mdn = request.getHeader(name);
				isGetMDN = true;
			}
			if (!isGetMDN && name.toLowerCase().equals("x-surfopen-msisdn".toLowerCase())) {
				headUtil.mdn = request.getHeader(name);
				isGetMDN = true;
			}
			if (!isGetMDN && name.toLowerCase().equals("x-drutt-portal-user-msisdn".toLowerCase())) {
				headUtil.mdn = request.getHeader(name);
				isGetMDN = true;
			}
		}
		return headUtil;

	}

	public static String getIP(HttpServletRequest request) {
		String[] ips = null;
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.length() > 0) {
			ips = ip.split(",");
		}
		return ips != null && ips.length > 0 ? ips[0] : "";
	}

	/**
	 * supported Wap's type
	 * 
	 * @param ua
	 * @return 0:all<br/>
	 *         1:only wap1.0<br/>
	 *         2:only wap2.0<br/>
	 */
	public static int getWapAble(HttpServletRequest request) {
		String ua = null;
		Enumeration<?> enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement().toString();

			if (name.toLowerCase().equals("User-Agent".toLowerCase())) {
				ua = request.getHeader(name);
				break;
			}
			if (name.toLowerCase().equals("UserAgent".toLowerCase())) {
				ua = request.getHeader(name);
				break;
			}
		}
		if (ua != null) {
			ua = ua.toLowerCase();
		}
		if (ua == null) {
			return 2;
		} else if (ua.indexOf("chrome") != -1) {
			return 2;
		} else if (ua.indexOf("opera") != -1) {// opera
			return 0;
		} else if (ua.indexOf("ucweb") != -1) {// uc浏览器
			return 0;
		} else if (ua.indexOf("msie") != -1) {// ie
			return 2;
		} else if (ua.indexOf("safari") != -1) {// 苹果自带
			return 2;
		} else if (ua.indexOf("firefox") != -1) {// 火狐
			return 2;
		} else if (ua.indexOf("gecko") != -1) {
			return 2;
		} else if (ua.indexOf("360") != -1) {// 360
			return 2;
		} else if (ua.indexOf("theworld") != -1) {// 世界之窗
			return 2;
		} else if (ua.indexOf("qqbrowser") != -1) {// qq浏览器
			return 2;
		} else if (ua.indexOf("android") != -1) {// android
			return 2;
		} else if (ua.indexOf("linux") != -1) {// linux
			return 2;
		} else {
			return 1;
		}
	}

}
