package com.github.tj9659.swiftspend.dto;

import java.util.Map;

public record ErrorResponseDTO(
        int status,
        String message,
        long timestamp,
        Map<String, String> validationErrors // Only populated if needed
) {}
