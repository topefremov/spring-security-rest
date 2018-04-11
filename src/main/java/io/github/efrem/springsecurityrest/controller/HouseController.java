package io.github.efrem.springsecurityrest.controller;

import io.github.efrem.springsecurityrest.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/houses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HouseController {

    private HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    ResponseEntity<?> getHousesForOrganization(@RequestParam(name = "organizationId") Long organizationId) {
        return new ResponseEntity<>(houseService.getHousesForOrganization(organizationId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<?> getHouseById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(houseService.getHouse(id), HttpStatus.OK);
    }
}
