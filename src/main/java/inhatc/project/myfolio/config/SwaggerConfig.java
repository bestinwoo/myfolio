package inhatc.project.myfolio.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "MyFolio API", version = "1.0"))
public class SwaggerConfig {
	@Bean
	public GroupedOpenApi openApi() {
		return GroupedOpenApi.builder()
				.group("MyFolio API")
				.addOpenApiCustomiser(buildOpenApi()).build();
	}

	public OpenApiCustomiser buildOpenApi() {
		SecurityScheme securityScheme = new SecurityScheme()
				.name("Authorization")
				.type(SecurityScheme.Type.HTTP)
				.in(SecurityScheme.In.HEADER)
				.bearerFormat("JWT")
				.scheme("bearer");

		return OpenApi -> OpenApi
				.addSecurityItem(new SecurityRequirement().addList("jwt token"))
				.getComponents().addSecuritySchemes("jwt token", securityScheme);
	}
}
