package commons.scroll.strategy;

/**
 * 滚动策略基类
 * 
 * @author bailey.fu
 * @date Oct 15, 2009
 * @version 1.3
 * @description
 */
public class BasicScrollStrategy implements ScrollStrategy {
	/** 默认--检查滚动点范围 */
	public static final boolean DEFAULT_CHECK_RANGE = true;
	/** 默认滚动点 */
	public static final int DEFAULT_INCISIONPOINT = 1;
	/** 默认滚动间隔长度 */
	public static final int DEFAULT_REVEALLENGTH = 10;

	protected int revealLength;
	protected int incisionPoint;
	protected int incisionPointTotal;
	/** 检查当前滚动点 */
	protected boolean checkRange;

	public BasicScrollStrategy() {
		revealLength = DEFAULT_REVEALLENGTH;
		incisionPoint = DEFAULT_INCISIONPOINT;
		incisionPointTotal = 0;
		checkRange = DEFAULT_CHECK_RANGE;
		validate();
	}

	public BasicScrollStrategy(boolean checkRange) {
		revealLength = DEFAULT_REVEALLENGTH;
		incisionPoint = DEFAULT_INCISIONPOINT;
		incisionPointTotal = 0;
		this.checkRange = checkRange;
		validate();
	}

	public BasicScrollStrategy(int revealLength, int incisionPoint) {
		this.revealLength = Math.abs(revealLength);
		this.incisionPoint = incisionPoint;
		incisionPointTotal = 0;
		checkRange = DEFAULT_CHECK_RANGE;
		validate();
	}

	public BasicScrollStrategy(int revealLength, int incisionPoint, boolean checkRange) {
		this.revealLength = Math.abs(revealLength);
		this.incisionPoint = incisionPoint;
		incisionPointTotal = 0;
		this.checkRange = checkRange;
		validate();
	}

	public BasicScrollStrategy(int revealLength, int incisionPoint, int incisionPointTotal) {
		this.revealLength = Math.abs(revealLength);
		this.incisionPoint = incisionPoint;
		this.incisionPointTotal = incisionPointTotal;
		checkRange = DEFAULT_CHECK_RANGE;
		validate();
	}

	public BasicScrollStrategy(int revealLength, int incisionPoint, int incisionPointTotal, boolean checkRange) {
		this.revealLength = Math.abs(revealLength);
		this.incisionPoint = incisionPoint;
		this.incisionPointTotal = incisionPointTotal;
		this.checkRange = checkRange;
		validate();
	}

	public boolean isCheckRange() {
		return checkRange;
	}

	public void setIncisionPoint(int incisionPoint) {
		this.incisionPoint = incisionPoint;
	}

	public int getIncisionPoint() {
		return incisionPoint;
	}

	public void setIncisionPointTotal(int incisionPointTotal) {
		this.incisionPointTotal = incisionPointTotal;
		if (checkRange) {
			incisionPoint = incisionPoint > this.incisionPointTotal ? this.incisionPointTotal : this.incisionPoint;
			/** 当前滚动点最小为1 */
			incisionPoint = incisionPoint <= 0 ? 1 : incisionPoint;
		}
	}

	public int getIncisionPointTotal() {
		return incisionPointTotal;
	}

	public void setRevealLength(int revealLength) {
		this.revealLength = revealLength;
	}

	public int getRevealLength() {
		return revealLength;
	}

	public int differenceOfincisionPoint() {
		return incisionPointTotal - incisionPoint;
	}

	/**
	 * 验证并修正参数值
	 */
	public void validate() {
		revealLength = revealLength <= 0 ? 1 : revealLength;
		incisionPoint = incisionPoint <= 0 ? 1 : incisionPoint;
		incisionPointTotal = incisionPointTotal < 0 ? 0 : incisionPointTotal;
	}
}
