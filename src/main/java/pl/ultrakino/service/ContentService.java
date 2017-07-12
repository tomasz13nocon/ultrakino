package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.resource.ContentResource;

import java.util.Collection;
import java.util.List;

public interface ContentService {

	ContentResource toResource(Content content);

	List<ContentResource> toResources(Collection<Content> contents);

	Content.Type getType(int id) throws NoRecordWithSuchIdException;
}
