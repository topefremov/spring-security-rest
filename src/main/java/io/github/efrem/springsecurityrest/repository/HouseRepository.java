package io.github.efrem.springsecurityrest.repository;

import io.github.efrem.springsecurityrest.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByUk_Organization_Id(Long id);
}
