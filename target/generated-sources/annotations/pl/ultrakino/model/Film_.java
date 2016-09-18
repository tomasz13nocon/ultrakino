package pl.ultrakino.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Film.class)
public abstract class Film_ extends pl.ultrakino.model.Content_ {

	public static volatile SingularAttribute<Film, String> coverFilename;
	public static volatile SingularAttribute<Film, Integer> timesRated;
	public static volatile SetAttribute<Film, Comment> comments;
	public static volatile SingularAttribute<Film, LocalDate> localPremiere;
	public static volatile SingularAttribute<Film, Integer> year;
	public static volatile SetAttribute<Film, Player> players;
	public static volatile SingularAttribute<Film, LocalDate> worldPremiere;
	public static volatile SingularAttribute<Film, Float> rating;
	public static volatile SingularAttribute<Film, String> description;
	public static volatile SingularAttribute<Film, Integer> runningTime;
	public static volatile SingularAttribute<Film, String> title;
	public static volatile ListAttribute<Film, String> productionCountries;
	public static volatile SetAttribute<Film, FilmographyEntry> castAndCrew;
	public static volatile SingularAttribute<Film, LocalDateTime> recommendationDate;
	public static volatile ListAttribute<Film, Rating> ratings;
	public static volatile SingularAttribute<Film, String> originalTitle;
	public static volatile SingularAttribute<Film, String> filmwebId;
	public static volatile SetAttribute<Film, Integer> categories;
	public static volatile SingularAttribute<Film, LocalDateTime> additionDate;
	public static volatile SingularAttribute<Film, Integer> views;

}

