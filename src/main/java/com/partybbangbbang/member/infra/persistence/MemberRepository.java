package com.partybbangbbang.member.infra.persistence;

import com.partybbangbbang.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);

    boolean existsByMemberInfoEmail(String email);

    boolean existsByInvitationCode(String invitationCode);

    Optional<Member> findByMemberInfoEmail(String email);

    Optional<Member> findByInvitationCode(String invitationCode);
}
