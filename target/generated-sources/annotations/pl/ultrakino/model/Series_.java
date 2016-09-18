package pl.ultrakino.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Series.class)
public abstract class Series_ extends pl.ultrakino.model.Content_ {

	public static volatile SingularAttribute<Series, String> coverFilename;
	public static volatile SingularAttribute<Series, Integer> numberOfSeasons;
	public static volatile SingularAttribute<Series, Integer> timesRated;
	public static volatile ListAttribute<Series, Rating> ratings;
	public static volatile SingularAttribute<Series, String> originalTitle;
	public static volatile SingularAttribute<Series, Float> rating;
	public static volatile SingularAttribute<Series, String> description;
	public static volatile ListAttribute<Series, Integer> categories;
	public static volatile SingularAttribute<Series, String> title;
	public static volatile ListAttribute<Series, Episode> episodes;
	public static volatile ListAttribute<Series, FilmographyEntry> castAndCrew;

}

