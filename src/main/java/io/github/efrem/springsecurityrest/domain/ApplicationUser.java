package io.github.efrem.springsecurityrest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class ApplicationUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_user_seq")
	@SequenceGenerator(name = "application_user_seq", sequenceName = "application_user_seq", allocationSize = 1)
	private final Long id;
	
	@Column(nullable = false, unique = true)
	private final String login;
	private final String password;
	private final String role;
	private final String type;
	
	public ApplicationUser() {
		this(null, null, null, null, null);
	}
}
