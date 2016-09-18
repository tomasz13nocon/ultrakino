package pl.ultrakino.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FilmographyEntry.class)
public abstract class FilmographyEntry_ {

	public static volatile SingularAttribute<FilmographyEntry, String> role;
	public static volatile SingularAttribute<FilmographyEntry, Person> person;
	public static volatile SingularAttribute<FilmographyEntry, String> name;
	public static volatile SingularAttribute<FilmographyEntry, String> attributes;
	public static volatile SingularAttribute<FilmographyEntry, Integer> id;
	public static volatile SingularAttribute<FilmographyEntry, Content> content;

}

