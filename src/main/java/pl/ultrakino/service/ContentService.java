package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.resource.ContentResource;

import java.util.Collection;
import java.util.List;

public interface ContentService {
	Content findById(int contentId) throws NoRecordWithSuchIdException;

	ContentResource toResource(Content content);

	List<ContentResource> toResources(Collection<Content> contents);
}
