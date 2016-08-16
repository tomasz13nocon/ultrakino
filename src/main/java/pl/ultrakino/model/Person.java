package pl.ultrakino.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
public class Person {

	@Id
	@SequenceGenerator(name = "person_id_gen", sequenceName = "persons_person_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_gen")
	@Column(name = "person_id")
	private Integer id;

	private String name;

	@Enumerated(EnumType.STRING)
	private PersonRole role;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "persons_contents_filmography",
			joinColumns = @JoinColumn(name = "person_id"),
			inverseJoinColumns = @JoinColumn(name = "content_id"))
	private List<Content> filmography = new ArrayList<>();


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

	public PersonRole getRole() {
		return role;
	}

	public void setRole(PersonRole role) {
		this.role = role;
	}

	public List<Content> getFilmography() {
		return filmography;
	}

	public void setFilmography(List<Content> filmography) {
		this.filmography = filmography;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		return id.equals(person.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
