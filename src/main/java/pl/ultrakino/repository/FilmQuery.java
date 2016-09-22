package pl.ultrakino.repository;

import pl.ultrakino.model.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class FilmQuery  {

	public enum OrderBy {
		ADDITION_DATE,
		PREMIERE,
		TITLE,
		RECOMMENDATION_DATE,
		VIEWS,
	}

	private String title;
	private Integer yearFrom;
	private Integer yearTo;
	private List<Integer> categories;
	private Set<Player.LanguageVersion> versions;
	private OrderBy orderBy;
	private boolean asc;
	private Integer resultLimit;
	private Integer pageNumber;


	public FilmQuery title(String title) {
		this.title = title;
		return this;
	}
	
	public FilmQuery yearFrom(Integer yearFrom) {
		this.yearFrom = yearFrom;
		return this;
	}
	
	public FilmQuery yearTo(Integer yearTo) {
		this.yearTo = yearTo;
		return this;
	}

	public FilmQuery categories(List<Integer> categories) {
		this.categories = categories;
		return this;
	}

	public FilmQuery versions(Set<Player.LanguageVersion> versions) {
		this.versions = versions;
		return this;
	}

	public FilmQuery categories(Integer... categories) {
		this.categories = Arrays.asList(categories);
		return this;
	}

	public FilmQuery orderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public FilmQuery asc(boolean asc) {
		this.asc = asc;
		return this;
	}

	public FilmQuery resultLimit(Integer resultLimit) {
		this.resultLimit = resultLimit;
		return this;
	}
	
	public FilmQuery pageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Integer getYearFrom() {
		return yearFrom;
	}

	public Integer getYearTo() {
		return yearTo;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	public Set<Player.LanguageVersion> getVersions() {
		return versions;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public Integer getResultLimit() {
		return resultLimit;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}

}
