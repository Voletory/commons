package commons.codec;

/**
 * SHA256 64位小写
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-09 16:15
 * @description 哈希加密算法;安全性较高
 */
public class SHA256Encrypter extends HashEncrypter {
	private static final String ALGORITHM = "SHA-256";
	private static SHA256Encrypter sha256Encrypt = new SHA256Encrypter();

	private SHA256Encrypter() {
		super(ALGORITHM, null);
	}

	private SHA256Encrypter(String alphabet) {
		super(ALGORITHM, alphabet);
	}

	public static SHA256Encrypter getInstance() {
		return sha256Encrypt;
	}

	public static SHA256Encrypter newInstance(boolean useBase64) {
		return new SHA256Encrypter(B64Encrypter.DEFAULT_ALPHABET);
	}

	public static String encrypt(String plainText) throws Exception {
		return sha256Encrypt.encode(plainText);
	}

	public static String encrypt(String plainText, String... salts) throws Exception {
		return sha256Encrypt.encode(plainText, salts);
	}

	public static String encrypt(String plainText, int iterations, String... salts) throws Exception {
		return sha256Encrypt.encode(plainText, iterations, salts);
	}
}
