package bodhitree.tree.lib.auth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bodhitree.tree.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthService authService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {

        final String reqAuthToken = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (reqAuthToken != null && reqAuthToken.startsWith("Bearer ")) {
            /* JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token */
            jwtToken = reqAuthToken.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } else {
            // logger.warn("JWT token is empty or does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = authService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /* After setting the Authentication in the context, we specify
                 * that the current user is authenticated. So it passes the
                 * Spring Security Configurations successfully.
                 */
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}

