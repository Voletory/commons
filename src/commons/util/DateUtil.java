package commons.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 * 
 * @author bailey.fu
 * @date Oct 12, 2009
 * @version 1.2
 * @description 时间相关处理
 */
public final class DateUtil {
	private DateUtil() {
	}

	public static long timeGapForSecond(Date preDate) {
		return timeGapForSecond(preDate, new Date());
	}

	/**
	 * 时间间隔(秒)
	 * 
	 * @param preDate
	 * @param tragetDate
	 * @return
	 */
	public static long timeGapForSecond(Date preDate, Date tragetDate) {
		return (tragetDate.getTime() - preDate.getTime()) / 1000;
	}

	public static String getDay(int day, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return DateFormatUtils.format(calendar, format);

	}

	public static Date getDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Calendar getCalendar(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar;
	}

	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	public static Calendar getCalendar(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar;
	}

	public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar;
	}

	public static String getWeek4Chinese(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getWeek4Chinese(calendar);
	}

	public static String getWeek4Chinese(Calendar cal) {
		int week = cal.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 1: {
			return "周日";
		}
		case 2: {
			return "周一";
		}
		case 3: {
			return "周二";
		}
		case 4: {
			return "周三";
		}
		case 5: {
			return "周四";
		}
		case 6: {
			return "周五";
		}
		case 7: {
			return "周六";
		}
		default: {
			return "未知";
		}
		}
	}

	/** 获取时间 */
	public static String getDetailTime(long millisecond) {
		if (millisecond <= 0) {
			return "0秒";
		}
		StringBuilder sb = new StringBuilder();
		int seconds = Long.valueOf(millisecond / 1000).intValue();
		if (seconds <= 60) {
			sb.append(seconds).append("秒");
		} else {
			int minute = seconds / 60;
			seconds = seconds % 60;
			if (minute < 60) {
				sb.append(minute).append("分").append(seconds).append("秒");
			} else {
				int hour = minute / 60;
				minute = minute % 60;
				if (hour < 24) {
					sb.append(hour).append("时").append(minute).append("分").append(seconds).append("秒");
				} else {
					int day = hour / 24;
					hour = hour % 24;
					if (day <= 30) {
						sb.append(day).append("天").append(hour).append("时").append(minute).append("分").append(seconds).append("秒");
					} else {
						int month = day / 30;
						day = day % 30;
						if (month < 12) {
							sb.append(month).append("月");
							if (day > 0) {
								sb.append(day).append("天");
							}
							sb.append(hour).append("时").append(minute).append("分").append(seconds).append("秒");
						} else {
							int year = month / 12;
							month = month % 12;
							sb.append(year).append("年");
							if (month > 0) {
								sb.append(month).append("月");
							}
							if (day > 0) {
								sb.append(day).append("天");
							}
							sb.append(hour).append("时").append(minute).append("分").append(seconds).append("秒");
						}
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 根据时间差返回对应的格式
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeByDate(Date time) {
		if (time == null)
			return null;
		Calendar now = Calendar.getInstance();
		Calendar prv = Calendar.getInstance();
		prv.setTime(time);
		int prv_year = prv.get(Calendar.YEAR);
		int now_year = now.get(Calendar.YEAR);
		if (prv_year < now_year)
			return DateFormatUtils.format(time, "yyyy-MM-dd");
		int prv_month = prv.get(Calendar.MONTH);
		int now_month = now.get(Calendar.MONTH);
		if (prv_month < now_month)
			return DateFormatUtils.format(time, "MM-dd HH:mm");
		int prv_day = prv.get(Calendar.DAY_OF_MONTH);
		int now_day = now.get(Calendar.DAY_OF_MONTH);
		if (prv_day != now_day)
			return DateFormatUtils.format(time, "MM-dd HH:mm");
		return DateFormatUtils.format(time, "HH:mm:ss");
	}
}
