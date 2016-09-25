package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Comment;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.CommentRepository;
import pl.ultrakino.repository.ContentRepository;
import pl.ultrakino.repository.UserRepository;
import pl.ultrakino.service.CommentService;

import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private ContentRepository contentRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ContentRepository contentRepository) {
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.contentRepository = contentRepository;
	}

	@Override
	public Comment save(Comment comment, int contentId, String username) throws NoRecordWithSuchIdException, NoUserWithSuchUsernameException {
		Content content = contentRepository.findById(contentId);
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) throw new NoUserWithSuchUsernameException();
		Comment newComment = new Comment();
		newComment.setContents(comment.getContents());
		newComment.setContent(content);
		newComment.setAddedBy(user.get());
		return commentRepository.save(newComment);
	}

}
