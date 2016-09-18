package pl.ultrakino.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rating.class)
public abstract class Rating_ {

	public static volatile SingularAttribute<Rating, User> ratedBy;
	public static volatile SingularAttribute<Rating, Float> rating;
	public static volatile SingularAttribute<Rating, Long> id;
	public static volatile SingularAttribute<Rating, Content> content;

}

