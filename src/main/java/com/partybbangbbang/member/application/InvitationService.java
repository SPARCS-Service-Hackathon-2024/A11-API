package com.partybbangbbang.member.application;

import com.partybbangbbang.couple.application.dto.CoupleIdResponse;
import com.partybbangbbang.couple.domain.Couple;
import com.partybbangbbang.couple.infra.persistence.CoupleRepository;
import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.member.application.dto.request.MatchRequest;
import com.partybbangbbang.member.application.dto.response.InvitationCodeResponse;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.infra.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.partybbangbbang.member.exception.MemberError.INVALID_INVITATION_CODE;
import static com.partybbangbbang.member.exception.MemberError.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationService {

    private final MemberRepository memberRepository;
    private final CoupleRepository coupleRepository;

    public InvitationCodeResponse getInvitationCodeResponse(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        return new InvitationCodeResponse(member.getInvitationCode());
    }

    public CoupleIdResponse matchCouple(
            Long id,
            MatchRequest request
    ) {
        String invitationCode = request.invitationCode();
        Member receiver = memberRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        Member sender = memberRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> BusinessException.of(INVALID_INVITATION_CODE));


        Couple couple = Couple.of(
                receiver,
                sender,
                request.babyName(),
                request.receiverPrimaryStatus()
        );
        
        receiver.updateMatchStatus(true);
        sender.updateMatchStatus(true);

        Couple savedCouple = coupleRepository.save(couple);

        return new CoupleIdResponse(savedCouple.getId());
    }
}
