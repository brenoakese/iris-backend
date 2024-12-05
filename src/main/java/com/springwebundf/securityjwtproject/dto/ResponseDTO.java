package com.springwebundf.securityjwtproject.dto;

import com.springwebundf.securityjwtproject.infra.security.TokenData;

public record ResponseDTO(TokenData token) {
}
