package commons.codec.encryption;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 对加密注解的处理
 * 
 * @author fuli
 * @version 1.0
 * @date 2017-08-01 11:41
 */
public interface SecretProcessor {
	final String VALUE_ENCRYPTED = "ENCRYPTED";
	final String VALUE_PLAINTEXT = "PLAIN_TEXT";
	final Map<String, String> EMPTY_SALTS_MAP = new HashMap<>();

	//ENCRYPT
	default <T> T encryptBean(T t) throws Exception {
		return encryptBean(t, EMPTY_SALTS_MAP);
	}
	/**
	 * 
	 * @param t
	 * @param salt<br/>
	 * 			相当于privateSalts只有一个元素,此时Encrypt.privateSalt可为任意值
	 * @return
	 * @throws Exception
	 */
	default <T> T encryptBean(T t, String salt) throws Exception {
		Map<String, String> privateSalts = new HashMap<>();
		privateSalts.put("DEFAULT_PRIVATE_SALT", salt);
		return encryptBean(t, privateSalts);
	}
	default <T> T encryptBean(T t, Map<String, String> privateSalts) throws Exception {
		String signValue = SignEnhancer.getSignValue(t);
		if (signValue == null || signValue.equals(VALUE_PLAINTEXT)) {
			t = encryptOriginalBean(t, privateSalts);
		}
		return SignEnhancer.makeSign(t, VALUE_ENCRYPTED);
	}
	/**
	 * 
	 * @param t
	 * @param privateSecrets 各field的加解密key
	 * @param privateSalts 各field的盐
	 * @return
	 * @throws Exception
	 */
	default <T> T encryptBean(T t, Map<String, byte[]> privateSecrets, Map<String, String> privateSalts) throws Exception {
		String signValue = SignEnhancer.getSignValue(t);
		if (signValue == null || signValue.equals(VALUE_PLAINTEXT)) {
			t = encryptOriginalBean(t, privateSecrets, privateSalts);
		}
		return SignEnhancer.makeSign(t, VALUE_ENCRYPTED);
	}
	default <T> T encryptOriginalBean(T t) throws Exception {
		return encryptOriginalBean(t, EMPTY_SALTS_MAP);
	}
	default <T> T encryptOriginalBean(T t, String salt) throws Exception {
		Map<String, String> privateSalts = new HashMap<>();
		privateSalts.put("DEFAULT_PRIVATE_SALT", salt);
		return encryptOriginalBean(t, privateSalts);
	}
	default <T> T encryptOriginalBean(T t, Map<String, String> privateSalts) throws Exception {
		return encryptOriginalBean(t, null, privateSalts);
	}
	default <T> T encryptOriginalBean(T t,Map<String, byte[]> privateSecrets, Map<String, String> privateSalts) throws Exception {
		Encrypt classEncrypt = t.getClass().getDeclaredAnnotation(Encrypt.class);
		for (Field field : SignEnhancer.makeSureClass(t.getClass()).getDeclaredFields()) {
			EncryptResolver encryptResolver = new EncryptResolver(classEncrypt, field.getDeclaredAnnotation(Encrypt.class));
			field.setAccessible(true);
			String value = (String) field.get(t);
			field.set(t, encryptResolver.encrypt(value, privateSecrets, privateSalts));
		}
		return t;
	}
	/// DECRYPT
	default <T> T decryptBean(T t) throws Exception {
		return decryptBean(t, null, EMPTY_SALTS_MAP);
	}
	default <T> T decryptBean(T t, String salt) throws Exception {
		Map<String, String> privateSalts = new HashMap<>();
		privateSalts.put("DEFAULT_PRIVATE_SALT", salt);
		return decryptBean(t, null, privateSalts);
	}
	default <T> T decryptBean(T t, Map<String, byte[]> privateSecrets, Map<String, String> privateSalts) throws Exception {
		String signValue = SignEnhancer.getSignValue(t);
		if (signValue == null || signValue.equals(VALUE_ENCRYPTED)) {
			t = decryptOriginalBean(t, privateSecrets, privateSalts);
		}
		return SignEnhancer.makeSign(t, VALUE_PLAINTEXT);
	}
	default <T> T decryptOriginalBean(T t) throws Exception {
		return decryptOriginalBean(t,null, EMPTY_SALTS_MAP);
	}
	default <T> T decryptOriginalBean(T t, String salt) throws Exception {
		Map<String, String> privateSalts = new HashMap<>();
		privateSalts.put("DEFAULT_PRIVATE_SALT", salt);
		return decryptOriginalBean(t, null,privateSalts);
	}
	default <T> T decryptOriginalBean(T t, Map<String, byte[]> privateSecrets, Map<String, String> privateSalts) throws Exception {
		Encrypt classEncrypt = t.getClass().getDeclaredAnnotation(Encrypt.class);
		for (Field field : SignEnhancer.makeSureClass(t.getClass()).getDeclaredFields()) {
			EncryptResolver encryptResolver = new EncryptResolver(classEncrypt, field.getDeclaredAnnotation(Encrypt.class));
			field.setAccessible(true);
			String value = (String) field.get(t);
			field.set(t, encryptResolver.decrypt(value, privateSecrets, privateSalts));
		}
		return t;
	}

}
