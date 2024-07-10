package amcn.amcn.config;

import amcn.amcn.member.web.session.SessionConst;
import amcn.amcn.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 보호 비활성화
        http.csrf(csrf -> csrf.disable());

        // 폼 로그인 비활성화
        http.formLogin(login -> login.disable());

        // HTTP Basic 인증 비활성화
        http.httpBasic(basic -> basic.disable());

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(authenticationSuccessHandler())  // 인증 성공 시 핸들러 설정
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/logout")  // 로그아웃을 처리할 커스텀 URL 설정
                .logoutSuccessUrl("/")  // 로그아웃 후 리다이렉트할 URL 설정
                .invalidateHttpSession(true)  // 세션 무효화
                .deleteCookies(SessionConst.LOGIN_MEMBER)  // 쿠키 삭제
        );

        // 요청 허용 및 인증 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/oauth2/**", "/**").permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/");
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }
}
