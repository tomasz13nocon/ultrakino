package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "filmography_entries")
@Getter
@Setter
public class FilmographyEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "filmography_entry_id")
	private Integer id;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;

	private String name;

	private String attributes;

	private String role;

	private int number;


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
