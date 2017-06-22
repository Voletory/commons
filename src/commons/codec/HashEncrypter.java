package commons.codec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 散列算法
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-28 10:59
 */
public abstract class HashEncrypter {
	protected MessageDigest messageDigest;
	protected B64Encrypter b64Encrypter;

	public HashEncrypter(String algorithm, String alphabet) {
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(algorithm + " encryption is not supported" + e);
		}
		if (alphabet != null)
			b64Encrypter = B64Encrypter.getInstance(alphabet);
	}

	public String encode(String plainText, String... salts) throws Exception {
		return encode(plainText, 0, salts);
	}

	public String encode(String plainText, int iterations, String... salts) throws Exception {
		if (salts.length > 0) {
			for (String salt : salts) {
				messageDigest.update(salt.getBytes());
			}
		}
		byte[] result = messageDigest.digest(plainText.getBytes());
		if (iterations > 0) {
			for (int i = 0; i < iterations; i++) {
				messageDigest.reset();
				result = messageDigest.digest(result);
			}
		}
		return b64Encrypter == null ? Hex.bytes2Hex(result) : b64Encrypter.encode(result);
		// return new BigInteger(1, result).toString(16);
	}
}
