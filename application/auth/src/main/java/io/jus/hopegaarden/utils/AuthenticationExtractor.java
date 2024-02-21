package io.jus.hopegaarden.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class AuthenticationExtractor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String HEADER_SPLIT_DELIMITER = " ";
    private static final int TOKEN_TYPE_INDEX = 0;
    private static final int TOKEN_VALUE_INDEX = 1;
    private static final int VALID_HEADER_SPLIT_LENGTH = 3;

    public static Optional<String> extractToken(final HttpServletRequest request) {
        String header = extractHeader(request);

        if (!StringUtils.hasText(header)) {
            return Optional.empty();
        }

        return extractFromHeader(header.split(HEADER_SPLIT_DELIMITER));
    }

    public static String extractHeader(final HttpServletRequest request) {

        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public static Optional<String> extractFromHeader(final String[] headerParts) {
        if (headerParts.length == VALID_HEADER_SPLIT_LENGTH &&
                headerParts[TOKEN_TYPE_INDEX].equals(BEARER)) {
            return Optional.ofNullable(headerParts[TOKEN_VALUE_INDEX]);
        }

        return Optional.empty();
    }
}
