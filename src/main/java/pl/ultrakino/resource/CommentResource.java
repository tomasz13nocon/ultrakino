package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class CommentResource {

	private Long uid;
	private String contents;
	private UserResource addedBy;
	private CommentResource parentComment;
	private LocalDateTime submissionDate;
	private int upvotes;
	private int downvotes;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CommentResource)) return false;
		if (!super.equals(o)) return false;
		CommentResource that = (CommentResource) o;
		return Objects.equals(getUid(), that.getUid());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getUid());
	}
}
