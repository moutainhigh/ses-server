package com.redescooter.ses.api.common.vo.base;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Data
@ToString(callSuper = true)
public class PageResult<T> extends GeneralResult{

	private static final long serialVersionUID = 1L;

	private int rowTotal;

	private int pageNo;

	private boolean hasNextPage;

	private int pageSize;

	private List<T> list;

	public int getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	public PageResult() {
	}

	@SuppressWarnings("rawtypes")
	public static List defaultList = new ArrayList<>(0);

	public static <L> PageResult<L> create(PageEnter enter, int rowTotal, List<L> list) {
		PageResult<L> r = new PageResult<L>();
		r.setPageNo(enter.getPageNo());
		r.setPageSize(enter.getPageSize());
		r.setRowTotal(rowTotal);
		r.setHasNextPage((r.pageNo * r.pageSize) < rowTotal);
		r.setList(list);
		return r;
	};

	@SuppressWarnings("unchecked")
	public static <Z> PageResult<Z> createZeroRowResult(PageEnter enter) {
		PageResult<Z> r = new PageResult<>();
		r.setPageNo(enter.getPageNo());
		r.setPageSize(enter.getPageSize());
		r.setRowTotal(0);
		r.setHasNextPage(false);
		r.setList(defaultList);
		return r;
	}
}
