package commons.codec;

/**
 * Message-Digest Algorithm 5加密32位小写
 * 
 * @author bailey.fu
 * @date Feb 6, 2010
 * @update 2017-03-28
 * @version 1.2
 * @description 哈希加密算法
 */
public final class MD5Encrypter extends HashEncrypter {
	private static final String ALGORITHM = "MD5";
	private static final MD5Encrypter md5Encrypt = new MD5Encrypter();

	private MD5Encrypter() {
		super(ALGORITHM, null);
	}

	private MD5Encrypter(String alphabet) {
		super(ALGORITHM, alphabet);
	}

	public static MD5Encrypter getInstance() {
		return md5Encrypt;
	}

	public static MD5Encrypter newInstance(boolean useBase64) {
		return new MD5Encrypter(B64Encrypter.DEFAULT_ALPHABET);
	}

	public static String encrypt(String plainText) throws Exception {
		return md5Encrypt.encode(plainText);
	}

	public static String encrypt(String plainText, String... salts) throws Exception {
		return md5Encrypt.encode(plainText, salts);
	}

	public static String encrypt(String plainText, int iterations, String... salts) throws Exception {
		return md5Encrypt.encode(plainText, iterations, salts);
	}
}
