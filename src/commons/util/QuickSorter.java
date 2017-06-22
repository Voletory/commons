package commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * 快速排序
 * 
 * @author bailey_fu
 * @data 2014-2-13
 * @version $Id: QuickSorter.java, v 0.1 2014-2-13 下午05:13:19 bailey_fu Exp $
 * @Description 数量大于5000时速度优于java.util.Collections.sort
 *              且远优于java.util.Arrays.sort
 */
@SuppressWarnings("unchecked")
public final class QuickSorter {

	private QuickSorter() {
	}

	/**
	 * 传入Collection排序速度快于Array (最优排序)
	 * 
	 * @param source
	 * @param isDesc
	 * @return
	 */
	public static Collection<?> sort(Collection<? extends Comparable<?>> source, boolean... isDesc) {
		Comparable<?>[] sourceArray = new Comparable[source.size()];
		source.toArray(sourceArray);
		quicksort(sourceArray, 0, sourceArray.length - 1, isDesc);
		return Arrays.asList(sourceArray);
	}

	/**
	 * 快速排序 (数组超过10000时速度远快于java.util.Arrays.sort())
	 * 
	 * @param source
	 * @param isDesc
	 *            是否倒序,默认false
	 */
	public static void sort(Comparable<?>[] source, boolean... isDesc) {
		quicksort(source, 0, source.length - 1, isDesc);
	}

	/**
	 * 传入Collection排序速度快于Array (最优排序)
	 * 
	 * @param source
	 * @param comparator
	 * @param isDesc
	 */
	public static Collection<?> sort(Collection<?> source, Comparator<?> comparator, boolean... isDesc) {
		Object[] sourceArray = new Object[source.size()];
		source.toArray(sourceArray);
		quicksort(sourceArray, 0, sourceArray.length - 1, comparator, isDesc);
		return Arrays.asList(sourceArray);
	}

	/**
	 * 数量超过5000时Comparator略慢于Comparable
	 * 
	 * @param source
	 * @param comparator
	 */
	public static void sort(Object[] source, Comparator<?> comparator, boolean... isDesc) {
		quicksort(source, 0, source.length - 1, comparator, isDesc);
	}

	private static void quicksort(Comparable<?>[] source, int begin, int end, boolean... isDesc) {
		if (end > begin) {
			// 产生的[轴]的位置
			int index = partition(source, begin, end, isDesc);
			// 对[轴]左边的队列排序
			quicksort(source, begin, index - 1, isDesc);
			// 对[轴]右边的队列排序
			quicksort(source, index + 1, end, isDesc);
		}
	}

	private static void quicksort(Object[] source, int begin, int end, Comparator<?> comparator, boolean... isDesc) {
		if (end > begin) {
			// 产生的[轴]的位置
			int index = partition(source, begin, end, comparator, isDesc);
			// 对[轴]左边的队列排序
			quicksort(source, begin, index - 1, comparator, isDesc);
			// 对[轴]右边的队列排序
			quicksort(source, index + 1, end, comparator, isDesc);
		}
	}

	@SuppressWarnings("rawtypes")
	private static int partition(Comparable[] source, int begin, int end, boolean... isDesc) {
		/*
		 * 一般会采用开始位置为[轴],这里使用随机产生轴 采用随机产生[轴],是一种优化手段,可降低因队列有序而出现最大时空复杂度
		 * 但出现最坏情况复杂度任为O(n^2),实际上随机化快速排序得到理论最坏情况的可能性仅为1/(2^n)
		 */
		int index = begin + RandomUtils.getPositiveInt(end - begin + 1);
		// [轴]的值
		Comparable pivot = source[index];
		// 将[轴]移动到队列尾
		swap(source, index, end);

		// 倒序
		if (isDesc.length != 0 && isDesc[0]) {
			/*
			 * i:循环队列的指针 index:队列中最后一个小于[轴]的数的位置(不是小于[轴]的数中的最小数)
			 */
			for (int i = index = begin; i < end; i++) {
				if (source[i].compareTo(pivot) > 0) {
					swap(source, index, i);
					// 指向下一个不大于[轴]的数的位置(若下一个数任大于[轴]则会继续交换,直到出现不大于[轴]的数)
					index++;
				}
			}
		} else {
			/*
			 * i:循环队列的指针 index:队列中最后一个大于[轴]的数的位置(不是大于[轴]的数中的最大数)
			 */
			for (int i = index = begin; i < end; i++) {
				if (source[i].compareTo(pivot) < 0) {
					swap(source, index, i);
					// 指向下一个不小于[轴]的数的位置(若下一个数任小于[轴]则会继续交换,直到出现不小于[轴]的数)
					index++;
				}
			}
		}
		// 将[轴]和队列中最后一个大于[轴]的数交换位置,即形成所有[轴]左边的数小于[轴],所有[轴]右边的数大于或等于[轴]
		swap(source, index, end);
		return index;
	}

	@SuppressWarnings("rawtypes")
	private static int partition(Object[] source, int begin, int end, Comparator comparator, boolean... isDesc) {
		/*
		 * 一般会采用开始位置为[轴],这里使用随机产生轴 采用随机产生[轴],是一种优化手段,可降低因队列有序而出现最大时空复杂度
		 * 但出现最坏情况复杂度任为O(n^2),实际上随机化快速排序得到理论最坏情况的可能性仅为1/(2^n)
		 */
		int index = begin + RandomUtils.getPositiveInt(end - begin + 1);
		// [轴]的值
		Object pivot = source[index];
		// 将[轴]移动到队列尾
		swap(source, index, end);
		if (isDesc.length != 0 && isDesc[0]) {
			/*
			 * i:循环队列的指针 index:队列中最后一个小于[轴]的数的位置(不是小于[轴]的数中的最小数)
			 */
			for (int i = index = begin; i < end; i++) {
				if (comparator.compare(source[i], pivot) > 0) {
					swap(source, index, i);
					// 指向下一个不大于[轴]的数的位置(若下一个数任大于[轴]则会继续交换,直到出现不大于[轴]的数)
					index++;
				}
			}
		} else {
			/*
			 * i:循环队列的指针 index:队列中最后一个大于[轴]的数的位置(不是大于[轴]的数中的最大数)
			 */
			for (int i = index = begin; i < end; i++) {
				if (comparator.compare(source[i], pivot) < 0) {
					swap(source, index, i);
					// 指向下一个不小于[轴]的数的位置(若下一个数任小于[轴]则会继续交换,直到出现不小于[轴]的数)
					index++;
				}
			}
		}
		// 将[轴]和队列中最后一个大于[轴]的数交换位置,即形成所有[轴]左边的数小于[轴],所有[轴]右边的数大于或等于[轴]
		swap(source, index, end);
		return index;
	}

	private static void swap(Object[] source, int swapIndex1, int swapIndex2) {
		// 保持队列中具有相同值的数的原始位置,即,队列中两个相邻的值相同的数不交换移动,持有排序前的顺序
		if (swapIndex1 != swapIndex2) {
			Object temp = source[swapIndex1];
			source[swapIndex1] = source[swapIndex2];
			source[swapIndex2] = temp;
		}
	}

	// 带阀的快速排序速度远低于不设阀,故不推荐使用该方法
	@Deprecated
	static void quicksortByThreshold(Comparable<?>[] source, int begin, int end) {
		if (end > begin) {
			// 产生的[轴]的位置
			int index = partition(source, begin, end);
			// 对[轴]左边的队列排序
			if ((index - 1 - begin) < QUICK_THRESHOLD) {
				SelectSorter.selectsort(source, begin, index - 1);
			} else {
				quicksort(source, begin, index - 1);
			}
			// 对[轴]右边的队列排序
			if ((end - index - 1) < QUICK_THRESHOLD) {
				SelectSorter.selectsort(source, end, index + 1);
			} else {
				quicksort(source, index + 1, end);
			}
		}
	}

	/**
	 * 快速排序的[阈]<br/>
	 * 设置[阈]是一种优化手段<br/>
	 * 因为快速排序的实现需要消耗递归栈的空间, 而大多数情况下都会通过使用系统递归栈来完成递归求解,
	 * 在元素数量较大时,对系统栈的频繁存取会影响到排序的效率<br/>
	 * 作用 : 在每次递归求解中,如果元素总数不足这个阈值,则放弃快速排序,调用一个简单的排序过程完成该子序列的排序<br/>
	 * 若小于 [阈] 值则采用选择排序法<br/>
	 */
	private static final int QUICK_THRESHOLD = 10;
}
