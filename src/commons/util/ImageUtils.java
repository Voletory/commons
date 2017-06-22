package commons.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片工具
 * 
 * @author bailey.fu
 * @date May 25, 2010
 * @version 1.0
 * @description
 */
public final class ImageUtils {

	/** 改变图片大小 */
	public static File resize(File source, String targetName, int targetWidth, int targetHeight, float quality) {
		Image image = null;
		try {
			image = ImageIO.read(source);
		} catch (Exception e) {
			return null;
		}
		return doResize(image, targetName, targetWidth, targetHeight, null, quality);
	}

	public static File resize(File source, String targetName, int targetWidth, int targetHeight, Color background, float quality) {
		Image image = null;
		try {
			image = ImageIO.read(source);
		} catch (Exception e) {
			return null;
		}
		return doResize(image, targetName, targetWidth, targetHeight, background, quality);
	}

	/** 按倍数放大/缩小 */
	public static File resize(File source, String targetName, double multiple, float quality) {
		Image image = null;
		try {
			image = ImageIO.read(source);
		} catch (Exception e) {
			return null;
		}
		return resize(image, targetName, multiple, quality);
	}

	/** 按倍数放大/缩小 */
	public static File resize(Image source, String targetName, double multiple, float quality) {
		int width = source.getWidth(null);
		int height = source.getHeight(null);
		return doResize(source, targetName, Double.valueOf(width * multiple).intValue(), Double.valueOf(height * multiple).intValue(), null, quality);
	}

	private static File doResize(Image image, String targetName, int targetWidth, int targetHeight, Color background, float quality) {
		File target = null;
		try {
			BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			if (background != null) {
				Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
				g2.setBackground(background);
				g2.clearRect(0, 0, targetWidth, targetHeight);
			}
			bufferedImage.getGraphics().drawImage(image, 0, 0, targetWidth, targetHeight, null);

			target = new File(targetName);
			FileOutputStream fos = new FileOutputStream(target);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
			jep.setQuality(quality, true);

			encoder.encode(bufferedImage, jep);
			fos.close();
		} catch (Exception e) {
			target = null;
		}
		return target;
	}

}
