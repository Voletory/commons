package commons.http;

import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import commons.log.XLogger;

/**
 * SSL连接上下文
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-16 11:48
 */
public class SSLContextFactory {
	private static SSLContext ctx;
	private final static String PROTOCAL_NAME = "SSL";

	public static SSLContext getInstance(boolean chkCert) {
		if (ctx == null) {
			try {
				ctx = SSLContext.getInstance(PROTOCAL_NAME);
				if (chkCert) {
					System.err.println("您要实现证书信任连接，请联系我们的管理员！");
				} else {
					ctx.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
				}
			} catch (Exception e) {
				XLogger.error("SSLContextFactory create error", e);
			}
			return ctx;
		}
		return ctx;
	}
}
