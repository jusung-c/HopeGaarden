package io.jus.hopegaarden.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("${hopeCoreUrls}") String[] serverList) {
        Info info = new Info()
                .title("Hope Gaarden 사용자 인증 API Document")
                .description("Hope Gaarden 인증서버 API 문서입니다.")
                .contact(new Contact()
                        .name("🔗 Hope Gaarden Backend GitHub Link")
                        .url("https://github.com/jusung-c/HopeGaarden"));

        List<Server> servers = Arrays.stream(serverList)
                .map((url) -> new Server().url(url))
                .toList();

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("Bearer");

        SecurityRequirement schemaRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(schemaRequirement))
                .info(info)
                .servers(servers);
    }

}
