package pl.ultrakino.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile ListAttribute<User, Content> watchedContent;
	public static volatile SingularAttribute<User, String> passwd;
	public static volatile ListAttribute<User, Rating> ratings;
	public static volatile SetAttribute<User, String> roles;
	public static volatile ListAttribute<User, Player> addedPlayers;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> avatarFilename;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

}

