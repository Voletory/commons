package commons.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import commons.scroll.BasedScrollable;
import commons.scroll.ScrollException;
import commons.scroll.page.Page;
import commons.scroll.strategy.BasicScrollStrategy;
import commons.scroll.strategy.ScrollStrategy;

/**
 * 滚动容器
 * 
 * @author bailey.fu
 * @date Oct 12, 2008
 * @version 2.0
 * @description 容器滚动获取辅助类<br/>
 *              滚动获取集合的部分内容
 */
@SuppressWarnings("unchecked")
public class ScrollableCollection<E> extends BasedScrollable<E> {
	private E[] source;

	public ScrollableCollection(Collection<E> sourceCollection) {
		super();
		source = (E[]) sourceCollection.toArray();
		setTotalLength(source.length);
	}

	public ScrollableCollection(E[] sourceArray) {
		super();
		source = sourceArray;
		setTotalLength(source.length);
	}

	public ScrollableCollection(Page page, Collection<E> sourceCollection) {
		super(new BasicScrollStrategy(page.getPageSize(), page.getStartPage()));
		source = (E[]) sourceCollection.toArray();
		setTotalLength(source.length);
	}

	public ScrollableCollection(Page page, E[] sourceArray) {
		super(new BasicScrollStrategy(page.getPageSize(), page.getStartPage()));
		source = sourceArray;
		setTotalLength(source.length);
	}

	public ScrollableCollection(ScrollStrategy scrollStrategy, Collection<E> sourceCollection) {
		super(scrollStrategy);
		source = (E[]) sourceCollection.toArray();
		setTotalLength(source.length);
	}

	public ScrollableCollection(ScrollStrategy scrollStrategy, E[] sourceArray) {
		super(scrollStrategy);
		source = sourceArray;
		setTotalLength(source.length);
	}

	/**
	 * 设置滚动点
	 * 
	 * @param incisionPoint
	 */
	private void setIncisionPoint(int incisionPoint) {
		scrollStrategy.setIncisionPoint(incisionPoint);
		makePoint();
	}

	/**
	 * 上一个滚动点内容
	 * 
	 * @return
	 * @throws ScrollException
	 */
	public Collection<E> previousIncision() throws ScrollException {
		if (previousIncisionPoint() > 1) {
			setIncisionPoint(previousIncisionPoint());
			return obtainIncision();
		}
		return null;
	}

	/**
	 * 下一个滚动点内容
	 * 
	 * @return
	 * @throws ScrollException
	 */
	public Collection<E> nextIncision() throws ScrollException {
		if (haveNextIncisionPoint()) {
			setIncisionPoint(nextIncisionPoint());
			return obtainIncision();
		}
		return null;
	}

	// ----------------------- Specify ---------------------------
	public Collection<E> obtainIncision(int incisionPoint) throws ScrollException {
		setIncisionPoint(incisionPoint);
		return obtainIncision();
	}

	public List<E> obtainIncisionWithList(int incisionPoint) throws ScrollException {
		setIncisionPoint(incisionPoint);
		return obtainIncisionWithList();
	}

	public E[] obtainIncisionWithArray(int incisionPoint) throws ScrollException {
		setIncisionPoint(incisionPoint);
		return obtainIncisionWithArray();
	}

	// ----------------------- Current ---------------------------
	public Collection<E> obtainIncision() throws ScrollException {
		return Arrays.asList(obtainIncisionWithArray());
	}

	public List<E> obtainIncisionWithList() throws ScrollException {
		return Arrays.asList(obtainIncisionWithArray());
	}

	public E[] obtainIncisionWithArray() throws ScrollException {
		if (scrollStrategy.isCheckRange()) {
			if (startPoint < 0)
				throw new ScrollException(ScrollException.NEGATIVE_INCISIONPOINT);
			else if (startPoint > endPoint)
				throw new ScrollException(ScrollException.INCISIONPOINT_OUTOF_TOTAL);
		}

		int length = endPoint - startPoint;
		E[] result = (E[]) new Object[length];
		if (length > 0) {
			int tempStartPoint = startPoint;

			int index = 0;
			for (; tempStartPoint < endPoint; tempStartPoint++, index++)
				result[index] = source[tempStartPoint];
		}
		return result;
	}

	public Collection<E> obtainIncisionByReroute() throws ScrollException {
		return Arrays.asList(obtainIncisionWithArrayByReroute());
	}

	public List<E> obtainIncisionWithListByReroute() throws ScrollException {
		return Arrays.asList(obtainIncisionWithArrayByReroute());
	}

	/** 逆向获取 */
	public E[] obtainIncisionWithArrayByReroute() throws ScrollException {
		if (scrollStrategy.isCheckRange()) {
			if (startPoint < 0)
				throw new ScrollException(ScrollException.NEGATIVE_INCISIONPOINT);
			else if (startPoint > endPoint)
				throw new ScrollException(ScrollException.INCISIONPOINT_OUTOF_TOTAL);
		}
		int length = source.length - 1;
		E[] result = null;
		startPoint = length - startPoint;
		endPoint = length - endPoint;

		result = (E[]) new Object[startPoint - endPoint];
		if (result.length > 0) {
			int temp = 0;
			for (; startPoint > endPoint; startPoint--, temp++) {
				result[temp] = source[startPoint];
			}
		}
		return result;
	}

	// ------------------------ Source ---------------------------
	public Collection<E> getSource() {
		return Arrays.asList(source);
	}

	public List<E> getSourceList() {
		return Arrays.asList(source);
	}

	public E[] getSourceArray() {
		return source;
	}

	public void setSource(Collection<E> sourceCollection) {
		source = (E[]) sourceCollection.toArray();
		setTotalLength(source.length);
	}

	/**
	 * 获取指定分割点的Iterator
	 * 
	 * @param incisionPoint
	 * @return
	 */
	public SequenceIterator<E> iterator(int incisionPoint) {
		if (incisionPoint < 1 || incisionPoint > scrollStrategy.getIncisionPointTotal()) {
			return null;
		}
		setIncisionPoint(incisionPoint);
		return super.iterator();
	}

}
