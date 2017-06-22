package commons.http.ud;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import commons.io.StreamUtils;

/**
 * 下载
 * 
 * @author bailey.fu
 * @date May 25, 2010
 * @version 1.0
 * @description
 */
public class DownLoader {
	private File file;

	public DownLoader(String filePath) throws FileNotFoundException {
		file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("File [" + filePath + "] is not Exist!");
		}
	}

	public void downLoad(HttpServletResponse response) throws IOException {
		downLoad(response, false);
	}

	public void downLoad(HttpServletResponse response, boolean online) throws IOException {
		if (file == null) {
			response.sendError(404, "file not found!");
			return;
		}
		/** 重置 */
		response.reset();
		/** 在线打开方式 */
		if (online) {
			URL u = new URL("file:///" + file.getPath());
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
			/** 下载方式 */
		} else {
			response.setContentType("application/bin");
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		}
		OutputStream out = null;
		BufferedInputStream br = null;

		byte[] buf = new byte[1024];
		int len = 0;
		try {
			br = new BufferedInputStream(new FileInputStream(file));
			out = response.getOutputStream();
			while ((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			StreamUtils.close(br, out);
		}
	}
}
