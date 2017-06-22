package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import commons.beanutils.ReflectionUtils;
import commons.codec.Charset;
import commons.config.ConfigurationException;
import commons.config.XMLConfiguration;
import commons.io.StreamUtils;

/**
 * XML文件的读取/写入
 * 
 * @author bailey.fu
 * @date May 2, 2010
 * @version 1.0
 * @description
 */
public class XMLHandler extends XMLConfiguration {

	public XMLHandler(String fileName) throws ConfigurationException {
		super(fileName);
	}

	public File getConfigFile() {
		return this.configFile;
	}

	/**
	 * 
	 * @param charset
	 *            默认utf-8
	 * @throws IOException
	 */
	public void write(Charset... charset) throws IOException {
		FileOutputStream fos = null;
		XMLWriter xmlWriter = null;
		try {
			fos = new FileOutputStream(configFile);
			OutputFormat opf = OutputFormat.createPrettyPrint();
			opf.setEncoding(charset.length == 0 ? Charset.UTF8.VALUE : charset[0].VALUE);
			xmlWriter = new XMLWriter(fos, opf);
			xmlWriter.write(document);
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			ReflectionUtils.invokeNoParam(xmlWriter, "close");
			StreamUtils.close(fos);
		}
	}

	public void write4Standalone() throws IOException {
		write4Standalone(Charset.UTF8);
	}

	public void write4Standalone(Charset code) throws IOException {
		FileOutputStream fos = null;
		XMLWriter xmlWriter = null;
		try {
			fos = new FileOutputStream(configFile);
			OutputFormat opf = OutputFormat.createPrettyPrint();
			opf.setEncoding(code.VALUE);
			xmlWriter = new XMLWriter(fos, opf);
			xmlWriter.write(document);
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			ReflectionUtils.invokeNoParam(xmlWriter, "close");
			StreamUtils.close(fos);
		}
	}

	public void configure() throws ConfigurationException {
	}

}
