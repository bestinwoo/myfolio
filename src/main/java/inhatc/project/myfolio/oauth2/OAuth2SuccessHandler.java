package inhatc.project.myfolio.oauth2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import inhatc.project.myfolio.jwt.TokenDto;
import inhatc.project.myfolio.jwt.TokenProvider;
import inhatc.project.myfolio.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	private final TokenProvider tokenProvider;
	@Value("${app.oauth2.authorized-redirect-uri}")
	private String redirectUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication)
			throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		Member loggedMember = oAuth2User.getMember();

		log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
		log.info("토큰 발행 시작");

		TokenDto.Response token = tokenProvider.generateTokenDto(createUserPayload(loggedMember));
		log.info("{}", token);
		String redirectUri = UriComponentsBuilder.fromUriString(redirectUrl)
				.queryParam("accessToken", token.getAccessToken())
				.queryParam("refreshToken", token.getRefreshToken())
				.toUriString();

		response.sendRedirect(redirectUri);
	}

	public Map<String, Object> createUserPayload(Member member) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("sub", member.getId());
		payload.put("email", member.getEmail());
		payload.put("name", member.getName());

		payload.put("role", "ROLE_USER");
		return payload;
	}
}
