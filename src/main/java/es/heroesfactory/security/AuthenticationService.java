package es.heroesfactory.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN_QUERY_NAME = "API-KEY";
    private static final String AUTH_TOKEN = "8e366910";

    private static final String SWAGGER_PATH = "swagger-ui";

    private static final String API_DOCS_PATH = "v3/api-docs";

    public static Authentication getAuthentication(HttpServletRequest request) {

        if (request.getRequestURI().contains(SWAGGER_PATH) || request.getRequestURI().contains(API_DOCS_PATH)) {
            return new ApiKeyAuthentication(AUTH_TOKEN, AuthorityUtils.NO_AUTHORITIES);
        }

        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        apiKey = apiKey == null ? request.getParameter(AUTH_TOKEN_QUERY_NAME) : apiKey;

        if ((apiKey == null )|| !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

}
