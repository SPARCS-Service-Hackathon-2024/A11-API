package com.partybbangbbang.member.application;

import com.partybbangbbang.couple.domain.Couple;
import com.partybbangbbang.couple.infra.persistence.CoupleRepository;
import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.global.security.annotation.AuthId;
import com.partybbangbbang.member.application.dto.request.HearReportRequest;
import com.partybbangbbang.member.domain.HearReport;
import com.partybbangbbang.member.domain.Member;
import com.partybbangbbang.member.infra.persistence.HearReportRepository;
import com.partybbangbbang.member.infra.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.partybbangbbang.member.exception.MemberError.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class HearReportService {

    private final MemberRepository memberRepository;
    private final CoupleRepository coupleRepository;
    private final HearReportRepository reportRepository;

    public void writeHearReport(@AuthId Long id, HearReportRequest request) {
        Member sender = memberRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        Couple couple = coupleRepository.findByHusbandOrWife(sender, sender)
                .orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

        HearReport hearReport = HearReport.of(
                sender,
                couple.getHusband() == sender ? couple.getWife() : couple.getHusband(),
                couple.getBabyName(),
                request.emotion());

        reportRepository.save(hearReport);
    }
}
