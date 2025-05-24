package FortuneMonBackEnd.fortuneMon.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI LogITAPI() {
        Info info = new Info()
                .title("FortuneMon BE API")
                .description("FortuneMon 백엔드 API 명세서")
                .version("1.0.0");

        String jwtSchemeName = "JWT access token";
        String refreshKey = "JWT refresh token";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(jwtSchemeName)
                .addList(refreshKey);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
                .addSecuritySchemes(refreshKey, new SecurityScheme()
                        .name("RefreshToken")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                );

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(info);
    }
}
