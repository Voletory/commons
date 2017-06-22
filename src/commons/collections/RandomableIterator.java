package commons.collections;

import java.util.Collection;

import commons.util.RandomUtils;

/**
 * 随机迭代器
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 2.0
 * @description
 */
public class RandomableIterator<E> extends CountableIterator<E> {
	/** 是否重复提取元素---默认不重复 */
	private boolean isOverlap = false;

	public RandomableIterator(Collection<E> source, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
	}

	public RandomableIterator(Collection<E> source, boolean isOverlap, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
		this.isOverlap = isOverlap;
	}

	public RandomableIterator(E[] source, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
	}

	public RandomableIterator(E[] source, boolean isOverlap, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
		this.isOverlap = isOverlap;
	}

	@Override
	protected int createIndex() {
		int keyIndex = RandomUtils.getPositiveInt(indexList.size());
		int index = indexList.get(keyIndex);
		if (!isOverlap)
			indexList.remove(keyIndex);
		return index;
	}

	@Override
	protected int getStartIndex() {
		return 0;
	}

	public boolean isOverlap() {
		return isOverlap;
	}

}
