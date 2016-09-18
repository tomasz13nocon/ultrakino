package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Comment;
import pl.ultrakino.model.Content;
import pl.ultrakino.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaCommentRepository implements CommentRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Comment save(Comment comment) {
		em.persist(comment);
		return comment;
	}

}
