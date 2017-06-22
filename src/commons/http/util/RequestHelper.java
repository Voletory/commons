package commons.http.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import commons.log.CLogger;

/**
 * Http请求对象Request辅助类
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.0
 * @description
 */
public class RequestHelper {

	private HttpServletRequest request;
	private String defaultValue_STRING;
	private int defaultValue_INT;

	private RequestHelper(HttpServletRequest request) {
		this.request = request;
		defaultValue_STRING = StringUtils.EMPTY;
		defaultValue_INT = 0;
	}

	private RequestHelper(HttpServletRequest request, String defaultValue_STRING) {
		this.request = request;
		this.defaultValue_STRING = defaultValue_STRING;
		defaultValue_INT = 0;
	}

	private RequestHelper(HttpServletRequest request, int defaultValue_INT) {
		this.request = request;
		this.defaultValue_STRING = "";
		this.defaultValue_INT = defaultValue_INT;
	}

	private RequestHelper(HttpServletRequest request, String defaultValue_STRING, int defaultValue_INT) {
		this.request = request;
		this.defaultValue_STRING = defaultValue_STRING;
		this.defaultValue_INT = defaultValue_INT;
	}

	public static RequestHelper buildRequestHelper(HttpServletRequest request) {
		return new RequestHelper(request);
	}

	public static RequestHelper buildRequestHelper(HttpServletRequest request, String defaultValue_STRING) {
		return new RequestHelper(request, defaultValue_STRING);
	}

	public static RequestHelper buildRequestHelper(HttpServletRequest request, int defaultValue_INT) {
		return new RequestHelper(request, defaultValue_INT);
	}

	public static RequestHelper buildRequestHelper(HttpServletRequest request, String defaultValue_STRING, int defaultValue_INT) {
		return new RequestHelper(request, defaultValue_STRING, defaultValue_INT);
	}

	public int getInt(String paramName) {
		try {
			return Integer.parseInt(request.getParameter(paramName));
		} catch (Exception e) {
			return defaultValue_INT;
		}
	}

	public int getInt(String paramName, int defaultValue) {
		try {
			return Integer.parseInt(request.getParameter(paramName));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getString(String paramName) {
		return request.getParameter(paramName) == null ? defaultValue_STRING : request.getParameter(paramName);
	}

	public String getString(String paramName, String defaultValue) {
		return request.getParameter(paramName) == null ? defaultValue : request.getParameter(paramName);
	}

	/** -------------------------StaticMethod----------------------------- */

	public static void printRequest(HttpServletRequest request) {
		System.out.println("----------Request Content Start--------------");
		System.out.println("Header:");
		Enumeration<?> enu = request.getHeaderNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			System.out.println(name + ":" + request.getHeader(name));
		}
		System.out.println("Parameter:");
		enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			System.out.println(name + ":" + request.getParameter(name));
		}
		System.out.println("----------Request Content End--------------");
	}

	public static String getMethod(String getURL) {
		String sRet = StringUtils.EMPTY;
		try {

			URL url;
			URLConnection urlconn;
			try {
				url = new URL(getURL);
				urlconn = url.openConnection();
				urlconn.connect();
				BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
				String str = null;
				while (null != ((str = br.readLine()))) {
					sRet += str;
				}
			} catch (MalformedURLException mfe) {
				CLogger.error("RequestHelper getMethod failed. cause : {}", mfe);
				sRet = "MalformedURLException";
			} catch (ConnectException ex) {
				CLogger.error("RequestHelper getMethod failed. cause : {}", ex);
				sRet = "ConnectException";
			} catch (IOException ioe) {
				CLogger.error("RequestHelper getMethod failed. cause : {}", ioe);
				sRet = "IOException";
			}
		} catch (Exception e) {
			CLogger.error("RequestHelper getMethod failed. cause : {}", e);
			sRet = "UnknownException";
		}
		sRet = sRet.trim();
		return sRet;
	}

	public static String formatPageUrl(String srcUrl, String defaultUrl) {
		if (srcUrl == null) {
			return defaultUrl.endsWith("?") ? defaultUrl : defaultUrl + "?";
		}
		if (!srcUrl.startsWith("http://")) {
			srcUrl = "http://" + srcUrl;
		}
		if (!srcUrl.endsWith("?")) {
			srcUrl = srcUrl + "?";
		}
		return srcUrl;
	}

	public static String Post2Url(String destUrl, String content) {
		String sRet = "";
		try {
			URL url;
			URLConnection urlConn;
			DataOutputStream printout;

			url = new URL(destUrl);

			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);

			// No caching, we want the real thing.
			urlConn.setUseCaches(false);

			// Specify the content type.
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Content-Type", "text/plain");
			// Get around any basic authentication checks.
			// Note that you'll need to provide the base64Encode()
			// method.
			// Send POST output.
			printout = new DataOutputStream(urlConn.getOutputStream());

			printout.writeBytes(content);
			printout.flush();
			printout.close();

			// Get response data.
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			String str = null;
			while (null != ((str = br.readLine()))) {
				sRet += str;
			}
			br.close();
		} catch (MalformedURLException me) {
			CLogger.error("RequestHelper Post2Url failed. cause : {}", me);
		} catch (IOException ioe) {
			CLogger.error("RequestHelper Post2Url failed. cause : {}", ioe);
		}
		return sRet;
	}

	public static String getQueryString(HttpServletRequest request) {
		StringBuilder temp = new StringBuilder();
		Enumeration<?> paramNames = request.getParameterNames();
		boolean first = true;
		while (paramNames.hasMoreElements()) {
			if (!first) {
				temp.append("&");
			}
			String paramName = paramNames.nextElement().toString();
			temp.append(paramName).append("=").append(request.getParameter(paramName));
			first = false;
		}
		return temp.toString();
	}

	public static String getQueryString(HttpServletRequest request, String linkMark) {
		StringBuilder temp = new StringBuilder();
		Enumeration<?> paramNames = request.getParameterNames();
		boolean first = true;
		while (paramNames.hasMoreElements()) {
			if (!first) {
				temp.append(linkMark);
			}
			String paramName = paramNames.nextElement().toString();
			temp.append(paramName).append("=").append(request.getParameter(paramName));
			first = false;
		}
		return temp.toString();
	}
}
