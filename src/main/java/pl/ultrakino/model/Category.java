package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

	public Category() {}

	public Category(String name) {
		this.name = name;
	}

	@Id
	@SequenceGenerator(name = "category_id_gen", sequenceName = "categories_category_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_gen")
	@Column(name = "category_id")
	private int id;

	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Category)) return false;
		Category category = (Category) o;
		return Objects.equals(getName(), category.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
