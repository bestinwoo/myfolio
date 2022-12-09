package inhatc.project.myfolio.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import inhatc.project.myfolio.jwt.JwtAccessDeniedHandler;
import inhatc.project.myfolio.jwt.JwtAuthFilter;
import inhatc.project.myfolio.jwt.JwtAuthenticationEntryPoint;
import inhatc.project.myfolio.jwt.TokenProvider;
import inhatc.project.myfolio.oauth2.CustomOAuth2UserService;
import inhatc.project.myfolio.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomOAuth2UserService oAuth2UserService;
	private final OAuth2SuccessHandler successHandler;
	private final TokenProvider tokenProvider;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Bean
	public WebSecurityCustomizer webSecurity() {
		return (web) -> web.ignoring().antMatchers("/resources/**", "/images/**", "/swagger-ui/**", "/v3/api-docs/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors()
				.configurationSource(request -> {
					CorsConfiguration cors = new CorsConfiguration();
					cors.setAllowedOrigins(List.of("http://localhost:3000"));
					cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					cors.setAllowedHeaders(List.of("*"));
					return cors;
				});

		http.httpBasic()
				.disable() // 기본 httpBasic 인증 disable(formLogin)
				.csrf()
				.disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)

				.and()
				.headers()
				.frameOptions()
				.sameOrigin()

				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/token/**").permitAll()
				.antMatchers("/project/**").permitAll()
				.anyRequest()
				.authenticated();

		http.oauth2Login()
				.successHandler(successHandler)
				.userInfoEndpoint().userService(oAuth2UserService);

		http.addFilterBefore(new JwtAuthFilter(tokenProvider),
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
