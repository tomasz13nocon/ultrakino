package pl.ultrakino.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "filmography_entries")
public class FilmographyEntry {

	@Id
	@SequenceGenerator(name = "filmography_entry_id_gen", sequenceName = "filmography_entries_filmography_entry_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filmography_entry_id_gen")
	@Column(name = "filmography_entry_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;

	private String name;

	private String attributes;

	private String role;


	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FilmographyEntry)) return false;
		FilmographyEntry that = (FilmographyEntry) o;
		return Objects.equals(getPerson(), that.getPerson()) &&
				Objects.equals(getContent(), that.getContent());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPerson(), getContent());
	}
}
