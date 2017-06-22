package commons.beanutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import commons.io.StreamUtils;
import commons.log.XLogger;

/**
 * 序列化工具类
 * 
 * @author bailey.fu
 * @date Jul 9, 2010
 * @version 1.0
 * @description
 */
@SuppressWarnings("unchecked")
public final class SerializeUtils {

	/**
	 * 对象序列化成字符串
	 * 
	 * @param serializable
	 * @return
	 */
	public static String serialize2String(Serializable serializable) {
		byte[] bs = serialize2ByteArray(serializable);
		if (bs != null) {
			char[] cs = new char[bs.length];
			for (int i = 0; i < bs.length; i++) {
				cs[i] = (char) bs[i];
			}
			return String.valueOf(cs);
		}
		return null;
	}

	/**
	 * 对象序列化成字节数组
	 * 
	 * @param serializable
	 * @return
	 */
	public static byte[] serialize2ByteArray(Serializable serializable) {
		ByteArrayOutputStream bos = null;
		ObjectOutput out = null;
		try {
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(serializable);

			return bos.toByteArray();
		} catch (Exception e) {
			XLogger.error("SerializeUtils serialize2ByteArray failed. cause : {}", e);
		} finally {
			StreamUtils.close(out, bos);
		}
		return null;
	}

	/**
	 * 字符串序列化成对象
	 * 
	 * @param serializeString
	 * @return
	 */
	public static <T> T string2Serialize(String serializeString) {
		if (serializeString != null) {
			char[] cs = serializeString.toCharArray();
			byte[] bs = new byte[cs.length];
			for (int i = 0; i < cs.length; i++) {
				bs[i] = (byte) cs[i];
			}
			return byteArray2Serialize(bs);
		}
		return null;
	}

	public static <T> T byteArray2Serialize(byte[] byteArray) {
		ByteArrayInputStream bis = null;
		ObjectInput in = null;
		try {
			bis = new ByteArrayInputStream(byteArray);
			in = new ObjectInputStream(bis);
			return (T) in.readObject();
		} catch (Exception e) {
			XLogger.error("SerializeUtils byteArray2Serialize failed. cause : {}", e);
		} finally {
			StreamUtils.close(in, bis);
		}
		return null;
	}
}
