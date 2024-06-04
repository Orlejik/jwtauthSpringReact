package com.art.jwt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ErrorDTO {
    public ErrorDTO(String unauthorizedPath) {
    }
}
