package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	private String contents;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User addedBy;

	@ManyToOne
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	@Column(name = "submission_date")
	private LocalDateTime submissionDate;

	private int upvotes;

	private int downvotes;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;


	@PrePersist
	public void prePersist() {
		submissionDate = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Comment)) return false;
		Comment comment = (Comment) o;
		return Objects.equals(getAddedBy(), comment.getAddedBy()) &&
				Objects.equals(getSubmissionDate(), comment.getSubmissionDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAddedBy(), getSubmissionDate());
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}
}
