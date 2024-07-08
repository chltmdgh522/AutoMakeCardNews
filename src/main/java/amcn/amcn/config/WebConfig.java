package amcn.amcn.config;

import amcn.amcn.member.web.session.LogCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/join", "/login", "/css/**", "/*.ico", "/error",
                        "/logout", "/forgot-id", "/forgot-password", "/new-password", "/admin/login", "/admin/logout",
                        "/my-page/imagesV3/{boardId}", "/my-page/imagesV2/{memberId}",
                        "/my-page/images/{boardId}", "/my-page/imageV4/{image}",
                        "/*.jpg", "/*.png", "/*.gif", "/logo/AMCNicon.png",
                        "/email-verification","/join-email","/aiimg/**","/ai/image/{filename}");

    }
}