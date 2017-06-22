package commons.util;

/**
 * 选择排序
 * 
 * @author bailey_fu
 * @data 2014-2-14
 * @version $Id: SelectSorter.java, v 0.1 2014-2-14 上午10:36:06 bailey_fu Exp $
 * @Description 排序速度慢于QuickSorter和java.util.Arrays
 */
@SuppressWarnings("unchecked")
public final class SelectSorter {
	private SelectSorter() {

	}

	/**
	 * 选择排序 (速度远慢于快速排序,不推荐使用)
	 * 
	 * @param source
	 */
	public static void sort(Comparable<?>[] source) {
		selectsort(source, 0, source.length - 1);
	}

	static void selectsort(@SuppressWarnings("rawtypes") Comparable[] source, int begin, int end) {
		int maxIndex;
		for (int i = end; i > 0; i--) {
			maxIndex = i;
			for (int j = 0; j < i; j++) {
				if (source[j].compareTo(source[maxIndex]) > 0) {
					swap(source, maxIndex, j);
				}
			}
		}
	}

	private static void swap(Object[] source, int swapIndex1, int swapIndex2) {
		// 保持队列中具有相同值的数的原始位置,即,队列中两个相邻的值相同的数不交换移动,持有排序前的顺序
		if (swapIndex1 != swapIndex2) {
			Object temp = source[swapIndex1];
			source[swapIndex1] = source[swapIndex2];
			source[swapIndex2] = temp;
		}
	}
}
