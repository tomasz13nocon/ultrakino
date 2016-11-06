package pl.ultrakino.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "series_categories")
public class SeriesCategory {

	public SeriesCategory() {}

	public SeriesCategory(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "series_category_id")
	private int id;

	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SeriesCategory)) return false;
		SeriesCategory seriesCategory = (SeriesCategory) o;
		return Objects.equals(getName(), seriesCategory.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
