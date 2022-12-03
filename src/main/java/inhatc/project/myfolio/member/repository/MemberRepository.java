package inhatc.project.myfolio.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.project.myfolio.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
}
