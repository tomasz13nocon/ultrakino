package pl.ultrakino.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.UnsupportedContentTypeException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.ContentResource;
import pl.ultrakino.service.ContentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

	// TODO: Make Content implement some interface (or do it in content..?) to return title, cover etc. Episodes will return details of their series.

	@Override
	public ContentResource toResource(Content content) {
//		if (content instanceof Film)
//			return filmResourceAsm.toResource((Film) content);
//		else if (content instanceof Episode)
//			return null;
//		else if (content instanceof Series)
//			return null;
		throw new UnsupportedContentTypeException();
	}

	@Override
	public List<ContentResource> toResources(Collection<Content> contents) {
		return contents.stream().map(this::toResource).collect(Collectors.toList());
	}

}
