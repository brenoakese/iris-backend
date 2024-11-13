package com.springwebundf.securityjwtproject.dto;

import com.springwebundf.securityjwtproject.infra.security.TokenData;

import java.time.Instant;

public record ResponseDTO(TokenData token) {
}
