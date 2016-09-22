package pl.ultrakino.resources.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.Comment;
import pl.ultrakino.resources.CommentResource;
import pl.ultrakino.web.FilmController;

@Component
public class CommentResourceAsm extends ResourceAssemblerSupport<Comment, CommentResource> {

	private UserResourceAsm userResourceAsm;

	@Autowired
	public CommentResourceAsm(UserResourceAsm userResourceAsm) {
		super(FilmController.class, CommentResource.class);
		this.userResourceAsm = userResourceAsm;
	}

	@Override
	public CommentResource toResource(Comment comment) {
		CommentResource res = new CommentResource();
		res.setUid(comment.getId());
		res.setContents(comment.getContents());
		res.setAddedBy(userResourceAsm.toResource(comment.getAddedBy()));
		if (comment.getParentComment() != null)
			res.setParentComment(toResource(comment.getParentComment()));
		res.setSubmissionDate(comment.getSubmissionDate());
		res.setUpvotes(comment.getUpvotes());
		res.setDownvotes(comment.getDownvotes());
		return res;
	}
}
