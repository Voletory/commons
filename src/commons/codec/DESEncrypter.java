package commons.codec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

import org.apache.commons.lang3.StringUtils;

/**
 * DES对称加解密
 * 
 * @author bailey_fu
 * @data 2013-12-20
 * @update 2016-12-09 14:47
 * @version 2.0
 * @Description 密钥长度：56位；已过时，由AES替代
 */
public final class DESEncrypter {
	private static Map<String, DESEncrypter> encryptMap = new HashMap<String, DESEncrypter>();
	private static final String KEY_ALGORITHM = "DES";
	// 56位
	int keySize = 56;
	private String workPattern = "ECB";
	private String paddingPattern = "PKCS5Padding";
	private Cipher enCipher = null;
	private Cipher deCipher = null;
	private B64Encrypter b64Encrypter = null;
	protected String desKey;

	private DESEncrypter(String desKey) {
		init(desKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	private DESEncrypter(String desKey, String b64Key) {
		init(desKey, b64Key);
	}

	private void init(String desKey, String b64Key) {
		this.desKey=desKey;
		try {
			Key key = generateKey();
			StringBuilder pattern = new StringBuilder(KEY_ALGORITHM);
			pattern.append("/").append(workPattern);
			pattern.append("/").append(paddingPattern);

			enCipher = Cipher.getInstance(pattern.toString());
			enCipher.init(Cipher.ENCRYPT_MODE, key);

			deCipher = Cipher.getInstance(pattern.toString());
			deCipher.init(Cipher.DECRYPT_MODE, key);

			b64Encrypter = B64Encrypter.getInstance(b64Key);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing DESEncrypt class. Cause: " + e);
		}
	}
	// 覆盖此方法以改变Key的生成方式
	public Key generateKey() throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
		kgen.init(keySize, new SecureRandom(desKey.getBytes()));
		return kgen.generateKey();
		// OR
		// SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(),KEY_ALGORITHM);
		// 此方式密匙必须是16位，待加密内容的长度必须是16的倍数
	}
	public String encode(byte[] data) throws RuntimeException {
		String strMi = null;
		try {
			strMi = b64Encrypter.encode(enCipher.doFinal(data));
		} catch (Exception e) {
			throw new RuntimeException("DESEncrypt encode error. Cause: " + e);
		}
		return strMi;
	}

	public byte[] decode(String encryptText) throws RuntimeException {
		try {
			return deCipher.doFinal(b64Encrypter.decode(encryptText));
		} catch (Exception e) {
			throw new RuntimeException("DESEncrypt decode error. Cause: " + e);
		}
	}

	public void encodeFile(String file, String destFile) throws RuntimeException {
		InputStream is = null;
		OutputStream out = null;
		CipherInputStream cis = null;
		try {
			is = new FileInputStream(file);
			out = new FileOutputStream(destFile);
			cis = new CipherInputStream(is, enCipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = cis.read(buffer)) > 0) {
				out.write(buffer, 0, r);
			}
		} catch (Exception e) {
			throw new RuntimeException("DESEncrypt encodeFile error. Cause: " + e);
		} finally {
			try {
				if (cis != null)
					cis.close();
				if (out != null)
					out.close();
				if (is != null)
					is.close();
			} catch (Exception e) {
			}
		}
	}

	public void decodeFile(String file, String destFile) throws RuntimeException {
		InputStream is = null;
		OutputStream out = null;
		CipherOutputStream cos = null;
		try {
			is = new FileInputStream(file);
			out = new FileOutputStream(destFile);
			cos = new CipherOutputStream(out, deCipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = is.read(buffer)) >= 0) {
				cos.write(buffer, 0, r);
			}
		} catch (Exception e) {
			throw new RuntimeException("DESEncrypt decodeFile error. Cause: " + e);
		} finally {
			try {
				if (cos != null)
					cos.close();
				if (out != null)
					out.close();
				if (is != null)
					is.close();
			} catch (Exception e) {
			}
		}
	}

	/********************* static method ************************/

	public static DESEncrypter getInstance(String desKey) {
		return getInstance(desKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	public static DESEncrypter getInstance(String desKey, String b64Key) {
		if (StringUtils.isBlank(desKey) || !B64Encrypter.checkAlphabet(b64Key)) {
			return null;
		}
		String key = desKey + b64Key;
		DESEncrypter desEncrypt = encryptMap.get(key);
		if (desEncrypt == null) {
			desEncrypt = new DESEncrypter(desKey, b64Key);
			encryptMap.put(key, desEncrypt);
		}
		return desEncrypt;
	}

	/**
	 * 
	 * @param desKey
	 * @param plainText
	 * @return
	 */
	public static String encrypt(String desKey, byte[] data) throws RuntimeException {
		if (StringUtils.isBlank(desKey) || data == null)
			return null;
		return getInstance(desKey).encode(data);
	}

	/**
	 * @param desKey
	 * @param b64Key
	 * @param plainText
	 * @return
	 */
	public static String encrypt(String desKey, String b64Key, byte[] data) throws RuntimeException {
		if (StringUtils.isBlank(desKey) || !B64Encrypter.checkAlphabet(b64Key) || data == null)
			return null;
		return getInstance(desKey, b64Key).encode(data);
	}

	/**
	 * 
	 * @param desKey
	 * @param encryptText
	 * @return
	 */
	public static byte[] decrypt(String desKey, String encryptText) throws RuntimeException {
		if (StringUtils.isBlank(desKey) || StringUtils.isBlank(encryptText))
			return null;
		return getInstance(desKey).decode(encryptText);
	}

	/**
	 * 
	 * @param desKey
	 * @param b64Key
	 * @param encryptText
	 * @return
	 */
	public static byte[] decrypt(String desKey, String b64Key, String encryptText) throws RuntimeException {
		if (StringUtils.isBlank(desKey) || StringUtils.isBlank(encryptText) || !B64Encrypter.checkAlphabet(b64Key))
			return null;
		return getInstance(desKey, b64Key).decode(encryptText);
	}

	/**
	 * 
	 * @param desKey
	 * @param file
	 * @param destFile
	 * @throws Exception
	 */
	public static void encryptFile(String desKey, String file, String destFile) throws RuntimeException {
		if (!StringUtils.isBlank(desKey)) {
			getInstance(desKey).encodeFile(file, destFile);
		}
	}

	/**
	 * 
	 * @param desKey
	 * @param b64Key
	 * @param file
	 * @param destFile
	 * @throws Exception
	 */
	public static void encryptFile(String desKey, String b64Key, String file, String destFile) throws RuntimeException {
		if (!StringUtils.isBlank(desKey) && B64Encrypter.checkAlphabet(b64Key)) {
			getInstance(desKey, b64Key).encodeFile(file, destFile);
		}
	}

	/**
	 * 
	 * @param desKey
	 * @param file
	 * @param destFile
	 * @throws Exception
	 */
	public static void decryptFile(String desKey, String file, String destFile) throws RuntimeException {
		if (!StringUtils.isBlank(desKey)) {
			getInstance(desKey).decodeFile(file, destFile);
		}
	}

	/**
	 * 
	 * @param desKey
	 * @param b64Key
	 * @param file
	 * @param destFile
	 * @throws Exception
	 */
	public static void decryptFile(String desKey, String b64Key, String file, String destFile) throws RuntimeException {
		if (!StringUtils.isBlank(desKey) && B64Encrypter.checkAlphabet(b64Key)) {
			getInstance(desKey, b64Key).decodeFile(file, destFile);
		}
	}
}
