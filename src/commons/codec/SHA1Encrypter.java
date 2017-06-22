package commons.codec;

/**
 * SHA1 40位小写
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-09 16:00
 * @update 2017-03-28
 * @description 哈希加密算法;运行速度比 MD5慢，安全性稍高
 */
public class SHA1Encrypter extends HashEncrypter {
	private static final String ALGORITHM = "SHA-1";
	private static SHA1Encrypter sha1Encrypt = new SHA1Encrypter();

	private SHA1Encrypter() {
		super(ALGORITHM, null);
	}

	private SHA1Encrypter(String alphabet) {
		super(ALGORITHM, alphabet);
	}

	public static SHA1Encrypter getInstance() {
		return sha1Encrypt;
	}

	public static SHA1Encrypter newInstance(boolean useBase64) {
		return new SHA1Encrypter(B64Encrypter.DEFAULT_ALPHABET);
	}

	public static String encrypt(String plainText) throws Exception {
		return sha1Encrypt.encode(plainText);
	}

	public static String encrypt(String plainText, String... salts) throws Exception {
		return sha1Encrypt.encode(plainText, salts);
	}

	public static String encrypt(String plainText, int iterations, String... salts) throws Exception {
		return sha1Encrypt.encode(plainText, iterations, salts);
	}
}
