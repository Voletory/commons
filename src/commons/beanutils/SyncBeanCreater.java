package commons.beanutils;

import java.util.function.Supplier;

/**
 * 
 * @author BaileyFu
 * @version v1.0
 * @date 2018年11月17日
 */
public interface SyncBeanCreater {
	@SuppressWarnings("unchecked")
	default <T> T syncCreate(Supplier<Object> exists, Supplier<Object> create) {
		Object target=exists.get();
		if (target == null) {
			synchronized (this) {
				target = exists.get();
				if (target == null) {
					try {
						target = create.get();
					} catch (RuntimeException e) {
						throw e;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return (T)target;
	}
}
