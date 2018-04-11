package io.github.efrem.springsecurityrest.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.*;

@Data
@Entity
public class Uk {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uk_seq")
    @SequenceGenerator(name = "uk_seq", sequenceName = "uk_seq", allocationSize = 1)
    private Long id;
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "organization_id")
    @JsonBackReference
    private Organization organization;

    @OneToMany(mappedBy = "uk")
    @JsonManagedReference
    private Set<House> houses = new HashSet<>();

}
