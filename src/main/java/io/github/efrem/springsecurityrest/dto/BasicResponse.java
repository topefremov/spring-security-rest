package io.github.efrem.springsecurityrest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BasicResponse {
	private final int status;
	private final String reason;
	private final String message;
}
