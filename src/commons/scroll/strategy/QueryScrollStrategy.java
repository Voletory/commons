package commons.scroll.strategy;

/**
 * 设置结果集滚动起始点策略
 * 
 * @author bailey.fu
 * @date Dec 14, 2009
 * @version 1.1
 * @description
 */
public class QueryScrollStrategy extends BasicScrollStrategy {

	/** 默认设置开始位置 */
	public static final boolean DEFAULT_START_POINT = true;
	/** 默认设置最大结果集 */
	public static final boolean DEFAULT_MAXRESULTS = true;

	/** 是否设置开始位置 */
	private boolean needStartPoint;
	/** 是否设置获取长度 */
	private boolean needMaxResult;

	public QueryScrollStrategy() {
		super();
		needStartPoint = DEFAULT_START_POINT;
		needMaxResult = DEFAULT_MAXRESULTS;
	}

	public QueryScrollStrategy(int revealLength) {
		super(revealLength, DEFAULT_INCISIONPOINT);
		needStartPoint = DEFAULT_START_POINT;
		needMaxResult = DEFAULT_MAXRESULTS;
	}

	public QueryScrollStrategy(int revealLength, int incisionPoint) {
		super(revealLength, incisionPoint);
		needStartPoint = DEFAULT_START_POINT;
		needMaxResult = DEFAULT_MAXRESULTS;
	}

	public QueryScrollStrategy(boolean needStartPoint, boolean needMaxResult) {
		super();
		this.needStartPoint = needStartPoint;
		this.needMaxResult = needMaxResult;
	}

	public QueryScrollStrategy(boolean needStartPoint, boolean needMaxResult, boolean checkRange) {
		super(checkRange);
		this.needStartPoint = needStartPoint;
		this.needMaxResult = needMaxResult;
	}

	public boolean isNeedStartPoint() {
		return needStartPoint;
	}

	public void setNeedStartPoint(boolean needStartPoint) {
		this.needStartPoint = needStartPoint;
	}

	public boolean isNeedMaxResult() {
		return needMaxResult;
	}

	public void setNeedMaxResult(boolean needMaxResult) {
		this.needMaxResult = needMaxResult;
	}

}
