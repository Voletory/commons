package commons.codec;

/**
 * SHA256 128位小写
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-09 16:20
 * @description 哈希加密算法;安全性较高
 */
public class SHA512Encrypter extends HashEncrypter {
	private static final String ALGORITHM = "SHA-512";
	private static SHA512Encrypter sha512Encrypt = new SHA512Encrypter();

	private SHA512Encrypter() {
		super(ALGORITHM, null);
	}

	private SHA512Encrypter(String alphabet) {
		super(ALGORITHM, alphabet);
	}

	public static SHA512Encrypter getInstance() {
		return sha512Encrypt;
	}

	public static SHA512Encrypter newInstance(boolean useBase64) {
		return new SHA512Encrypter(B64Encrypter.DEFAULT_ALPHABET);
	}

	public static String encrypt(String plainText) throws Exception {
		return sha512Encrypt.encode(plainText);
	}

	public static String encrypt(String plainText, String... salts) throws Exception {
		return sha512Encrypt.encode(plainText, salts);
	}

	public static String encrypt(String plainText, int iterations, String... salts) throws Exception {
		return sha512Encrypt.encode(plainText, iterations, salts);
	}
}
