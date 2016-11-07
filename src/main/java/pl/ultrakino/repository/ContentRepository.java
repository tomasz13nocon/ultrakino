package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Rateable;

public interface ContentRepository {

	Content findById(int contentId) throws NoRecordWithSuchIdException;

	<T> void merge(T t);
}
