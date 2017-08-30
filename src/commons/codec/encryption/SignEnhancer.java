package commons.codec.encryption;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import commons.beanutils.ReflectionUtils;
import commons.lang.StringUtils;

/**
 * 
 * @author fuli
 * @version 1.0
 * @date 2017-08-16 10:09
 */
public class SignEnhancer {
	private static final String SIGN_NAME = "_KPF_SECURITY_IS_ENCRYPTED_";
	private static final String EXTEND_NAME = "$WithSignOfKPBS";
	private static Map<String, Class<?>> enhancedClazzMap = new HashMap<>();
	@SuppressWarnings("unchecked")
	static <T> T makeSign(T t, String signValue) {
		T result = t;
		String clazzName = t.getClass().getName();
		if (!clazzName.endsWith(EXTEND_NAME)) {
			clazzName += EXTEND_NAME;
		}
		Class<?> enhancedClazz = enhancedClazzMap.get(clazzName);
		if (enhancedClazz == null) {
			try {
				ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				ClassReader reader = new ClassReader(t.getClass().getName());
				reader.accept(new SignClassAdapter(Opcodes.ASM5, cw), ClassReader.SKIP_DEBUG);
				byte[] code = cw.toByteArray();
				enhancedClazz = new MyLoader().loadClass(t.getClass().getName() + EXTEND_NAME, code);
				enhancedClazzMap.put(clazzName, enhancedClazz);
				result = (T) enhancedClazz.newInstance();
				for (Field f : t.getClass().getDeclaredFields()) {
					f.setAccessible(true);
					Object value = f.get(t);
					f.set(result, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			ReflectionUtils.setFieldValue(result, SIGN_NAME, signValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	static String getSignValue(Object obj) {
		try {
			Field field = obj.getClass().getDeclaredField(SIGN_NAME);
			field.setAccessible(true);
			return StringUtils.defaultString(field.get(obj), null);
		} catch (NoSuchFieldException nfe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	static Class<?> makeSureClass(Class<?> clazz) {
		return clazz.getName().endsWith(EXTEND_NAME) ? clazz.getSuperclass() : clazz;
	}
	private static class MyLoader extends ClassLoader {
		public Class<?> loadClass(String className, byte[] classFile) throws ClassFormatError {
			return defineClass(className, classFile, 0, classFile.length);
		}
	}
	private static class SignClassAdapter extends ClassVisitor implements Opcodes {
		public SignClassAdapter(int api, ClassVisitor cv) {
			super(api, cv);
		}
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			super.visit(version, access, name + EXTEND_NAME, signature, name, interfaces);
			{
				MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
				mv.visitCode();
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESPECIAL, name, "<init>", "()V", false);
				mv.visitInsn(RETURN);
				mv.visitMaxs(1, 1);
				mv.visitEnd();
			}
		}
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			return null;
		}

		@Override
		public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
			return null;
		}
		@Override
		public void visitEnd() {
			FieldVisitor fv = super.visitField(ACC_PUBLIC, SIGN_NAME, Type.getDescriptor(String.class), null, null);
			fv.visitEnd();
			super.visitEnd();
		}
	}
}
