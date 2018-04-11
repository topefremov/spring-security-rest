package io.github.efrem.springsecurityrest.service;

import io.github.efrem.springsecurityrest.domain.House;
import io.github.efrem.springsecurityrest.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private HouseRepository houseRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public List<House> getHousesForOrganization(Long organizationId) {
        return houseRepository.findByUk_Organization_Id(organizationId);
    }

    @Override
    public House getHouse(Long houseId) {
        return houseRepository.findOne(houseId);
    }
}
