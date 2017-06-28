package commons.beanutils;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Protocol Buffer序列化<br/>
 * 跟java序列化相比，体积大约是其1/30-4/5，但所需时间是其10倍；仅适合于大对象，如10M
 * 
 * @author fuli
 * @version 1.0
 * @date 2017-05-24 17:30
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ProtostuffSerializer {
	private static ConcurrentHashMap<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

	public static <T> byte[] serialize(final T source) {
		final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			final Schema schema = getSchema(source.getClass());
			return ProtostuffIOUtil.toByteArray(source, schema, buffer);
		} catch (final Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	public static <T> T deserialize(Class<T> clazz, final byte[] bytes) {
		try {
			final Schema schema = getSchema(clazz);
			Object target = schema.newMessage();
			ProtostuffIOUtil.mergeFrom(bytes, target, schema);
			return target == null ? null : (T) target;
		} catch (final Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private static <T> Schema<T> getSchema(Class<T> clazz) {
		Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clazz);
			cachedSchema.put(clazz, schema);
		}
		return schema;
	}
}
