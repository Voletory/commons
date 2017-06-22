package commons.scroll;

/**
 * 滚动时异常
 * 
 * @author bailey.fu
 * @date Oct 14, 2009
 * @version 1.1
 * @description
 */
public class ScrollException extends Exception {
	private static final long serialVersionUID = 1L;
	/** [负数/0]滚动点 */
	public static final int NEGATIVE_INCISIONPOINT = -1;
	/** 超过最大滚动点 */
	public static final int INCISIONPOINT_OUTOF_TOTAL = -2;
	/** 无下一个滚动点 ( 已是最后滚动点 ) */
	public static final int LAST_INCISIONPOINT = -3;
	/** 无上一个滚动点 ( 已是第一个滚动点 ) */
	public static final int FIRST_INCISIONPOINT = -4;
	private String cause;
	public ScrollException(String cause) {
		super(cause);
		this.cause = cause;
	}
	public ScrollException(int causeID) {
		String cause = "Scroll Error";
		switch (causeID) {
		case NEGATIVE_INCISIONPOINT:
			cause = "负数滚动点";
			break;
		case INCISIONPOINT_OUTOF_TOTAL:
			cause = "超过最大滚动点";
			break;
		case LAST_INCISIONPOINT:
			cause = "无下一个滚动点 ( 已是最后滚动点 )";
			break;
		case FIRST_INCISIONPOINT:
			cause = "无上一个滚动点 ( 已是第一个滚动点 )";
			break;
		}
		this.cause = cause;
	}
	public String getMessage() {
		return cause;
	}
}
