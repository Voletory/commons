package com.lz.components.common.beanutil;

import java.util.function.Supplier;

import com.lz.components.common.exception.LzRuntimeException;

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
					} catch (Exception e) {
						throw new LzRuntimeException(e);
					}
				}
			}
		}
		return (T)target;
	}
}
