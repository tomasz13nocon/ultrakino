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
import pl.ultrakino.resources.CommentResource;
import pl.ultrakino.service.CommentService;
import pl.ultrakino.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private ContentRepository contentRepository;
	private UserService userService;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ContentRepository contentRepository, UserService userService) {
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.contentRepository = contentRepository;
		this.userService = userService;
	}

	@Override
	public Comment save(String comment, int contentId, String username) throws NoRecordWithSuchIdException, NoUserWithSuchUsernameException {
		if (comment.length() > 255 || comment.trim().length() < 3)
			throw new IllegalArgumentException();
		Content content = contentRepository.findById(contentId);
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) throw new NoUserWithSuchUsernameException();
		Comment newComment = new Comment();
		newComment.setContents(comment);
		newComment.setContent(content);
		newComment.setAddedBy(user.get());
		return commentRepository.save(newComment);
	}

	@Override
	public List<CommentResource> toResources(Collection<Comment> comments) {
		return comments.stream().map(this::toResource).collect(Collectors.toList());
	}

	@Override
	public CommentResource toResource(Comment comment) {
		CommentResource res = new CommentResource();
		res.setUid(comment.getId());
		res.setContents(comment.getContents());
		res.setAddedBy(userService.toResource(comment.getAddedBy()));
		if (comment.getParentComment() != null)
			res.setParentComment(toResource(comment.getParentComment()));
		res.setSubmissionDate(comment.getSubmissionDate());
		res.setUpvotes(comment.getUpvotes());
		res.setDownvotes(comment.getDownvotes());
		return res;
	}

}
