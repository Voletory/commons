package commons.scroll.strategy;

import commons.strategy.Strategy;

/**
 * 滚动策略
 * 
 * @author bailey.fu
 * @date Oct 14, 2009
 * @version 1.1
 * @description
 */
public interface ScrollStrategy	extends
								Strategy {

	public void setIncisionPoint(int incisionPoint);

	public int getIncisionPoint();

	public void setRevealLength(int revealLength);

	public int getRevealLength();

	public void setIncisionPointTotal(int incisionPointTotal);

	public int getIncisionPointTotal();

	/**
	 * 当前滚动点与最大滚动点的间隔
	 * 
	 * @return
	 */
	public int differenceOfincisionPoint();

	/**
	 * 是否检查滚动点越界(当前滚动点超过最大滚动点)
	 * 
	 * @return
	 */
	public boolean isCheckRange();

}
