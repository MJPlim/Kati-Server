package com.kati.core.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EnterMyTabResponse {
	private final int enterCode;
	private final String userName;
	private final String message;
}
