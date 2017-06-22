package commons.codec;

/**
 * SHA224 56位小写
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-09 16:23
 * @description 哈希加密算法;安全性较高
 */
public class SHA224Encrypter extends HashEncrypter {
	private static final String ALGORITHM = "SHA-224";
	private static SHA224Encrypter sha224Encrypt = new SHA224Encrypter();

	private SHA224Encrypter() {
		super(ALGORITHM, null);
	}

	private SHA224Encrypter(String alphabet) {
		super(ALGORITHM, alphabet);
	}

	public static SHA224Encrypter getInstance() {
		return sha224Encrypt;
	}

	public static SHA224Encrypter newInstance(boolean useBase64) {
		return new SHA224Encrypter(B64Encrypter.DEFAULT_ALPHABET);
	}

	public static String encrypt(String plainText) throws Exception {
		return sha224Encrypt.encode(plainText);
	}

	public static String encrypt(String plainText, String... salts) throws Exception {
		return sha224Encrypt.encode(plainText, salts);
	}

	public static String encrypt(String plainText, int iterations, String... salts) throws Exception {
		return sha224Encrypt.encode(plainText, iterations, salts);
	}
}
