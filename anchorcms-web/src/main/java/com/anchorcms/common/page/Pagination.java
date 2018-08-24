package com.anchorcms.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 列表分页。包含list属性。
 */
@SuppressWarnings("serial")
public class Pagination extends SimplePage implements Serializable,
		Paginable {
	//查询条件1
	public String searchOne;
	//查询条件2
	public String searchTwo;
	//查询条件3
	public String searchThree;
	//查询条件4
	public String searchFour;
	//查询条件5
	public String serchFive;

	public Pagination() {
	}

	public void setSearchOne(String searchOne) {
		this.searchOne = searchOne;
	}

	public String getSearchOne() {

		return searchOne;
	}
	public String getSearchTwo() {
		return searchTwo;
	}

	public void setSearchTwo(String searchTwo) {
		this.searchTwo = searchTwo;
	}

	public String getSearchThree() {
		return searchThree;
	}

	public void setSearchThree(String searchThree) {
		this.searchThree = searchThree;
	}

	public String getSearchFour() {
		return searchFour;
	}

	public void setSearchFour(String searchFour) {
		this.searchFour = searchFour;
	}

	public String getSerchFive() {
		return serchFive;
	}

	public void setSerchFive(String serchFive) {
		this.serchFive = serchFive;
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(int pageNo, int pageSize, int totalCount, List<?> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<?> list;

	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */
	public void setList(List list) {
		this.list = list;
	}
}
