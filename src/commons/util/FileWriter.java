package commons.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import commons.codec.Charset;

/**
 * 文件创建
 * 
 * @author bailey.fu
 * @date Apr 15, 2010
 * @version 1.0
 * @description
 */
public final class FileWriter {

	private static final String TXT_MARK = ",";

	public static void write2XML(String fileName, Document document, Charset charset, boolean standalone) throws IOException {
		XMLWriter xmlWriter = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			OutputFormat opf = OutputFormat.createPrettyPrint();
			opf.setEncoding(charset.VALUE);

			if (standalone) {
				xmlWriter = new XMLWriter(fos, opf);
			} else {
				xmlWriter = new XMLWriter(fos, opf);
			}
			xmlWriter.write(document);
			fos.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
			if (xmlWriter != null) {
				try {
					xmlWriter.close();
				} catch (Exception e) {
				}
			}
		}
	}

//	public static void write2Excel(String fileName, String[] titles, String[][] contents) throws IOException {
//		Workbook wb = null;
//		if (fileName.endsWith(".xls"))
//			wb = new HSSFWorkbook();
//		else
//			wb = new XSSFWorkbook();
//		Map<String, CellStyle> styles = createStyles(wb);
//
//		Sheet sheet = wb.createSheet("sheet");
//		PrintSetup printSetup = sheet.getPrintSetup();
//		printSetup.setLandscape(true);
//		sheet.setFitToPage(true);
//		sheet.setHorizontallyCenter(true);
//
//		// header row
//		Row headerRow = sheet.createRow(0);
//		headerRow.setHeightInPoints(25);
//		Cell headerCell;
//		for (int i = 0; i < titles.length; i++) {
//			headerCell = headerRow.createCell(i);
//			headerCell.setCellValue(titles[i]);
//			headerCell.setCellStyle(styles.get("header"));
//		}
//
//		int rownum = 1;
//		for (int i = 0; i < contents.length; i++) {
//			Row row = sheet.createRow(rownum++);
//			for (int j = 0; j < titles.length; j++) {
//				Cell cell = row.createCell(j);
//				cell.setCellStyle(styles.get("cell"));
//			}
//		}
//
//		// set sample data
//		for (int i = 0; i < contents.length; i++) {
//			Row row = sheet.getRow(1 + i);
//			if (contents[i] != null) {
//				for (int j = 0; j < contents[i].length; j++) {
//					if (contents[i][j] == null) {
//						row.getCell(j).setCellValue(" ");
//					} else {
//						row.getCell(j).setCellValue(contents[i][j]);
//					}
//				}
//			}
//		}
//		/** 设置列宽 */
//		for (int i = 0; i < titles.length; i++) {
//			sheet.setColumnWidth(i, 20 * 256);
//		}
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream(fileName);
//			wb.write(out);
//			out.flush();
//		} catch (IOException ioe) {
//			throw ioe;
//		} finally {
//			if (out != null) {
//				try {
//					out.close();
//				} catch (Exception e) {
//				}
//			}
//		}
//	}

	public static void write2Txt(String fileName, String[] titles, String[][] contents, Charset... charset) throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(fileName);
			osw = new OutputStreamWriter(fos, charset.length == 0 ? Charset.UTF8.VALUE : charset[0].VALUE);
			bw = new BufferedWriter(osw);

			int index = 1;
			int length = titles.length;
			for (String title : titles) {
				bw.write(title);
				if (index < length) {
					bw.write(TXT_MARK);
				}
				index++;
			}
			bw.newLine();

			for (String[] content : contents) {
				index = 1;
				length = content.length;
				for (String str : content) {
					bw.write(str);
					if (index < length) {
						bw.write(TXT_MARK);
					}
					index++;
				}
				bw.newLine();
			}
			bw.flush();
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (Exception e) {

				}
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (Exception e) {

				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {

				}
			}
		}
	}

	public static void write2CSV(String fileName, String[] titles, String[][] contents, Charset... charset) throws IOException {
		if (!fileName.toLowerCase().endsWith(".csv")) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			fileName += ".csv";
		}
		write2Txt(fileName, titles, contents, charset);
	}

	/**
	 * Create a library of cell styles
	 */
//	private static Map<String, CellStyle> createStyles(Workbook wb) {
//		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
//		CellStyle style;
//		Font titleFont = wb.createFont();
//		titleFont.setFontHeightInPoints((short) 18);
//		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		style = wb.createCellStyle();
//		style.setAlignment(HorizontalAlignment.CENTER);
//		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		style.setFont(titleFont);
//		styles.put("title", style);
//
//		Font monthFont = wb.createFont();
//		monthFont.setFontHeightInPoints((short) 11);
//		monthFont.setColor(IndexedColors.WHITE.getIndex());
//		style = wb.createCellStyle();
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		style.setFont(monthFont);
//		style.setWrapText(true);
//		styles.put("header", style);
//
//		style = wb.createCellStyle();
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setWrapText(true);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//		styles.put("cell", style);
//
//		style = wb.createCellStyle();
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
//		styles.put("formula", style);
//
//		style = wb.createCellStyle();
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
//		styles.put("formula_2", style);
//
//		return styles;
//	}
}
