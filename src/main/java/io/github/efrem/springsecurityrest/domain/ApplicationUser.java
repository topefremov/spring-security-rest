package io.github.efrem.springsecurityrest.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import static javax.persistence.FetchType.*;

@Data
@Entity
public class ApplicationUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_user_seq")
	@SequenceGenerator(name = "application_user_seq", sequenceName = "application_user_seq", allocationSize = 1)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String login;
	private String password;
	private String role;
	private String type;

	@ManyToOne(fetch = LAZY)
    @JoinColumn(name = "organization_id")
    @JsonBackReference
	private Organization organization;
	
}
