package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Comment;

public interface CommentService {

	/**
	 * Persist a comment with Content of contentId, and user of username
	 * @param comment comment object with no Content and User.
	 * @param contentId id of content to which this comment belongs
	 * @param username username of author of the comment
	 * @return Comment which got persisted
	 * @throws NoRecordWithSuchIdException if there is no Content with contentId
	 * @throws NoUserWithSuchUsernameException if there is no User with username
	 */
	Comment save(Comment comment, int contentId, String username) throws NoRecordWithSuchIdException, NoUserWithSuchUsernameException;

}
