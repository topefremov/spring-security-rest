package io.github.efrem.springsecurityrest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.efrem.springsecurityrest.dto.BasicResponse;

@RestController
@RequestMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

	@Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
	@GetMapping
	ResponseEntity<BasicResponse> get() {
		return ResponseEntity.ok(
				new BasicResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "This is secure endpoint"));

	}

	@Secured("ROLE_SUPERADMIN")
	@PostMapping
	ResponseEntity<BasicResponse> post() {
		return ResponseEntity.ok(
				new BasicResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "This is secure endpoint"));

	}
	
	@GetMapping("all")
	ResponseEntity<BasicResponse> forAllAuthenticated() {
		return ResponseEntity.ok(
				new BasicResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "This is secure endpoint"));

	}
}
