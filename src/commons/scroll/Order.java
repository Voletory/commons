/**
 * X
 * Copyright (c) 2014-2014 X company,Inc.All Rights Reserved.
 */
package commons.scroll;

/**
 * @author bailey_fu
 * @data 2014-1-21
 * @version $Id: Order.java, v 0.1 2014-1-21 下午12:35:39 bailey_fu Exp $
 * @Description
 */
public class Order {

	protected static final int ORDER_TYPE_ASC = 0;
	protected static final int ORDER_TYPE_DESC = 1;

	public static final Order ORDER_ASC = new Order(ORDER_TYPE_ASC);
	public static final Order ORDER_DESC = new Order(ORDER_TYPE_DESC);

	/** 0:ASC 1:DESC */
	private int type;

	public Order(int type) {
		this.type = type;
	}

	public boolean isDESC() {
		return type == ORDER_TYPE_DESC;
	}

	public boolean isASC() {
		return type == ORDER_TYPE_ASC;
	}
}
