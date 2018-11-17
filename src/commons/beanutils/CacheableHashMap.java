package commons.beanutils;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 防重复获取,可缓存的Map
 * 
 * @author fuli
 * @date 2018年8月13日
 * @version 1.0.0
 */
public class CacheableHashMap<K, V> extends HashMap<K, V> {
	private static final long serialVersionUID = -8450383477709484792L;

	public V get(K k, Function<K, V> fun) {
		V v = super.get(k);
		if (v == null) {
			v = fun.apply(k);
			super.put(k, v);
		}
		return v;
	}
	public V get(K k, Supplier<V> valueGetter) {
		V v = super.get(k);
		if (v == null) {
			v = valueGetter.get();
			super.put(k, v);
		}
		return v;
	}
}
