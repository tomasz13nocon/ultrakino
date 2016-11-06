package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "film_categories")
public class FilmCategory {

	public FilmCategory() {}

	public FilmCategory(String name) {
		this.name = name;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_category_id")
	private int id;

	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FilmCategory)) return false;
		FilmCategory filmCategory = (FilmCategory) o;
		return Objects.equals(getName(), filmCategory.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
