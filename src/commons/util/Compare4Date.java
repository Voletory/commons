package commons.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间排序
 * 
 * @author bailey_fu
 * @data 2012-2-2
 * @version 1.0
 * @Description 默认顺序：从小到大<br/>
 *              默认条件：毫秒
 */
public class Compare4Date implements Comparable<Compare4Date> {

	/** 顺序：从小到大 */
	public static final boolean COMPARE_ORDER_SEQUENCE_TRUE = true;
	/** 顺序：从大到小 */
	public static final boolean COMPARE_ORDER_SEQUENCE_FALSE = false;

	/** 比较参数 : 毫秒 (默认) */
	public static final int COMPARE_CONDITION_DEFAULT = 0;
	/** 比较参数 : 秒 */
	public static final int COMPARE_CONDITION_SECOND = 1;

	private boolean compareOrderSequence;
	private int compareCondition;
	private Date compareDate;

	public Compare4Date() {
		compareOrderSequence = COMPARE_ORDER_SEQUENCE_TRUE;
		compareCondition = COMPARE_CONDITION_DEFAULT;
	}

	public Compare4Date(Date compareDate) {
		this.compareDate = compareDate;
		compareOrderSequence = COMPARE_ORDER_SEQUENCE_TRUE;
		compareCondition = COMPARE_CONDITION_DEFAULT;
	}

	public Date getCompareDate() {
		return compareDate;
	}

	public void setCompareDate(Date compareDate) {
		this.compareDate = compareDate;
	}

	public void setCompareOrderSequence(boolean compareOrderSequence) {
		this.compareOrderSequence = compareOrderSequence;
	}

	public void setCompareCondition(int compareCondition) {
		this.compareCondition = compareCondition;
	}

	public int compareTo(Compare4Date o) {
		if (this.compareDate != null && o != null && o.compareDate != null) {
			switch (compareCondition) {
			case COMPARE_CONDITION_SECOND: {
				Calendar cal = Calendar.getInstance();
				cal.setTime(this.compareDate);
				int tSecond = cal.get(Calendar.SECOND);
				cal.setTime(o.compareDate);
				int oSecond = cal.get(Calendar.SECOND);

				if (compareOrderSequence) {
					return oSecond < tSecond ? 1 : oSecond > tSecond ? -1 : 0;
				} else {
					return oSecond > tSecond ? 1 : oSecond < tSecond ? -1 : 0;
				}
			}
			default: {
				if (compareOrderSequence) {
					return o.compareDate.getTime() < this.compareDate.getTime() ? 1 : o.compareDate.getTime() > this.compareDate.getTime() ? -1 : 0;
				} else {
					return o.compareDate.getTime() > this.compareDate.getTime() ? 1 : o.compareDate.getTime() < this.compareDate.getTime() ? -1 : 0;
				}
			}
			}
		}
		return 0;
	}

}
