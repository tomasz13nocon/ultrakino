package pl.ultrakino.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "series_categories")
public class SeriesCategory {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "series_category_id")
	private int id;
}
