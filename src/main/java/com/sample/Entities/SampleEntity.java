package com.sample.Entities;

import lombok.Data;
import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Data
public class SampleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String firstname;
	private String lastname;
}
