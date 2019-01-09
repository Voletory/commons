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

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

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

	public static void main(String[] args) throws Exception{
		RSAPublicKeyImpl pubk=createPublicKey();
		RSAPrivateCrtKeyImpl prik=createPrivateKey();
		String publicKey=B64Encrypter.encrypt(pubk.encode());
		String privateKey=B64Encrypter.encrypt(prik.encode());
		System.out.println(publicKey);
		System.out.println(privateKey);
		publicKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIxq6RtQQWWW78SWDHY2+zfC5RgPuTy7kFsaQI+4o21SbG92DZCtW9CRZvtKRlYj/Jk7F2yS3PAZuYWjx2ntTj0CAwEAAQ==";
		privateKey="MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAjGrpG1BBZZbvxJYMdjb7N8LlGA+5PLuQWxpAj7ijbVJsb3YNkK1b0JFm+0pGViP8mTsXbJLc8Bm5haPHae1OPQIDAQABAkAzfPRTBp+kP69Vl2ukYU7GyJk2tHVI9sHdFgg8awjKCdNtP1kT+5AbDwJMgyAUsskVBKZwB7qJnug77rq0FLWhAiEA7omVVCiowc76y+0HRMKAZxM1n6o5XeIvHs0aXA6Q3MUCIQCWsngkJRmpVEDuY0np4DljOPt96MiUKinHFM1QcGKzGQIgDu/+se554ukbO3n0YpHriIjjasQ2I3LukHc3l1wyJiECIQCBfxmojc0QaltlvyKb/Fe0QRo0J159nAHMlr6b+geGuQIgX/bVglGG+SlRintLrtSMg4yi/o26SLG+Xo/GDdwl7kQ=";
		publicKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOfZf0Mw3ckCWCzmzfn7sjq6mCMganCSbsjsxVaQYa3sXgxrJQSZe91OsFeccAxmGlGhUIEoQoiwRGqIATJXyCMCAwEAAQ==";
		privateKey="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAgrmMx8FmtbfXqo5TRFKeIufiCN3LNxMZqupdDmvhbruBM83TKTKVUVcXbBHFSZ4zN567Oi6h7xHkMKz0qFQ7UQIDAQABAkALqKrSogAcEuJsNrdcb5Xk/RNewRE/vzdZ0/8qqxp2+qdfrxWJjyrStWIqEusGEJ7E6DCdXLNFXjDscIk6vNBBAiEAx/CYwOGAjc5gZLDaFGt+JI6n61WjOsYgnP/qrZkWzZkCIQCnYMt25mOR9n56V9YETcg/0nyMdpjz4VsrTqcjIhI+eQIhALpgQdRSoMYPYfWakb9FMHIddNQ/oQfxlocjW/x03zfJAiEAi2jpzxzR7i865TfnwCnxEoeEyZG2j9syDn4yY+bq/zECIAF1RNwzBuIylKVQc4TK5C/UzKedwhNpp9NIkK1SVlWg";
		System.out.println(publicKey.length()+"publicKey : "+publicKey);
		System.out.println(privateKey.length()+"privateKey : "+privateKey);
		RSAEncrypter re=RSAEncrypter.getInstance(publicKey, privateKey);
		String sss=MD5Encrypter.encrypt("a123sa中作a").toUpperCase();
		System.out.println(sss);
		String s;
		System.out.println(s=re.encryptByPrivateKey("test encrypt".getBytes()));
		System.out.println(s=new String(re.decryptByPublicKey("qsZpP5cVcfDCDstnFBB0TdxO2RaMa0IkTI0u8v84qA2xYpYqCzpSaGv3eBoS6KvGvBe6pBkNRtevUnReU6XBtg==")));
		System.out.println(Base64.encode("ABC123ffffffffffffff".getBytes()));
		System.out.println(B64Encrypter.encrypt("ABC123ffffffffffffff".getBytes()));
		System.out.println(new String(Base64.decode("QUJDMTIzZmZmZmZmZmZmZmZmZmY=")));
//		System.out.println(new String(Base64.decode("QUJDMTIzZmZmZmZmZmZmZmZmZmY*")));
		System.out.println(new String(B64Encrypter.decrypt("QUJDMTIzZmZmZmZmZmZmZmZmZmY=")));
	}
}
