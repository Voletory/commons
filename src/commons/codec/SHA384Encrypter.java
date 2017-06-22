package commons.codec;

/**
 * SHA224 96位小写
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-09 16:26
 * @description 哈希加密算法
 */
public class SHA384Encrypter extends HashEncrypter {
	private static final String ALGORITHM = "SHA-384";
	private static SHA384Encrypter sha384Encrypt = new SHA384Encrypter();

	private SHA384Encrypter() {
		super(ALGORITHM, null);
	}

	private SHA384Encrypter(String alphabet) {
		super(ALGORITHM, alphabet);
	}

	public static SHA384Encrypter getInstance() {
		return sha384Encrypt;
	}

	public static SHA384Encrypter newInstance(boolean useBase64) {
		return new SHA384Encrypter(B64Encrypter.DEFAULT_ALPHABET);
	}

	public static String encrypt(String plainText) throws Exception {
		return sha384Encrypt.encode(plainText);
	}

	public static String encrypt(String plainText, String... salts) throws Exception {
		return sha384Encrypt.encode(plainText, salts);
	}

	public static String encrypt(String plainText, int iterations, String... salts) throws Exception {
		return sha384Encrypt.encode(plainText, iterations, salts);
	}
}
