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
 * DESede即三重DES加密算法，也被称为3DES
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-14 10:29
 * @Description 通过迭代次数的提高了安全性，但同时也造成了加密效率低的问题。正因DESede算法效率问题，AES算法诞生了
 */
public class DESedeEncrypter {
	private static Map<String, DESedeEncrypter> encryptMap = new HashMap<String, DESedeEncrypter>();
	private static final String KEY_ALGORITHM = "DESede";
	//112位/168位
	int keySize=168;
	//ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128
	private String workPattern="ECB";
	//Nopadding/PKCS5Padding/ISO10126Padding
	private String paddingPattern="PKCS5Padding";
	private Cipher enCipher = null;
	private Cipher deCipher = null;
	private B64Encrypter b64Encrypter = null;
	protected String desKey;

	private DESedeEncrypter(String desKey) {
		init(desKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	private DESedeEncrypter(String desKey, String b64Key) {
		init(desKey, b64Key);
	}

	private void init(String desKey, String b64Key) {
		this.desKey=desKey;
		try {
			Key key = generateKey();
			StringBuilder pattern=new StringBuilder(KEY_ALGORITHM);
			pattern.append("/").append(workPattern);
			pattern.append("/").append(paddingPattern);
			
			enCipher = Cipher.getInstance(pattern.toString());
			enCipher.init(Cipher.ENCRYPT_MODE, key);

			deCipher = Cipher.getInstance(pattern.toString());
			deCipher.init(Cipher.DECRYPT_MODE, key);

			b64Encrypter = B64Encrypter.getInstance(b64Key);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing DESedeEncrypter class. Cause: " + e);
		}
	}
	// 覆盖此方法以改变Key的生成方式
	public Key generateKey() throws Exception {
		if (keySize != 112 && keySize != 168 ) {
			throw new RuntimeException("Error initializing DESedeEncrypter class. Cause: Unsupported keySize " + keySize);
		}
		KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(desKey.getBytes());
		kgen.init(keySize, secureRandom);
		return kgen.generateKey();
	}
	public String encode(byte[] data) throws RuntimeException {
		String strMi = null;
		try {
			strMi = b64Encrypter.encode(enCipher.doFinal(data));
		} catch (Exception e) {
			throw new RuntimeException("DESedeEncrypter encode error. Cause: " + e);
		}
		return strMi;
	}

	public byte[] decode(String encryptText) throws RuntimeException {
		try {
			return deCipher.doFinal(b64Encrypter.decode(encryptText));
		} catch (Exception e) {
			throw new RuntimeException("DESedeEncrypter decode error. Cause: " + e);
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
			throw new RuntimeException("DESedeEncrypter encodeFile error. Cause: " + e);
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
			throw new RuntimeException("DESedeEncrypter decodeFile error. Cause: " + e);
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

	public static DESedeEncrypter getInstance(String desKey) {
		return getInstance(desKey, B64Encrypter.DEFAULT_ALPHABET);
	}

	public static DESedeEncrypter getInstance(String desKey, String b64Key) {
		if (StringUtils.isBlank(desKey) || !B64Encrypter.checkAlphabet(b64Key)) {
			return null;
		}
		String key = desKey + b64Key;
		DESedeEncrypter desedeEncrypt = encryptMap.get(key);
		if (desedeEncrypt == null) {
			desedeEncrypt = new DESedeEncrypter(desKey, b64Key);
			encryptMap.put(key, desedeEncrypt);
		}
		return desedeEncrypt;
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
