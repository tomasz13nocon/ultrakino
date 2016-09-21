package pl.ultrakino.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.ultrakino.model.Player.LanguageVersion;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Player.class)
public abstract class Player_ {

	public static volatile SingularAttribute<Player, String> src;
	public static volatile SingularAttribute<Player, Boolean> lostSrc;
	public static volatile SingularAttribute<Player, String> hosting;
	public static volatile SingularAttribute<Player, User> addedBy;
	public static volatile SingularAttribute<Player, Boolean> foreignSrc;
	public static volatile SingularAttribute<Player, LanguageVersion> languageVersion;
	public static volatile SingularAttribute<Player, Integer> id;
	public static volatile SingularAttribute<Player, LocalDateTime> additionDate;
	public static volatile SingularAttribute<Player, Content> content;
	public static volatile SingularAttribute<Player, String> quality;

}

