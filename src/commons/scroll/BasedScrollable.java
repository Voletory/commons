package commons.scroll;

import commons.collections.SequenceIterator;
import commons.log.XLogger;
import commons.scroll.page.Page;
import commons.scroll.strategy.BasicScrollStrategy;
import commons.scroll.strategy.ScrollStrategy;

/**
 * 滚动实现基类
 * 
 * @author bailey.fu
 * @date Oct 14, 2009
 * @version 2.0
 * @description
 */
public abstract class BasedScrollable<E> implements Scrollable<E>, Iterable<E> {
	/** 已是第一个滚动点 */
	public static final int NOT_PREVIOUS_INCISIONPOINT = 0;
	/** 已是最后一个滚动点 */
	public static final int NOT_NEXT_INCISIONPOINT = -1;

	/** 滚动策略 */
	protected ScrollStrategy scrollStrategy;
	/** 滚动源长度 */
	protected int totalLength;
	/** 滚动点开始的位置 */
	protected int startPoint;
	/** 滚动点结束的位置 */
	protected int endPoint;
	/** 序列 */
	protected int startOrder;

	public BasedScrollable() {
		this.scrollStrategy = new BasicScrollStrategy();
		this.totalLength = 0;
		this.startPoint = 0;
		this.endPoint = 0;
	}

	public BasedScrollable(ScrollStrategy scrollStrategy) {
		this.scrollStrategy = scrollStrategy;
		this.totalLength = 0;
		this.startPoint = 0;
		this.endPoint = 0;
	}

	public void setPage(Page page) {
		setScrollStrategy(new BasicScrollStrategy(page.getPageSize(), page.getStartPage()));
	}

	public void setScrollStrategy(ScrollStrategy scrollStrategy) {
		this.scrollStrategy = scrollStrategy;
		setTotalLength(totalLength);
	}

	public ScrollStrategy getScrollStrategy() {
		return scrollStrategy;
	}

	/**
	 * 生成滚动点[开始/结束]位置并初始化序号
	 */
	protected void makePoint() {
		int revealLength = scrollStrategy.getRevealLength();
		startPoint = (scrollStrategy.getIncisionPoint() - 1) * revealLength;
		endPoint = startPoint + revealLength;
		endPoint = endPoint > totalLength ? totalLength : endPoint;

		startOrder = (scrollStrategy.getIncisionPoint() - 1) * scrollStrategy.getRevealLength() + 1;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public int getEndPoint() {
		return endPoint;
	}

	/**
	 * 设置滚动源长度
	 * 
	 * @param totalLength
	 */
	protected void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
		int revealLength = scrollStrategy.getRevealLength();
		int residue = this.totalLength % revealLength;
		int quotient = this.totalLength / revealLength;
		scrollStrategy.setIncisionPointTotal(residue == 0 ? quotient : (quotient + 1));
		makePoint();
	}

	public int getTotalLength() {
		return totalLength;
	}

	public int getTotalIncisionPoint() {
		return scrollStrategy.getIncisionPointTotal();
	}

	/**
	 * 滚动节点总数
	 * 
	 * @return
	 */
	public int getIncisionPointTotal() {
		return scrollStrategy.getIncisionPointTotal();
	}

	public int getCurrentIncisionPoint() {
		return scrollStrategy.getIncisionPoint();
	}

	/**
	 * 滚动长度
	 * 
	 * @return
	 */
	public int getRevealLength() {
		return scrollStrategy.getRevealLength();
	}

	public boolean haveNextIncisionPoint() {
		return scrollStrategy.getIncisionPoint() < scrollStrategy.getIncisionPointTotal();
	}

	public int nextIncisionPoint() {
		return haveNextIncisionPoint() ? (scrollStrategy.getIncisionPoint() + 1) : NOT_NEXT_INCISIONPOINT;
	}

	public int previousIncisionPoint() {
		int currentIncisionPoint = scrollStrategy.getIncisionPoint();
		return currentIncisionPoint > 1 ? currentIncisionPoint - 1 : NOT_PREVIOUS_INCISIONPOINT;
	}

	public int getOrder(int incisionIndex, Order... order) {
		if (incisionIndex < 0)
			return 0;
		if (order.length == 0 || order[0].isASC()) {
			return startOrder + incisionIndex;
		} else {
			return totalLength - (startOrder + incisionIndex) + 1;
		}
	}

	public SequenceIterator<E> iterator() {
		SequenceIterator<E> sIterator = null;
		try {
			sIterator = new SequenceIterator<E>(obtainIncision());
			sIterator.assignOrder(startOrder);
		} catch (ScrollException e) {
			XLogger.error(getClass() + " create SequenceIterator failed . cause : {}", e);
		}
		return sIterator;
	}

}
