package inhatc.project.myfolio.member.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberTest {

	@Test
	void createMember() {
		final String oauth2Id = "testOauth2Id";
		final String email = "testEmail@email.com";
		final String name = "Tomas";
		Member member = Member.createMember(oauth2Id, email, name);

		assertThat(member.getEmail()).isEqualTo(email);
	}
}
