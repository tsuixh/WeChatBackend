package org.tsui.entity;

import java.util.List;

/**
 *	分页查询工具（属性信息）
 * @author TsuiXh
 */
public class PageAttr {
	
	public static final String SORT_ASC = " asc ";		//升序
	public static final String SORT_DESC = " desc ";		//降序
	public static final String DEFAULT_COLUMNS = " * ";	//所有列
	/**
	 * 分页前需要设置的数据（部分可选）
	 */
	
	private String tableName;//表名称（必须）
	private String columns;//查询列	（必须）
	private String condition;//查询条件
	private String sortColumn;//排序列
	private String sortType = SORT_ASC;//排序方式（降序|升序）
	private int currentPage;//当前页	（必须）
	private int pageSize;//页面大小	（必须）
	
	private List data;//获取的数据
	private int totalLine;//总记录数
	private int totalPage;//总页数
	
	
	public PageAttr(){
	}
	
	/**
	 * @param tableName	表明
	 * @param columns			列名
	 * @param condition		条件
	 * @param sortColumn	排序列
	 * @param sortType		排序方式
	 * @param currentPage	当前页
	 * @param pageSize		页面大小
	 */
	public PageAttr(String tableName, String columns, String condition, String sortColumn, String sortType,
			int currentPage, int pageSize) {
		super();
		this.tableName = tableName;
		this.columns = columns;
		this.condition = condition;
		this.sortColumn = sortColumn;
		this.sortType = sortType;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public int getTotalLine() {
		return totalLine;
	}
	public void setTotalLine(int totalLine) {
		this.totalLine = totalLine;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
}
