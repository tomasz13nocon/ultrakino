package pl.ultrakino.repository;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	private List<T> content = new ArrayList<>();
	private int pageNumber;
	private int pageCount;

	public Page() {}

	public Page(List<T> content, int pageNumber, int pageCount) {
		this.content = content;
		this.pageNumber = pageNumber;
		this.pageCount = pageCount;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
