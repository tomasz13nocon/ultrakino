package pl.ultrakino.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resources.SeriesResource;
import pl.ultrakino.service.SeriesCategoryService;
import pl.ultrakino.service.SeriesService;

import static pl.ultrakino.Constants.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/series", produces = "application/json;charset=utf-8")
public class SeriesController {

	private SeriesService seriesService;
	private SeriesCategoryService seriesCategoryService;

	@Autowired
	public SeriesController(SeriesService seriesService, SeriesCategoryService seriesCategoryService) {
		this.seriesService = seriesService;
		this.seriesCategoryService = seriesCategoryService;
	}

	@GetMapping("/{id}")
	public ResponseEntity getOneSeries(@PathVariable int id) {
		try {
			return ResponseEntity.ok(seriesService.toResource(seriesService.findById(id)));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity getSeries(@RequestParam MultiValueMap<String, String> params) {
		try {
			Page<Series> series = seriesService.find(params);
			Page<SeriesResource> result = new Page<>(
					seriesService.toResources(series.getContent()),
					series.getPageNumber(),
					series.getPageCount());
			return ResponseEntity.ok(result);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", e.getMessage()));
		}
	}

	@GetMapping("/categories")
	public ResponseEntity getCategories() {
		return ResponseEntity.ok(seriesCategoryService.findAll());
	}

}
