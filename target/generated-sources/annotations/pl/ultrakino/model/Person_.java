package pl.ultrakino.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile SingularAttribute<Person, String> filmwebId;
	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, Integer> id;
	public static volatile SetAttribute<Person, FilmographyEntry> filmography;

}

