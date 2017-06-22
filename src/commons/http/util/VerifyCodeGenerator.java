package commons.http.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import commons.util.RandomUtils;

/**
 * 验证码生成
 * 
 * @author bailey.fu
 * @date Apr 6, 2010
 * @update 2016-12-16 14:59
 * @version 1.1
 * @description
 */
public final class VerifyCodeGenerator {
	private static Map<String, VerifyCodeGenerator> generatorMap = new HashMap<String, VerifyCodeGenerator>();
	private static int defaultLength = 4;
	private static String defaultAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private int codeLength;
	private char[] alphabetLib;

	private VerifyCodeGenerator() {
		init(defaultAlphabet, defaultLength);
	}

	private VerifyCodeGenerator(String alphabet) {
		init(alphabet, defaultLength);
	}

	private VerifyCodeGenerator(String alphabet, int codeLength) {
		init(alphabet, codeLength);
	}

	private void init(String alphabet, int codeLength) {
		alphabetLib = alphabet.toCharArray();
		this.codeLength = (codeLength > 10 || codeLength < 1) ? defaultLength : codeLength;
	}

	public String createCode() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			sb.append(alphabetLib[RandomUtils.getPositiveInt(alphabetLib.length)]);
		}
		return sb.toString();
	}

	public BufferedImage createIMG() {
		return createIMG(createCode());
	}

	/******************** create method **********************/
	public static VerifyCodeGenerator getInstance() {
		return getInstance(defaultAlphabet, defaultLength);
	}

	public static VerifyCodeGenerator getInstance(String alphabet, int codeLength) {
		String key = new StringBuilder().append(alphabet).append("_").append(codeLength).toString();
		VerifyCodeGenerator vcGenerator = generatorMap.get(key);
		if (vcGenerator == null) {
			vcGenerator = new VerifyCodeGenerator(alphabet, codeLength);
			generatorMap.put(key, vcGenerator);
		}
		return vcGenerator;
	}

	/******************** static method **********************/
	public static String createCode(int length, char[] charLibrary) {
		if (length <= 0 || charLibrary.length == 0 || length > charLibrary.length) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(charLibrary[RandomUtils.getPositiveInt(charLibrary.length)]);
		}
		return sb.toString();
	}

	public static BufferedImage createIMG(String code) {
		BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
		// 得到该图片的绘图对象
		Graphics g = img.getGraphics();
		Color c = new Color(200, 150, 255);
		g.setColor(c);
		// 填充整个图片的颜色
		g.fillRect(0, 0, 68, 22);
		return createIMG(img, code);
	}

	public static BufferedImage createIMG(BufferedImage img, String code) {
		Graphics g = img.getGraphics();
		g.setColor(getRandColor(200, 250));
		int width = 80, height = 27;
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = RandomUtils.nextInt(width);
			int y = RandomUtils.nextInt(height);
			int xl = RandomUtils.nextInt(12);
			int yl = RandomUtils.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		char[] ch = code.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			g.setColor(new Color(20 + RandomUtils.nextInt(110), 20 + RandomUtils.nextInt(110), 20 + RandomUtils.nextInt(110)));
			g.drawString(String.valueOf(ch[i]), 10 * i + 15, 17);
		}
		return img;
	}

	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + RandomUtils.nextInt(bc - fc);
		int g = fc + RandomUtils.nextInt(bc - fc);
		int b = fc + RandomUtils.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
