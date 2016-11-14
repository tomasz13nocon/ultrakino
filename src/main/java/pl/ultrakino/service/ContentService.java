package pl.ultrakino.service;

import pl.ultrakino.model.Content;
import pl.ultrakino.resources.ContentResource;

import java.util.Collection;
import java.util.List;

public interface ContentService {
	ContentResource toResource(Content content);

	List<ContentResource> toResources(Collection<Content> contents);
}
