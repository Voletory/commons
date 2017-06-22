package commons.beanutils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import commons.log.XLogger;

/**
 * 反射工具类
 * 
 * @author bailey.fu
 * @date Oct 13, 2009
 * @update 2016-12-16 11:42
 * @version 1.3
 * @description
 */
@SuppressWarnings("unchecked")
public final class ReflectionUtils {

	public static <E> E getInstanceByName(String className) throws Exception {
		Class<?> clazz = Class.forName(className);
		return getInstance(clazz, null);
	}

	public static <E> E getInstanceByName(String className, Object[] params) throws Exception {
		Class<?> clazz = Class.forName(className);
		return getInstance(clazz, params);
	}

	public static <E> E getInstance(Class<?> clazz, Object[] params) throws Exception {
		Constructor<?> c = null;
		if (params == null || params.length == 0) {
			c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			return (E) c.newInstance();
		}
		Class<?>[] paramTypes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			paramTypes[i] = params[i].getClass();
		}
		c = clazz.getDeclaredConstructor(paramTypes);
		c.setAccessible(true);
		return (E) c.newInstance(params);
	}

	/*********************** annotation *************************/

	/************************* field ****************************/
	public static <E> E getFieldValue(Object target, String fieldName) throws Exception {
		try {
			Field field = ((target instanceof Class) ? (Class<?>) target : target.getClass()).getDeclaredField(fieldName);
			field.setAccessible(true);
			return (E) field.get(target);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void setFieldValue(Object target, String fieldName, Object value) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}

	public static Map<String, Object> getProperties(Object target) throws Exception {
		return getProperties(target, false);
	}

	public static Map<String, Object> getProperties(Object target, boolean includeStatic) throws Exception {
		if (target == null) {
			return null;
		}
		Map<String, Object> properties = new HashMap<>();
		Class<?> targetClass = (target instanceof Class) ? (Class<?>) target : target.getClass();
		for (Field f : targetClass.getDeclaredFields()) {
			if (!includeStatic && Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			f.setAccessible(true);
			properties.put(f.getName(), f.get(target));
		}
		return properties;
	}

	/************************* method *************************/
	public static <T> T invokeNoParam(Object target, String methodName) {
		if (target != null) {
			try {
				Method method = target.getClass().getDeclaredMethod(methodName);
				method.setAccessible(true);
				return (T) method.invoke(target);
			} catch (Exception e) {
				XLogger.error("ReflectionUtils.invoke(Object target, String methodName) error.", e);
			}
		}
		return null;
	}

	public static String[] getParameterNames(Class<?> clazz, Method method) {
		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes == null || parameterTypes.length == 0) {
			return null;
		}
		final Type[] types = new Type[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			types[i] = Type.getType(parameterTypes[i]);
		}
		final String[] parameterNames = new String[parameterTypes.length];
		try {
			ClassReader classReader = new ClassReader(clazz.getName());
			classReader.accept(new ClassVisitor(Opcodes.ASM5) {
				@Override
				public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
					// 只处理指定的方法
					Type[] argumentTypes = Type.getArgumentTypes(desc);
					if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
						return null;
					}
					return new MethodVisitor(Opcodes.ASM5) {
						@Override
						public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
							// 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this
							if (Modifier.isStatic(method.getModifiers())) {
								parameterNames[index] = name;
							} else if (index > 0 && index < parameterNames.length) {
								parameterNames[index - 1] = name;
							}
						}
					};
				}
			}, 0);
		} catch (IOException e) {
			XLogger.error("ReflectionUtils.getParameterNames(Class<?> clazz, Method method) error!", e);
		}
		return parameterNames;
	}

	/**
	 * 获取泛型Class
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getSuperClassGenericType(Class<?> clazz) {
		java.lang.reflect.Type genType = clazz.getGenericSuperclass();
		java.lang.reflect.Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<?>) params[0];
	}

}
