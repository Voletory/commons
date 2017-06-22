package commons.codec;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统生成公/私钥
 * 
 * @author bailey
 * @version 1.0
 * @date 2016-12-14 11:26
 */
@SuppressWarnings("unchecked")
public class PKeyGenerator {
	private static final String ALGORITHM_RSA = "RSA";
	private static Map<String, KeyPair> keyPairMap = new HashMap<String, KeyPair>();

	private static KeyPair generateKeyPair(String algorithm) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
		keyPairGen.initialize(1 << 9, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		keyPairMap.put(algorithm, keyPair);
		return keyPair;
	}

	// 根据原私钥byte还原私钥对象
	public static PrivateKey buildPrivateKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return buildPrivateKey(bytes, ALGORITHM_RSA);
	}

	public static PrivateKey buildPrivateKey(byte[] bytes, String algorithm)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		return KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(bytes));
	}

	/**
	 * RSA
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static <T extends PrivateKey> T createPrivateKey() throws NoSuchAlgorithmException {
		return createPrivateKey(ALGORITHM_RSA);
	}

	public static <T extends PrivateKey> T createPrivateKey(String algorithm) throws NoSuchAlgorithmException {
		KeyPair keyPair = keyPairMap.get(algorithm);
		if (keyPair == null) {
			keyPair = generateKeyPair(algorithm);
		}
		return (T) keyPair.getPrivate();
	}

	// 根据原公钥byte还原公钥对象
	public static PublicKey buildPublicKey(byte[] bytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return buildPublicKey(bytes, ALGORITHM_RSA);
	}

	public static PublicKey buildPublicKey(byte[] bytes, String algorithm)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(bytes));
	}

	/**
	 * RSA
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static <T extends PublicKey> T createPublicKey() throws NoSuchAlgorithmException {
		return createPublicKey(ALGORITHM_RSA);
	}

	public static <T extends PublicKey> T createPublicKey(String algorithm) throws NoSuchAlgorithmException {
		KeyPair keyPair = keyPairMap.get(algorithm);
		if (keyPair == null) {
			keyPair = generateKeyPair(algorithm);
		}
		return (T) keyPair.getPublic();
	}

}
