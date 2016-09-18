package pl.ultrakino.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Episode.class)
public abstract class Episode_ extends pl.ultrakino.model.Content_ {

	public static volatile SingularAttribute<Episode, Integer> timesRated;
	public static volatile SingularAttribute<Episode, LocalDate> localPremiere;
	public static volatile ListAttribute<Episode, Rating> ratings;
	public static volatile ListAttribute<Episode, Player> players;
	public static volatile SingularAttribute<Episode, Series> series;
	public static volatile SingularAttribute<Episode, LocalDate> worldPremiere;
	public static volatile SingularAttribute<Episode, Float> rating;
	public static volatile SingularAttribute<Episode, String> title;
	public static volatile SingularAttribute<Episode, Integer> views;

}

