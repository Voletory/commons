package commons.codec;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Base64编码
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 2.0
 * @update 2016-12-09 13:26
 * @description
 */
public final class B64Encrypter {
	//默认编码与sun.misc.BASE64Decoder操作相同
	static final String DEFAULT_ALPHABET="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	private static Map<String,B64Encrypter> coderMap=new HashMap<String,B64Encrypter>();
	//加密表中的ASCII码的值对应解密表的位，加密表的位对应解密表的值:A在加密表中是第0位，ASCII码值为65，则解密表的第65位为0
	private static char[] defaultEncodeChars = new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', //   0 -   9
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', //  10 -  19
			'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', //  20 -  29
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', //  30 -  39
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', //  40 -  49
			'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', //  50 -  59
			'8', '9', '+', '/' 								  //  60 -  63
	};
	private static byte[] defaultDecodeChars = new byte[] {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, //   0 -   9
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, //  10 -  19
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, //  20 -  29
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, //  30 -  39
			-1, -1, -1, 62, -1, -1, -1, 63, 52, 53, //  40 -  49
			54, 55, 56, 57, 58, 59, 60, 61, -1, -1, //  50 -  59
			-1, -1, -1, -1, -1,  0,  1,  2,  3,  4, //  60 -  69
			 5,  6,  7,  8,  9, 10, 11, 12, 13, 14, //  70 -  79
			 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,//  80 -  89
			 25, -1, -1, -1, -1, -1, -1, 26, 27, 28,//  90 -  99
			 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,// 100 - 109
			 39, 40, 41, 42, 43, 44, 45, 46, 47, 48,// 110 - 119
			 49, 50, 51, -1, -1, -1, -1, -1 		// 120 - 127
	};
	static{
		coderMap.put(DEFAULT_ALPHABET,new B64Encrypter(defaultEncodeChars,defaultDecodeChars));
	}
	private char[] base64EncodeChars;
	private byte[] base64DecodeChars;
	private B64Encrypter(char[] base64EncodeChars,byte[] base64DecodeChars){
		this.base64EncodeChars=base64EncodeChars;
		this.base64DecodeChars=base64DecodeChars;
	}
	
	public String encode(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;
		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("**");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("*");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	}
	public byte[] decode(String str) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		byte[] data = str.getBytes(Charset.ASCII.VALUE);
		int len = data.length;
		int i = 0;
		int b1, b2, b3, b4;
		while (i < len) {
			/* b1 */
			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1)
				break;
			/* b2 */
			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1)
				break;
			sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
			/* b3 */
			do {
				b3 = data[i++];
				if (b3 == 61)
					return sb.toString().getBytes(Charset.ISO8859_1.VALUE);
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1)
				break;
			sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
			/* b4 */
			do {
				b4 = data[i++];
				if (b4 == 61)
					return sb.toString().getBytes(Charset.ISO8859_1.VALUE);
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1)
				break;
			sb.append((char) (((b3 & 0x03) << 6) | b4));
		}
		return sb.toString().getBytes(Charset.ISO8859_1.VALUE);
	}
	
	public static boolean checkAlphabet(String alphabet){
		if(StringUtils.isBlank(alphabet)||alphabet.length()!=64)return false;
		byte[] temp=new byte[128];
		for(int i=0;i<64;i++){
			if(temp[alphabet.charAt(i)]>0){
				return false;
			}
			temp[alphabet.charAt(i)]=1;
		}
		return true;
	}
	public static B64Encrypter getInstance(){
		return getInstance(DEFAULT_ALPHABET);
	}
	public static B64Encrypter getInstance(String alphabet){
		if(!checkAlphabet(alphabet))return null;
		
		B64Encrypter coder=coderMap.get(alphabet);
		if(coder==null){
			char[] encodeChar=new char[64];
			byte[] decodeChar=new byte[128];
			for(int i=0;i<64;i++){
				encodeChar[i]=alphabet.charAt(i);
				decodeChar[encodeChar[i]]=Integer.valueOf(i).byteValue();
			}
			coder=new B64Encrypter(encodeChar,decodeChar); 
			coderMap.put(alphabet, coder);
		}
		return coder;
	}
	public static String encrypt(byte[] plainText) {
		return plainText==null?null:getInstance().encode(plainText);
	}
	public static byte[] decrypt(String ciphertext) throws UnsupportedEncodingException{
		return StringUtils.isBlank(ciphertext)?null:getInstance().decode(ciphertext);
	}
}
