package io.jus.hopegaarden.domain.define.member.repository;

import io.jus.hopegaarden.domain.define.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(final String nickname);

    Optional<Member> findByEmail(final String email);

}
