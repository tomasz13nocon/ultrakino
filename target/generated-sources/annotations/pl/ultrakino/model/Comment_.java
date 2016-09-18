package pl.ultrakino.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ {

	public static volatile SingularAttribute<Comment, Integer> upvotes;
	public static volatile SingularAttribute<Comment, String> contents;
	public static volatile SingularAttribute<Comment, User> addedBy;
	public static volatile SingularAttribute<Comment, Comment> parentComment;
	public static volatile SingularAttribute<Comment, LocalDateTime> submissionDate;
	public static volatile SingularAttribute<Comment, Long> id;
	public static volatile SingularAttribute<Comment, Integer> downvotes;
	public static volatile SingularAttribute<Comment, Content> content;

}

