package commons.http.ud;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL上传
 * 
 * @author bailey.fu
 * @date May 25, 2010
 * @update 2017-07-03 10:42
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
		URLConnection conn = path.openConnection();
		target = new File(targetName);
		try (InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				DataInputStream dis = new DataInputStream(bis);
				RandomAccessFile raf = new RandomAccessFile(target, "rw");) {
			conn.connect();
			byte[] buf = new byte[1024];
			int num = dis.read(buf);
			while (num != (-1)) {
				raf.write(buf, 0, num);
				raf.skipBytes(num); // 顺序写文件字节
				num = dis.read(buf);
			}
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	public File getTarget() {
		return target;
	}
}
