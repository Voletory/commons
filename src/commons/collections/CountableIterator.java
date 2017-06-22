/**
 * X
 * Copyright (c) 2014-2014 X company,Inc.All Rights Reserved.
 */
package commons.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import commons.scroll.Countable;

/**
 * 可计数迭代器
 * 
 * @author bailey_fu
 * @data 2014-1-23
 * @version $Id: CountableIterator.java, v 0.1 2014-1-23 下午05:28:34 bailey_fu
 *          Exp $
 * @Description 获取[集合]的值或下标地址,取元素返回Null或取下标返回-1则表示所有元素都已遍历
 */
@SuppressWarnings("unchecked")
public abstract class CountableIterator<E> implements Countable, Iterator<E> {
	protected E[] source = null;
	protected List<Integer> indexList = new ArrayList<Integer>();
	private int size = 0;
	private int obtainCount = 0;
	private int startOrder = 0;
	/** 当前元素下标 */
	private int currentIndex = -1;

	public CountableIterator(Collection<E> source) {
		this.source = (E[]) source.toArray();
		init();
	}

	public CountableIterator(Collection<E> source, int startOrder) {
		this.source = (E[]) source.toArray();
		this.startOrder = startOrder;
		init();
	}

	public CountableIterator(E[] source) {
		this.source = source;
		init();
	}

	public CountableIterator(E[] source, int startOrder) {
		this.source = source;
		this.startOrder = startOrder;
		init();
	}

	private void init() {
		obtainCount = startOrder;
		indexList.clear();
		currentIndex = -1;
		size = source == null ? 0 : source.length;
		if (size > 0) {
			for (int i = getStartIndex(); i < size; i++) {
				indexList.add(i);
			}
		}
	}

	/**
	 * 实际持有元素总数
	 * 
	 * @return
	 */
	public int sourceSize() {
		return source == null ? 0 : source.length;
	}

	/**
	 * 可迭代元素总数
	 * 
	 * @return
	 */
	public int size() {
		return indexList.size();
	}

	public boolean hasNext() {
		return indexList.size() == 0 ? false : true;
	}

	/**
	 * 下一个元素索引
	 * 
	 * @return
	 */
	public int nextIndex() {
		if (indexList.size() == 0)
			return -1;
		obtainCount++;
		currentIndex = createIndex();
		return currentIndex;
	}

	public E next() {
		int index = nextIndex();
		if (index == -1)
			return null;
		return source[index];
	}

	/**
	 * 
	 * 删除当前元素
	 */
	public void remove() {
		if (currentIndex < 0 || currentIndex >= indexList.size())
			return;
		int deleteIndex = -1;
		for (int indexValue : indexList) {
			deleteIndex++;
			if (indexValue == currentIndex) {
				break;
			}
		}
		if (deleteIndex != -1) {
			indexList.remove(deleteIndex);
			currentIndex = -1;
		}
	}

	public int getOrder() {
		return obtainCount;
	}

	/**
	 * 指定计数起始
	 * 
	 * @param obtainCount
	 */
	public void assignOrder(int obtainCount) {
		this.obtainCount = obtainCount;
	}

	/**
	 * 设置默认计数起始
	 * 
	 * @param startOrder
	 */
	public void setStartOrder(int startOrder) {
		this.startOrder = startOrder;
	}

	public void resetOrder() {
		obtainCount = startOrder;
	}

	public List<E> getSource() {
		if (source != null)
			return Arrays.asList(source);
		return null;
	}

	public E[] getArraySource() {
		return source;
	}

	/**
	 * 设置源并初始化
	 * 
	 * @param source
	 */
	public void setSource(Collection<E> source) {
		this.source = (E[]) source.toArray();
		init();
	}

	/**
	 * 设置源并初始化
	 * 
	 * @param source
	 */
	public void setSource(E[] source) {
		this.source = source;
		init();
	}

	/**
	 * 重置;恢复初始化状态
	 */
	public void reset() {
		init();
	}

	/**
	 * 创造下一个元素位置索引
	 * 
	 * @return
	 */
	abstract protected int createIndex();

	/**
	 * 开始的位置索引
	 * 
	 * @return
	 */
	abstract protected int getStartIndex();
}
