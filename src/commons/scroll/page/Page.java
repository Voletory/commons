/**
 * X
 * Copyright (c) 2014-2014 X company,Inc.All Rights Reserved.
 */
package commons.scroll.page;

/**
 * @author bailey_fu
 * @data 2014-1-20
 * @version 1.0
 * @Description
 */
public class Page implements Pageable {
	private int startPage;
	private int pageSize;

	public Page() {
		this.pageSize = 1;
		this.startPage = 1;
	}

	public Page(int pageSize, int startPage) {
		this.pageSize = pageSize;
		this.startPage = startPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

}
