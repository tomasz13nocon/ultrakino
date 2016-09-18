package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;

public interface ContentRepository {

	Content findById(int contentId) throws NoRecordWithSuchIdException;

}
