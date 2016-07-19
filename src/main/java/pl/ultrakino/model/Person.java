package pl.ultrakino.model;

import javax.persistence.*;
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

	private PersonRole role;

	@ManyToMany
	@JoinTable(name = "persons_contents_filmography",
			joinColumns = @JoinColumn(name = "person_id"),
			inverseJoinColumns = @JoinColumn(name = "content_id"))
	private List<Content> filmography;

}
