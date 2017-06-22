package commons.xml;

import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.io.OutputFormat;

/**
 * 
 * @author bailey.fu
 * @date Sep 6, 2010
 * @version 1.0
 * @description 重写writeDeclaration方法以写入standalone属性
 */
public class XMLWriter extends org.dom4j.io.XMLWriter {
	public XMLWriter(OutputStream writer, OutputFormat outputFormat) throws IOException {
		super(writer, outputFormat);
	}

	protected void writeDeclaration() throws IOException {
		OutputFormat format = getOutputFormat();
		if (!format.isSuppressDeclaration()) {
			writer.write("<?xml version=\"1.0\"");
			if (!format.isOmitEncoding()) {
				writer.write(" encoding=\"UTF-8\"");
			}
			writer.write(" standalone=\"yes\"");
			writer.write("?>");
			if (format.isNewLineAfterDeclaration()) {
				println();
			}
		}
	}
}
