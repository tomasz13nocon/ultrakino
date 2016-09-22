package pl.ultrakino.resources.assemblers;

import pl.ultrakino.exceptions.UnsupportedContentTypeException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Series;
import pl.ultrakino.resources.ContentResource;
import pl.ultrakino.resources.FilmResource;

/**
 * Delegate resource assembling to appropriate class
 */
public class ContentResourceAsm {

	private FilmResourceAsm filmResourceAsm;

	// TODO: Implement
	public ContentResource toResource(Content content) {
		if (content instanceof Film)
			return filmResourceAsm.toResource((Film) content);
//		else if (content instanceof Episode)
//			return null;
//		else if (content instanceof Series)
//			return null;
		throw new UnsupportedContentTypeException();
	}

}
