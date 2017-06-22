/**
 * X
 * Copyright (c) 2014-2014 X company,Inc.All Rights Reserved.
 */
package commons.scroll;

/**
 * @author bailey_fu
 * @data 2014-1-23
 * @version $Id: Countable.java, v 0.1 2014-1-23 下午05:21:44 bailey_fu Exp $
 * @Description
 */
public interface Countable {
	/**
	 * 当前顺序
	 * 
	 * @return
	 */
	int getOrder();

	/**
	 * 计数重置
	 */
	void resetOrder();
}
