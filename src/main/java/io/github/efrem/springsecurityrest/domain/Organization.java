package io.github.efrem.springsecurityrest.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_seq")
    @SequenceGenerator(name = "organization_seq", sequenceName = "organization_seq", allocationSize = 1)
    private Long id;
    private String Name;

    @OneToMany(mappedBy = "organization")
    @JsonManagedReference
    private Set<ApplicationUser> applicationUsers = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @JsonManagedReference
    private Set<Uk> uks = new HashSet<>();
}
