package commons.scroll;

import java.util.Collection;

import commons.scroll.page.Page;
import commons.scroll.strategy.BasicScrollStrategy;
import commons.scroll.strategy.ScrollStrategy;

/**
 * 查询结果集滚动类
 * 
 * @author bailey.fu
 * @date Oct 14, 2009
 * @version 2.0
 * @description
 */
public class ScrollableResult<E> extends BasedScrollable<E> {
	private Collection<E> incision;

	public ScrollableResult() {
		super();
	}

	public ScrollableResult(Page page) {
		super(new BasicScrollStrategy(page.getPageSize(), page.getStartPage()));
	}

	public ScrollableResult(ScrollStrategy scrollStrategy) {
		super(scrollStrategy);
	}

	public Collection<E> obtainIncision() throws ScrollException {
		return incision;
	}

	public Collection<E> getSource() {
		return incision;
	}

	/**
	 * 设置查询的记录总大小
	 * 
	 * @param rowTotal
	 * @throws ScrollException
	 */
	public void setRowTotal(int rowTotal) {
		setTotalLength(rowTotal);
	}

	public void setIncision(Collection<E> incision) {
		this.incision = incision;
	}
}
