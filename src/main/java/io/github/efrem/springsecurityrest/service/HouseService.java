package io.github.efrem.springsecurityrest.service;

import io.github.efrem.springsecurityrest.domain.House;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

public interface HouseService {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<House> getHousesForOrganization(Long organizationId);

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    House getHouse(Long houseId);
}
