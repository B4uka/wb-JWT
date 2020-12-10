package pl.wb.security.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import pl.wb.security.resource.Authz;
import pl.wb.security.utils.SecurityUtil;
import pl.wb.security.utils.UserInfo;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;

@Authz
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Inject
    private SecurityUtil securityUtil;

    @Inject
    private UserInfo userInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String email = null;

        String authString = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authString == null || authString.isEmpty() || !authString.startsWith("Bearer")) {

            throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        String token = authString.substring(SecurityUtil.BEARER.length()).trim();
        try {
            Key key = securityUtil.getSecurityKey();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            email = claimsJws.getBody().getSubject();
            userInfo.setEmail(email);
        } catch (Exception e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
