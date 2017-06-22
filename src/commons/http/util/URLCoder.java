package commons.http.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import commons.codec.Charset;
import commons.log.CLogger;

/**
 * URL Code处理
 * 
 * @author bailey.fu
 * @date Mar 25, 2010
 * @version 1.0
 * @description
 */
public final class URLCoder {

	public static String defaultEncode(String src, Charset code) {
		return encode(StringUtils.defaultString(src), code);
	}

	public static String encode(String src, Charset code) {
		try {
			return URLEncoder.encode(src, code.VALUE);
		} catch (Exception e) {
			CLogger.error("URLCoder encode error", e);
		}
		return null;
	}

	public static String defaultDecode(String src, Charset code) {
		return decode(StringUtils.defaultString(src), code);
	}

	public static String decode(String src, Charset code) {
		try {
			return URLDecoder.decode(src, code.VALUE);
		} catch (Exception e) {
			CLogger.error("URLCoder decode error", e);
		}
		return null;
	}

}
