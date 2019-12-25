package com.dk.lanmv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页模型基类
 * @author Wayne.M
 * 2019年2月16日
 */
public class PageModel<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	//当前页
	private Integer pageIndex;

	//页大小
	private Integer pageSize;

	//总记录数
	private Integer totalCount;

	//数据体
	private List<T> pageDatas;

	//总页数
	private Integer totalPage;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getPageDatas() {
		return pageDatas;
	}

	public void setPageDatas(List<T> pageDatas) {
		this.pageDatas = pageDatas;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return "PageModel{" +
				"pageIndex=" + pageIndex +
				", pageSize=" + pageSize +
				", totalCount=" + totalCount +
				", pageDatas=" + pageDatas +
				", totalPage=" + totalPage +
				'}';
	}
}
