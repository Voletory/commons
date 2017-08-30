package commons.codec.encryption;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.TYPE})
@Inherited
public @interface Encrypt {
	/**
	 * SecretKeeper.secrets密匙的key
	 */
	String publicSecret() default StringUtils.EMPTY;
	/**
	 * 调用加密算法时传入的动态密匙;会覆盖publicSecret(设置此值则加/解密时必须传入密匙)
	 */
	String privateSecret() default StringUtils.EMPTY;
	/**
	 * SecretKeeper.salts盐(公共盐)的key
	 */
	String publicSalt() default StringUtils.EMPTY;
	/**
	 * 调用加密算法时传入的动态盐;会覆盖publicSalt(设置此值则加/解密时必须传入盐)
	 */
	String privateSalt() default StringUtils.EMPTY;
	/**
	 * 加密算法
	 */
	Algorithm alg() default Algorithm.NULL;
}
