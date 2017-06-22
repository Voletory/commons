package commons.packet;

import java.text.NumberFormat;

import commons.lang.StringUtils;
import commons.util.PathUtil;

public class PacketUtils {

	/**
	 * 数值转换为字符形，左边补0
	 * 
	 * @param length
	 * @param maxDigits
	 *            最大位
	 * @param minDigits
	 *            最小位
	 * @return
	 */
	public static String int2String(int number, int maxDigits, int minDigits) {
		// 得到一个NumberFormat的实例
		NumberFormat nf = NumberFormat.getInstance();
		// 设置是否使用分组
		nf.setGroupingUsed(false);
		// 设置最大整数位数
		nf.setMaximumIntegerDigits(maxDigits);
		// 设置最小整数位数
		nf.setMinimumIntegerDigits(minDigits);

		return nf.format(number);
	}

	public static String int2String(int number, int digits) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(digits);
		nf.setMinimumIntegerDigits(digits);

		return nf.format(number);
	}

	/**
	 * 填充空格
	 * 
	 * @param spaceCount
	 * @return
	 */
	public static String fillSpace(int spaceCount) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < spaceCount; i++) {
			sBuffer.append(PathUtil.SPACEBAR);
		}
		return sBuffer.toString();
	}

	/**
	 * 字段转换成byte[],不足右补0
	 * 
	 * @param bytes
	 * @param value
	 * @param offset
	 * @param length
	 * @return 下一个字段的起始位置
	 */
	public static int string2BytesOnRight(byte[] data, String value, int offset, int length) {
		if (value == null) {
			return -1;
		}
		value = StringUtils.getLMTString(value, 4, false, "0");
		byte[] valueBytes = value.getBytes();
		int valueLength = valueBytes.length;
		for (int i = 0; i < length; i++) {
			if (i < valueLength)
				data[offset + i] = valueBytes[i];
			else
				data[offset + i] = 48;
		}
		return offset + length;
	}

	/**
	 * 字段转换成byte[],不足左补0
	 * 
	 * @param bytes
	 * @param value
	 * @param offset
	 * @param length
	 * @return 下一个字段的起始位置
	 */
	public static int string2BytesOnLeft(byte[] data, String value, int offset, int length) {
		if (value == null) {
			return -1;
		}
		value = value.replaceAll(" ", "");
		for (int x = value.length(); x < length; x++) {
			value = "0" + value;
		}
		byte[] valueBytes = value.getBytes();
		for (int i = 0; i < length; i++) {
			data[offset + i] = valueBytes[i];
		}
		return offset + length;
	}

	/**
	 * 字段转换成byte[],不足补空格
	 * 
	 * @param bytes
	 * @param value
	 * @param offset
	 * @param length
	 * @return 下一个字段的起始位置
	 */
	public static int string2Bytes(byte[] data, String value, int offset, int length) {
		if (value == null) {
			return -1;
		}
		byte[] valueBytes = value.getBytes();
		int valueLength = valueBytes.length;
		for (int i = 0; i < length; i++) {
			if (i < valueLength)
				data[offset + i] = valueBytes[i];
			else
				data[offset + i] = 32;
		}
		return offset + length;
	}

	public static String bytes2String(byte[] data, int offset, int length) {
		byte[] target = new byte[length];
		System.arraycopy(data, offset, target, 0, length);
		return new String(target);
	}
}
