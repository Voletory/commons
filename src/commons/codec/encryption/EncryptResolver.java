package commons.codec.encryption;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;

import commons.codec.AESEncrypter;
import commons.codec.B64Encrypter;
import commons.codec.Hex;
import commons.codec.MD5Encrypter;

public final class EncryptResolver {
	private Algorithm alg = Algorithm.NULL;
	private byte[] publicSecret = null;
	private String privateSecretName = null;
	private String publicSalt = null;
	private String privateSaltName = null;
	EncryptResolver(Encrypt classEncrypt, Encrypt fieldEncrypt) {
		if (fieldEncrypt != null) {
			String publicSecretName = SecretKeeper.DEFAULT_NAME;
			String publicSaltName = null;
			if (classEncrypt != null) {
				alg = classEncrypt.alg();
				publicSecretName = classEncrypt.publicSecret();
				publicSaltName = classEncrypt.publicSalt();
			}
			if (fieldEncrypt != null) {
				alg = fieldEncrypt.alg() != Algorithm.NULL ? fieldEncrypt.alg() : alg;
				publicSecretName = StringUtils.isNotBlank(fieldEncrypt.publicSecret()) ? fieldEncrypt.publicSecret() : publicSecretName;
				publicSaltName = StringUtils.isNotBlank(fieldEncrypt.publicSalt()) ? fieldEncrypt.publicSalt() : publicSaltName;
				privateSecretName=fieldEncrypt.privateSecret();
				privateSaltName = fieldEncrypt.privateSalt();
			}
			publicSecret = StringUtils.isNotBlank(publicSecretName) ? SecretKeeper.getSecret(publicSecretName) : SecretKeeper.getSecret();
			publicSalt = SecretKeeper.getSalt(publicSaltName);
		}
	}

	String encrypt(String value, Map<String, byte[]> privateSecrets, Map<String, String> privateSalts) throws Exception {
		if (StringUtils.isBlank(value) || alg == Algorithm.NULL)
			return value;
		byte[] secret = publicSecret;
		if (StringUtils.isNotBlank(privateSecretName)) {
			Asserts.notNull(privateSecrets, "privateSecrets");
			secret = privateSecrets.get(privateSecretName);
//			Asserts.notNull(secret, "privateSalt", "没有在privateSecrets找到key为" + privateSecretName + "的Secret");
			Asserts.notNull(secret, "privateSalt");
		}
		String salt = publicSalt;
		if(StringUtils.isNotBlank(privateSaltName)){
			Asserts.notNull(privateSalts, "privateSalts");
			salt = privateSalts.size() == 1 ? privateSalts.values().iterator().next() : privateSalts.get(privateSaltName);
//			Asserts.notEmpty(salt, "privateSalt","没有在privateSalts找到key为"+privateSaltName+"的salt");
			Asserts.notEmpty(salt, "privateSalt");
		}
		String original = value;
		if (StringUtils.isNotBlank(salt))
			value = value + salt;
		if (alg == Algorithm.AES && secret != null) {
			return AESEncrypter.newInstance(new String(secret), true).encode(value.getBytes());
		}
		if (alg == Algorithm.B64) {
			return B64Encrypter.encrypt(value.getBytes());
		}
		if (alg == Algorithm.HEX) {
			return Hex.encodeHexStr(value.getBytes());
		}
		if (alg == Algorithm.MD5) {
			return MD5Encrypter.newInstance(false).encode(value);
		}
		return original;
	}
	String decrypt(String value, Map<String, byte[]> privateSecrets, Map<String, String> privateSalts) throws Exception {
		if (StringUtils.isBlank(value) || alg == Algorithm.NULL)
			return value;
		byte[] secret = publicSecret;
		if (StringUtils.isNotBlank(privateSecretName)) {
			Asserts.notNull(privateSecrets, "privateSecrets");
			secret = privateSecrets.get(privateSecretName);
//			Asserts.notNull(secret, "privateSalt", "没有在privateSecrets找到key为" + privateSecretName + "的Secret");
			Asserts.notNull(secret, "privateSalt");
		}
		String salt = publicSalt;
		if(StringUtils.isNotBlank(privateSaltName)){
			Asserts.notNull(privateSalts, "privateSalts");
			salt = privateSalts.size() == 1 ? privateSalts.values().iterator().next() : privateSalts.get(privateSaltName);
//			Asserts.notEmpty(salt, "privateSalt","没有在privateSalts找到key为"+privateSaltName+"的salt");
			Asserts.notEmpty(salt, "privateSalt");
		}
		String decryptedValue = null;
		if (alg == Algorithm.AES) {
			if (secret == null)
				return value;
			decryptedValue = new String(AESEncrypter.newInstance(new String(secret), true).decode(value));
		} else if (alg == Algorithm.B64) {
			decryptedValue = new String(B64Encrypter.decrypt(value));
		} else if (alg == Algorithm.HEX) {
			decryptedValue = new String(Hex.decodeHex(value.toCharArray()));
		} else if (alg == Algorithm.MD5) {
			return value;
		}
		return StringUtils.isNotBlank(salt) ? StringUtils.substringBeforeLast(decryptedValue, salt) : decryptedValue;
	}
}
