package pl.ultrakino.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static pl.ultrakino.Constants.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/series", produces = "application/json;charset=utf-8")
public class SeriesController {

}
