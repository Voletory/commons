package commons.util;

import java.util.ArrayList;
import java.util.Collection;

import commons.collections.SequenceIterator;

/**
 * 序列号生成器(16位)
 * 
 * @author bailey.fu
 * @date Jan 27, 2010
 * @version 1.0
 * @description 系统时间加上一个随机的三位数构成
 */
public final class SeriesGenerator {

	private static SequenceIterator<String> sequenceIterator = null;

	static {
		Collection<String> coll = new ArrayList<String>();
		int limit = 999;

		String limitStr = String.valueOf(limit);
		String temp;
		for (int i = 0; i <= limit; i++) {
			temp = String.valueOf(i);
			while (limitStr.length() > temp.length()) {
				temp = "0" + temp;
			}
			coll.add(temp);
		}
		sequenceIterator = new SequenceIterator<String>(coll, true);
	}

	public synchronized static String createSeries() {
		return System.currentTimeMillis() + sequenceIterator.next();
	}
}
