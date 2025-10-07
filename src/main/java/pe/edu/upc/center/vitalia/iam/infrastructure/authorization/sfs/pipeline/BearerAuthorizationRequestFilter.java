package pe.edu.upc.center.vitalia.iam.infrastructure.authorization.sfs.pipeline;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.edu.upc.center.vitalia.iam.infrastructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import pe.edu.upc.center.vitalia.iam.infrastructure.tokens.jwt.BearerTokenService;

import java.io.IOException;

/**
 * Bearer Authorization Request Filter.
 * <p>
 * This class is responsible for filtering requests and setting the user authentication.
 * It extends the OncePerRequestFilter class.
 * </p>
 * @see OncePerRequestFilter
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

  private static final Logger LOGGER
      = LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);
  private final BearerTokenService tokenService;

  @Qualifier("defaultUserDetailsService")
  private final UserDetailsService userDetailsService;

  public BearerAuthorizationRequestFilter(BearerTokenService tokenService,
      UserDetailsService userDetailsService) {
    this.tokenService = tokenService;
    this.userDetailsService = userDetailsService;
  }

  /**
   * This method is responsible for filtering requests and setting the user authentication.
   * @param request The request object.
   * @param response The response object.
   * @param filterChain The filter chain object.
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
          throws ServletException, IOException {

    LOGGER.info("Request Path: {}", request.getRequestURI());

    String path = request.getRequestURI();
    LOGGER.info("Request URI: {}", path);

    // Rutas públicas que deben omitirse del filtro
    if (path.matches("^/api/v1/authentication(/.*)?$")
            || path.startsWith("/swagger")
            || path.startsWith("/v3/api-docs")) {
      LOGGER.info("Public endpoint, skipping filter.");
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = tokenService.getBearerTokenFrom(request);
      LOGGER.info("Extracted token: {}", token);

      if (token != null && tokenService.validateToken(token)) {
        String username = tokenService.getUsernameFromToken(token);
        var userDetails = userDetailsService.loadUserByUsername(username);
        SecurityContextHolder.getContext()
                .setAuthentication(UsernamePasswordAuthenticationTokenBuilder.build(userDetails, request));
      } else {
        LOGGER.info("Token is null or invalid");
      }

    } catch (Exception e) {
      LOGGER.error("Cannot set user authentication: {}", e.getMessage());
    }

    LOGGER.info("FILTER PASSED");
    filterChain.doFilter(request, response);
  }



}
