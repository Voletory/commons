package commons.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import commons.codec.Charset;
import commons.log.CLogger;

/**
 * 访问网络工具
 * 
 * @author bailey.fu
 * @date Oct 18, 2008
 * @version 1.1
 * @description 访问并获取网络资源工具类
 */
@SuppressWarnings("all")
public final class NETSnatchUtil {
	private static String GET = "GET";
	private static String POST = "POST";
	private static final int TIME_OUT = 5000;

	public static String snatchByGET(String url, String encode, int timeout, Map<String, String>... heads) {
		if (url == null || url.trim().equals("") || url.length() < 11) {
			return null;
		}
		StringBuffer content = new StringBuffer();

		HttpURLConnection conn = null;
		try {
			URL urlHandler = new URL(url);
			conn = (HttpURLConnection) urlHandler.openConnection();
			conn.setReadTimeout(timeout);
			conn.setRequestMethod(GET);
			if (heads.length > 0 && !CollectionUtils.isEmpty(heads[0])) {
				String key = null;
				for (Iterator<String> keys = heads[0].keySet().iterator(); keys.hasNext(); key = keys.next()) {
					conn.setRequestProperty(key, heads[0].get(key));
				}
			}
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, encode);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
			br.close();
			isr.close();
			is.close();
		} catch (Exception e) {
			CLogger.error("Error From NETSnatchUtil : ", e);
			return null;
		} finally {
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
				}
			}
		}
		return content.toString().trim();
	}

	/**
	 * 默认5000ms超时
	 * 
	 * @param url
	 * @param cookies
	 * @param encode
	 * @return
	 */
	public static String snatchByGET(String url, String cookies, String encode) {
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Cookies", cookies);
		return snatchByGET(url, encode, TIME_OUT, heads);
	}

	public static String snatchByGET(String url, String encode) {
		return snatchByGET(url, encode, TIME_OUT);
	}

	public static String snatchByGET(String url) {
		return snatchByGET(url, Charset.UTF8.VALUE, TIME_OUT);
	}

	public static String snatchByPOST(String url, String encode, int timeout, Map<String, String>... reqParams) {
		if (url == null || url.trim().equals("") || url.length() < 11) {
			return null;
		}
		StringBuffer content = new StringBuffer();

		HttpURLConnection conn = null;
		try {
			URL urlHandler = new URL(url);
			conn = (HttpURLConnection) urlHandler.openConnection();
			conn.setReadTimeout(timeout);
			conn.setRequestMethod(POST);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
			conn.setRequestProperty("Content-type", "text/html;charset=utf-8");

			StringBuilder temp = new StringBuilder();
			if (reqParams.length > 0 && !CollectionUtils.isEmpty(reqParams[0])) {
				String key = null;
				String value = null;
				for (Iterator<String> keys = reqParams[0].keySet().iterator(); keys.hasNext(); key = keys.next()) {
					value = reqParams[0].get(key);
					conn.addRequestProperty(key, value);
					temp.append(key).append("=").append(value);
				}
			}
			String params = temp.toString();
			conn.setRequestProperty("Content-length", String.valueOf(params.length()));

			OutputStream out = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);
			dos.write(params.getBytes());
			dos.flush();
			dos.close();
			out.close();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, encode);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
			br.close();
			isr.close();
			is.close();
		} catch (Exception e) {
			CLogger.error("Error From NETSnatchUtil : ", e);
			return null;
		} finally {
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
				}
			}
		}
		return content.toString().trim();
	}

	public static String snatchByPOST(String url, String encode, Map<String, String> reqParams) {
		return snatchByPOST(url, encode, TIME_OUT, reqParams);
	}

	public static String snatchByPOST(String url, Map<String, String> reqParams) {
		return snatchByPOST(url, Charset.UTF8.VALUE, TIME_OUT, reqParams);
	}
}
