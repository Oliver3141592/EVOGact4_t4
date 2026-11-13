package com.example.crudrapido.security;
 
import java.io.IOException;
import java.util.List;
 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
public class JwtFilter extends OncePerRequestFilter {
 
    private final JwtService jwtService;
 
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
 
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
 
        String header = request.getHeader("Authorization");
 
        if (header != null && header.startsWith("Bearer ")) {
            try {
                String email = jwtService.extractEmail(header.substring(7));
                var auth = new UsernamePasswordAuthenticationToken(email, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // token falso o vencido: no se autentica y Spring responderá 401
            }
        }
 
        filterChain.doFilter(request, response);
    }
}
