package com.ecommerce.vo;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

@Builder
public class GenericPageable {

	private int currentPageNo;//第幾頁
	
	private long pageDataSize;//一頁幾筆
	
	private long dataTotalSize;//此條件資料一共幾筆
	
	@Builder.Default
	private int pagesIconSize=10;//一共幾個頁簽
	
	private Set<Integer> pagination;//頁簽
	
	private long endPageNo;//結束共幾頁
	
	
	@JsonIgnore
	private int rowStart;//查詢分頁起始
	
	@JsonIgnore
	private int rowEnd;//查詢分頁結束

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getPageDataSize() {//long轉int
		int pageDataSizeInt = convertLongToInt(pageDataSize);
		return pageDataSizeInt;
	}

	public void setPageDataSize(long pageDataSize) {
		this.pageDataSize = pageDataSize;
	}

	public long getDataTotalSize() {
		
		return dataTotalSize;
	}
	public Set<Integer> getPagination() {
		
		  Set<Integer> pageSet = new HashSet<>();
	        int pageStart = Math.max(1, currentPageNo - this.pagesIconSize / 2);
	        int pageEnd = (int) Math.min(endPageNo, pageStart + this.pagesIconSize - 1);
	        while (pageStart > 1 && pageEnd - pageStart < this.pagesIconSize - 1) {
	            pageStart--;
	        }
	        while (pageEnd < endPageNo && pageEnd - pageStart < pagesIconSize - 1) {
	            pageEnd++;
	        }
	        for (int i = pageStart; i <= pageEnd; i++) {
	            pageSet.add(i);
	        }
	        
		    this.pagination = pageSet;
		    return pagination;
	}

	public void setPagination(Set<Integer> pagination) {
		this.pagination = pagination;
	}


	public void setDataTotalSize(long dataTotalSize) {
		int endPageNo = (int) Math.ceil((double) dataTotalSize / pageDataSize);
		
		this.dataTotalSize = dataTotalSize;
		this.endPageNo =endPageNo;
	}


	public void setPagesIconSize(int pagesIconSize) {
		this.pagesIconSize = pagesIconSize;
		
	}



	public long getEndPageNo() {
		endPageNo= (int) Math.ceil(dataTotalSize * 1.0 / pageDataSize); 
		return endPageNo;
	}

	public void setEndPageNo(long endPageNo) {
		this.endPageNo = endPageNo;
	}

	public int getRowStart() {
		 rowStart=(int) ((this.currentPageNo - 1) * this.pageDataSize);
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getRowEnd() {
		rowEnd=(int) (rowStart + pageDataSize - 1); // 計算查詢分頁結束
		return rowEnd;
	}

	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}

	
	public static int convertLongToInt(long value) {
	    return (int) Math.min(value, Integer.MAX_VALUE);
	}

	@Override
	public String toString() {
		return "GenericPageable [currentPageNo=" + currentPageNo + ", pageDataSize=" + pageDataSize + ", dataTotalSize="
				+ dataTotalSize + ", pagesIconSize=" + pagesIconSize + ", pagination=" + pagination + ", endPageNo="
				+ endPageNo + ", rowStart=" + rowStart + ", rowEnd=" + rowEnd + "]";
	}

	


}
