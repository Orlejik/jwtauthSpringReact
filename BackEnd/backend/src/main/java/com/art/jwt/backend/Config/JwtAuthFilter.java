
package com.art.jwt.backend.Config;

import com.art.jwt.backend.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest httpServletRequest,
            @NonNull HttpServletResponse httpServletResponse,
            @NonNull  FilterChain filterChain) throws ServletException, IOException {

//        Receive the token from Header
        var authHeader = httpServletRequest.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var username = userAuthProvider.extractUserName(jwt);

        if (StringUtils.isNoneEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(username);

            if (userAuthProvider.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }else{
                System.out.println("================== Token is not valid!!! ===========================");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}