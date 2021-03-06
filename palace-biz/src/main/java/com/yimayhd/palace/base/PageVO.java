package com.yimayhd.palace.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wenfeng zhang @ 14-10-10
 */
public class PageVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3787043722464290607L;

	protected List<T> result;

	private Paginator paginator;

	public PageVO(int pageNumber, int pageSize, int totalCount) {
		this(pageNumber, pageSize, totalCount, new ArrayList<T>(0));
	}
	public PageVO() {//默认空PageVO，用于异常数据返回
		this(1, 10, 0, new ArrayList<T>(0));
	}

	/**
	 *
	 *
	 * @param pageNumber
	 *            当前页码
	 * @param pageSize
	 *            每页显示条数
	 * @param totalCount
	 *            总记录条数
	 * @param result
	 *            结果list
	 */
	public PageVO(int pageNumber, int pageSize, int totalCount, List<T> result) {
		if (pageSize <= 0)
			throw new IllegalArgumentException("[pageSize] must great than zero");
		this.paginator = new Paginator(pageNumber, pageSize, totalCount);
		setItemList(result);
	}

	/**
	 * 是否是首页（第一页），第一页页码为1
	 *
	 * @return 首页标识
	 */
	public boolean isFirstPage() {
		return getThisPageNumber() == 1;
	}

	/**
	 * 是否是最后一页
	 *
	 * @return 末页标识
	 */
	public boolean isLastPage() {
		return getThisPageNumber() >= getLastPageNumber();
	}

	/**
	 * 是否有下一页
	 *
	 * @return 下一页标识
	 */
	public boolean isHasNextPage() {
		return getLastPageNumber() > getThisPageNumber();
	}

	/**
	 * 是否有上一页
	 *
	 * @return 上一页标识
	 */
	public boolean isHasPreviousPage() {
		return getThisPageNumber() > 1;
	}

	/**
	 * 获取最后一页页码，也就是总页数
	 *
	 * @return 最后一页页码
	 */
	public int getLastPageNumber() {
		return Paginator.computeLastPageNumber(getTotalCount(), getPageSize());
	}

	/**
	 * 总的数据条目数量，0表示没有数据
	 *
	 * @return 总数量
	 */
	public int getTotalCount() {
		return paginator.getTotalItems();
	}

	/**
	 * 获取当前页的首条数据的行编码
	 *
	 * @return 当前页的首条数据的行编码
	 */
	public int getThisPageFirstElementNumber() {
		return (getThisPageNumber() - 1) * getPageSize() + 1;
	}

	/**
	 * 获取当前页的末条数据的行编码
	 *
	 * @return 当前页的末条数据的行编码
	 */
	public int getThisPageLastElementNumber() {
		int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
		return getTotalCount() < fullPage ? getTotalCount() : fullPage;
	}

	/**
	 * 获取下一页编码
	 *
	 * @return 下一页编码
	 */
	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}

	/**
	 * 获取上一页编码
	 *
	 * @return 上一页编码
	 */
	public int getPreviousPageNumber() {
		return getThisPageNumber() - 1;
	}

	/**
	 * 每一页显示的条目数
	 *
	 * @return 每一页显示的条目数
	 */
	public int getPageSize() {
		return paginator.getPageSize();
	}

	/**
	 * 当前页的页码
	 *
	 * @return 当前页的页码
	 */
	public int getThisPageNumber() {
		return paginator.getPage();
	}

	/**
	 * 得到用于多页跳转的页码
	 * 
	 * @return
	 */
	public Integer[] getLinkPageNumbers() {
		return linkPageNumbers(7);
	}

	/**
	 * 得到用于多页跳转的页码 注意:不可以使用 getLinkPageNumbers(10)方法名称，因为在JSP中会与
	 * getLinkPageNumbers()方法冲突，报exception
	 * 
	 * @return
	 */
	public Integer[] linkPageNumbers(int count) {
		return Paginator.generateLinkPageNumbers(getThisPageNumber(), getLastPageNumber(), count);
	}

	public List<T> getItemList() {
		return result;
	}

	public void setItemList(List<T> itemList) {
		if (itemList == null)
			throw new IllegalArgumentException("'itemList' must be not null");
		this.result = itemList;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		if (paginator == null)
			throw new IllegalArgumentException("'paginator' must be not null");
		this.paginator = paginator;
	}

	public Iterator<T> iterator() {
		return result == null ? new ArrayList<T>().iterator() : result.iterator();
	}
}
