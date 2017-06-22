package commons.http;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClients;

import commons.io.StreamUtils;

/**
 * Https协议请求
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-16 14:09
 */
public class HttpsContacter extends HttpContactAble {
	private final static String PROTOCOL_NAME = "https";
	private int port = 443;
	private KeyStore trustStore = null;

	public HttpsContacter() {
	}

	public HttpsContacter(int port) {
		this.port = port;
	}

	public HttpsContacter(String storeFile, String storePass) throws Exception {
		FileInputStream instream = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			instream = new FileInputStream(new File(storeFile));
			trustStore.load(instream, storePass.toCharArray());
		} catch (Exception e) {
			throw e;
		} finally {
			StreamUtils.close(instream);
		}
	}

	@Override
	protected HttpClient buildHttpClient() throws Exception {
		if (trustStore == null) {
			Protocol httpProtocol = new Protocol(PROTOCOL_NAME, new SSLSocketFactory(false), port);
			Protocol.registerProtocol(PROTOCOL_NAME, httpProtocol);
			return HttpClients.createDefault();
		}
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore).build();
		SSLConnectionSocketFactory sslsFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		return HttpClients.custom().setSSLSocketFactory(sslsFactory).build();
	}
}
