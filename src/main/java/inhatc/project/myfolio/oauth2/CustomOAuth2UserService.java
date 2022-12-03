package inhatc.project.myfolio.oauth2;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import inhatc.project.myfolio.member.domain.Member;
import inhatc.project.myfolio.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	// Spring Security OAuth2 에서 기본으로 제공하는 OAuth2UserService 를 사용하기 위함
	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// OAuth2 계정 정보 가져오기
		OAuth2User oauth2User = delegate.loadUser(userRequest);
		Map<String, Object> attributes = oauth2User.getAttributes();

		String email = (String) attributes.get("email");
		String name = (String) attributes.get("name");
		String oauth2Id = (String) attributes.get("sub");

		Member loggedMember = memberRepository.findByEmail(email).orElseGet(() -> memberRepository.save(Member.createMember(oauth2Id, email, name)));

		return new CustomOAuth2User(attributes, loggedMember);
	}
}
