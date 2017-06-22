package commons.http.ud;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

import commons.io.StreamUtils;

/**
 * URL上传
 * 
 * @author bailey.fu
 * @date May 25, 2010
 * @version 1.0
 * @description
 */
public class URLUploader {

	private String sourceUrl;
	private String targetName;
	private File target;

	public URLUploader(String sourceUrl, String targetName) {
		this.sourceUrl = sourceUrl;
		this.targetName = targetName;
	}

	public void uploadFile() throws IOException {
		URL path = new URL(sourceUrl);
		InputStream is = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		RandomAccessFile raf = null;
		try {
			URLConnection conn = path.openConnection();
			conn.connect();
			is = conn.getInputStream();
			bis = new BufferedInputStream(is);
			dis = new DataInputStream(bis);
			target = new File(targetName);
			raf = new RandomAccessFile(target, "rw");
			byte[] buf = new byte[102400];
			int num = dis.read(buf);
			while (num != (-1)) {
				raf.write(buf, 0, num);
				raf.skipBytes(num); // 顺序写文件字节
				num = dis.read(buf);
			}
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			StreamUtils.close(dis, bis, is, raf);
		}
	}

	public File getTarget() {
		return target;
	}
}
