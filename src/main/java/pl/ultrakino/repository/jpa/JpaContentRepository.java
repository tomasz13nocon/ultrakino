package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.repository.ContentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaContentRepository implements ContentRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Content findById(int contentId) throws NoRecordWithSuchIdException {
		Content content = em.find(Content.class, contentId);
		if (content == null) throw new NoRecordWithSuchIdException();
		return content;
	}
}
