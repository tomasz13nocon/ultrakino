package pl.ultrakino.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "persons")
public class Person {

	public enum Role {
		DIRECTOR(1),
		SCREENWRITER(2),
		COMPOSER(3),
		CINEMATOGRAPHER(4),
		SCREENPLAY_MATERIALS(5),
		ACTOR(6),
		PRODUCER(9),
		EDITOR(10),
		COSTUME_DESIGNER(11);

		public int apiNumber;

		Role(int apiNumber) {
			this.apiNumber = apiNumber;
		}

		public int getApiNumber() {
			return apiNumber;
		}
	}

	@Id
	@SequenceGenerator(name = "person_id_gen", sequenceName = "persons_person_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_gen")
	@Column(name = "person_id")
	private Integer id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<FilmographyEntry> filmography = new HashSet<>();

	@Column(name = "filmweb_id")
	private String filmwebId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FilmographyEntry> getFilmography() {
		return filmography;
	}

	public void setFilmography(Set<FilmographyEntry> filmography) {
		this.filmography = filmography;
	}

	public String getFilmwebId() {
		return filmwebId;
	}

	public void setFilmwebId(String filmwebId) {
		this.filmwebId = filmwebId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Person)) return false;
		Person person = (Person) o;
		return Objects.equals(getName(), person.getName()) &&
				Objects.equals(getFilmwebId(), person.getFilmwebId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getFilmwebId());
	}
}
