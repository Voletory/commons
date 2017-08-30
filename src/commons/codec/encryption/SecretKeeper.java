package commons.codec.encryption;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import commons.codec.B64Encrypter;

public class SecretKeeper {
	public static final String DEFAULT_NAME="DEFAULT";
	private static Map<String,String> publicSalts=new HashMap<>();
	private static Map<String,byte[]> secrets=new HashMap<>();
	public static String getSalt() {
		return publicSalts.get(DEFAULT_NAME);
	}
	public static String getSalt(String saltName) {
		return publicSalts.get(saltName);
	}
	public static void setSalt(String salt) {
		SecretKeeper.publicSalts.put(DEFAULT_NAME, salt);
	}
	public static void setSalt(String saltName, String salt) {
		SecretKeeper.publicSalts.put(saltName, salt);
	}
	public static void setSalts(Map<String, String> salts) {
		SecretKeeper.publicSalts = salts;
	}
	
	public static void setHEXSecret(String HEXsecret) throws DecoderException {
		secrets.put(DEFAULT_NAME, StringUtils.isNotBlank(HEXsecret) ? Hex.decodeHex(HEXsecret.trim().toCharArray()) : null);
	}
	public static void setHEXSecret(String scretName, String HEXsecret) throws DecoderException {
		secrets.put(scretName, StringUtils.isNotBlank(HEXsecret) ? Hex.decodeHex(HEXsecret.trim().toCharArray()) : null);
	}
	public static void setHEXSecrets(Map<String, String> HEXsecrets) throws DecoderException {
		for(String key:HEXsecrets.keySet()){
			setHEXSecret(key,HEXsecrets.get(key));
		}
	}
	
	public static void setB64Secret(String B64secret) throws UnsupportedEncodingException {
		secrets.put(DEFAULT_NAME, StringUtils.isNotBlank(B64secret) ? B64Encrypter.decrypt(B64secret) : null);
	}
	public static void setB64Secret(String scretName, String B64secret) throws UnsupportedEncodingException {
		secrets.put(scretName, StringUtils.isNotBlank(B64secret) ? B64Encrypter.decrypt(B64secret) : null);
	}
	public static void setB64Secrets(Map<String, String> B64secrets) throws DecoderException, UnsupportedEncodingException {
		for(String key:B64secrets.keySet()){
			setB64Secret(key,B64secrets.get(key));
		}
	}
	
	public static void setStringSecret(String Stringsecret) {
		secrets.put(DEFAULT_NAME, StringUtils.isNotBlank(Stringsecret) ? Stringsecret.getBytes() : null);
	}
	public static void setStringSecret(String scretName, String Stringsecret) {
		secrets.put(scretName, StringUtils.isNotBlank(Stringsecret) ? Stringsecret.getBytes() : null);
	}
	public static void setStringSecrets(Map<String, String> Stringsecrets) throws DecoderException, UnsupportedEncodingException {
		for (String key : Stringsecrets.keySet()) {
			setStringSecret(key, Stringsecrets.get(key));
		}
	}
	
	public static byte[] getSecret(){
		return secrets.get(DEFAULT_NAME);
	}
	public static byte[] getSecret(String scretName){
		return secrets.get(scretName);
	}
}
