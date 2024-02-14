package com.partybbangbbang.member.application;

import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.member.application.dto.response.InvitationCodeResponse;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.infra.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.partybbangbbang.member.exception.MemberError.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationService {

    private final MemberRepository memberRepository;

    public InvitationCodeResponse getInvitationCodeResponse(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        return new InvitationCodeResponse(member.getInvitationCode());
    }
}
