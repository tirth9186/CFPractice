package com.cffilter.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role(ERole name) {
		super();
		this.name = name;
	}

	public Role() {
		
	}

		
	
}
