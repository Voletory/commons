package commons.collections;

import java.util.Collection;

/**
 * 顺序迭代器
 * 
 * @author bailey.fu
 * @date Nov 17, 2009
 * @version 2.0
 * @description
 */
public class SequenceIterator<E> extends CountableIterator<E> {
	/** 是否循环提取元素---默认不循环 */
	private boolean isCycle = false;
	/** 开始的位置 */
	private int startIndex;
	/** 当前的位置 */
	private int currentIndex = -1;

	public SequenceIterator(Collection<E> source, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
	}

	public SequenceIterator(Collection<E> source, boolean isCycle, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
		this.isCycle = isCycle;
	}

	public SequenceIterator(Collection<E> source, int startIndex, boolean isCycle, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
		this.startIndex = startIndex >= this.source.length ? this.source.length - 1 : startIndex < 0 ? 0 : startIndex;
		this.isCycle = isCycle;
	}

	public SequenceIterator(E[] source, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
	}

	public SequenceIterator(E[] source, boolean isCycle, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
		this.isCycle = isCycle;
	}

	public SequenceIterator(E[] source, int startIndex, boolean isCycle, int... startOrder) {
		super(source, startOrder.length == 0 ? 0 : startOrder[0]);
		this.startIndex = startIndex >= this.source.length ? this.source.length - 1 : startIndex < 0 ? 0 : startIndex;
		this.isCycle = isCycle;
	}

	@Override
	protected int createIndex() {
		int index = -1;
		if (isCycle) {
			currentIndex++;
			if (currentIndex >= indexList.size())
				currentIndex = 0;
			index = indexList.get(currentIndex);
		} else {
			index = indexList.get(0);
			indexList.remove(0);
		}
		return index;
	}

	@Override
	protected int getStartIndex() {
		return startIndex;
	}

	public boolean isCycle() {
		return isCycle;
	}
}
