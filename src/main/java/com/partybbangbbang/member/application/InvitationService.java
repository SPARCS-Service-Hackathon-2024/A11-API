package com.partybbangbbang.member.application;

import com.partybbangbbang.couple.application.dto.CoupleIdResponse;
import com.partybbangbbang.couple.domain.Couple;
import com.partybbangbbang.couple.infra.persistence.CoupleRepository;
import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.member.application.dto.request.InvitationCodeRequest;
import com.partybbangbbang.member.application.dto.request.MatchRequest;
import com.partybbangbbang.member.application.dto.response.ValidInvitationResponse;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.infra.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.partybbangbbang.member.exception.MemberError.INVALID_INVITATION_CODE;
import static com.partybbangbbang.member.exception.MemberError.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InvitationService {

    private final MemberRepository memberRepository;
    private final CoupleRepository coupleRepository;

    public ValidInvitationResponse isValidInvitation(InvitationCodeRequest request) {
        boolean result = memberRepository.existsByInvitationCode(request.invitationCode());
        return new ValidInvitationResponse(result);
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
        receiver.destroyInvitationCode();
        sender.destroyInvitationCode();

        Couple savedCouple = coupleRepository.save(couple);

        return new CoupleIdResponse(savedCouple.getId());
    }
}
