package com.cffilter.backend.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "users",
uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")})
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	
	@NotBlank
	@Size(max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
	private String handle;
	
	@NotBlank
	@Size(max = 120)
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
	joinColumns = @JoinColumn(referencedColumnName = "user_id"),
	inverseJoinColumns = @JoinColumn(referencedColumnName = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password,@NotBlank String handle) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.handle = handle;
	}

	public User() {
	}		
}
