package commons.scroll;

import java.util.Collection;

import commons.scroll.strategy.ScrollStrategy;

/**
 * 滚动功能接口
 * 
 * @author bailey.fu
 * @date Oct 14, 2009
 * @version 1.2
 * @description
 */
public interface Scrollable<E> {
	/**
	 * 新的滚动策略
	 * 
	 * @param scrollStrategy
	 * @return
	 */
	public void setScrollStrategy(ScrollStrategy scrollStrategy);

	/**
	 * 获取滚动策略
	 * 
	 * @return
	 */
	public ScrollStrategy getScrollStrategy();

	/**
	 * 滚动源总大小
	 * 
	 * @return
	 */
	public int getTotalLength();

	/**
	 * 滚动点数量
	 * 
	 * @return
	 */
	public int getTotalIncisionPoint();

	/**
	 * 当前滚动点
	 * 
	 * @return
	 */
	public int getCurrentIncisionPoint();

	/**
	 * 下一个滚动点<br/>
	 * 若当前滚动点既最后滚动点则返回-1
	 * 
	 * @return
	 */
	public int nextIncisionPoint();

	/**
	 * 前一个滚动点<br/>
	 * 若当前滚动点既第一个滚动点则返回0
	 * 
	 * @return
	 */
	public int previousIncisionPoint();

	/**
	 * 是否还有下一个滚动点 (当前滚动点是否最大滚动点)
	 * 
	 * @return
	 */
	public boolean haveNextIncisionPoint();

	/**
	 * 当前滚动点数据集
	 * 
	 * @return
	 * @throws ScrollException
	 */
	public Collection<E> obtainIncision() throws ScrollException;

	/**
	 * 滚动源
	 * 
	 * @return
	 */
	public Collection<E> getSource();

	/**
	 * 当前序号
	 * 
	 * @param incisionIndex
	 * @param order
	 *            default:ASC
	 * @return
	 */
	public int getOrder(int incisionIndex, Order... order);
}
