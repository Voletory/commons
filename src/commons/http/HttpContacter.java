package commons.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Http协议请求
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-16 14:09
 */
public class HttpContacter extends HttpContactAble {
	public HttpContacter(){
		
	}
	public HttpContacter(String contentType){
		super(contentType);
	}
	@Override
	protected HttpClient buildHttpClient() throws Exception {
		return HttpClients.createDefault();
	}
}
