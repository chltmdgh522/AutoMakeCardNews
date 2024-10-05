package amcn.amcn.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "AMCN 무료 API ",
                description = "API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "최승호",
                        email = "chltmdgh517@naver.com"
                )
        )
)
@Configuration
public class OpenApiConfig {
}
