package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country {

	public Country() {}

	public Country(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id")
	private int id;

	private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Country)) return false;
		Country country = (Country) o;
		return Objects.equals(getName(), country.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
