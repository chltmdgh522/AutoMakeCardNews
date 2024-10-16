package amcn.amcn.config;

import amcn.amcn.member.web.session.LogCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // /api/** 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000") // 리액트 앱의 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowCredentials(true); // 쿠키를 포함한 요청 허용
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/join", "/login", "/css/**", "/*.ico", "/error",
                        "/logout", "/forgot-id", "/forgot-password", "/new-password", "/admin/login", "/admin/logout",
                        "/my-page/imagesV3/{boardId}", "/my-page/imagesV2/{memberId}",
                        "/my-page/images/{boardId}", "/my-page/imageV4/{image}",
                        "/*.jpg", "logo/*.png", "/*.gif", "/logo/**","/*.png",
                        "/email-verification","/join-email","/aiimg/**","/ai/image/{filename}",
                        "/js/**","/api/**");

    }
}