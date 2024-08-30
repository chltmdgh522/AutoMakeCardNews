package amcn.amcn.config;

import amcn.amcn.oauth.exception.AccountAlreadyExistsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (AccountAlreadyExistsException ex) {
            // 예외가 발생한 경우, 로그인 페이지로 리다이렉트
            response.sendRedirect("/join?error=" + URLEncoder.encode(ex.getMessage(), "UTF-8"));
        }
    }

}