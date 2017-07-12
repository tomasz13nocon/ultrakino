package pl.ultrakino.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.model.Player;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ContentQuery {

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
	private List<Integer> countries;
	private Set<Player.LanguageVersion> versions;
	private OrderBy orderBy;
	private boolean asc;
	private Integer resultLimit;
	private Integer pageNumber;

	public ContentQuery() {}

	public ContentQuery(MultiValueMap<String, String> params) {
		List<String> titleParam = params.get("title");
		if (titleParam != null)
			setTitle(titleParam.get(0));


		List<String> yearFromParam = params.get("yearFrom");
		if (yearFromParam != null) {
			try {
				setYearFrom(Integer.parseInt(yearFromParam.get(0)));
			}
			catch(NumberFormatException e){
				throw new IllegalArgumentException("value of yearFrom parameter is not a valid integer");
			}
		}


		List<String> yearToParam = params.get("yearTo");
		if (yearToParam != null) {
			try {
				setYearTo(Integer.parseInt(yearToParam.get(0)));
			}
			catch(NumberFormatException e){
				throw new IllegalArgumentException("value of yearTo parameter is not a valid integer");
			}
		}


		List<String> categoriesParam = params.get("filmCategories");
		if (categoriesParam != null) {
			try {
				List<Integer> categories = categoriesParam.stream().map(Integer::parseInt).collect(Collectors.toList());
				setCategories(categories);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of filmCategories parameter contains an invalid integer");
			}
		}


		List<String> countriesParam = params.get("countries");
		if (countriesParam != null) {
			try {
				List<Integer> countries = countriesParam.stream().map(Integer::parseInt).collect(Collectors.toList());
				setCountries(countries);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of countries parameter contains an invalid integer");
			}
		}


		List<String> versionsParam = params.get("versions");
		if (versionsParam != null) {
			try {
				Set<Player.LanguageVersion> versions = versionsParam.stream().map(Player.LanguageVersion::valueOf).collect(Collectors.toSet());
				setVersions(versions);
			}
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("value of versions parameter is not a valid LanguageVersion constant");
			}
		}


		List<String> orderByParam = params.get("orderBy");
		if (orderByParam != null) {
			try {
				ContentQuery.OrderBy orderBy = ContentQuery.OrderBy.valueOf(orderByParam.get(0));
				setOrderBy(orderBy);
			}
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("value of orderBy parameter is not a valid OrderBy constant");
			}
		}


		List<String> ascParam = params.get("asc");
		if (ascParam != null && ascParam.get(0).equals("true")) {
			setAsc(true);
		}


		List<String> resultLimitParam = params.get("resultLimit");
		if (resultLimitParam != null) {
			try {
				Integer resultLimit = Integer.parseInt(resultLimitParam.get(0));
				setResultLimit(resultLimit);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of resultLimit parameter is not a valid integer");
			}
		}


		List<String> pageNumberParam = params.get("pageNumber");
		if (pageNumberParam != null) {
			try {
				Integer pageNumber = Integer.parseInt(pageNumberParam.get(0));
				setPageNumber(pageNumber);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of pageNumber parameter is not a valid integer");
			}
		}
	}

}
