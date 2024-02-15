package com.partybbangbbang.couple.application;

import com.partybbangbbang.couple.application.dto.response.CoupleMainResponse;
import com.partybbangbbang.couple.domain.Couple;
import com.partybbangbbang.couple.infra.persistence.CoupleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleQueryRepository coupleQueryRepository;

    public CoupleMainResponse getCoupleMainResponse(Long id) {
        Couple couple = coupleQueryRepository.findCoupleByMemberId(id);
        return new CoupleMainResponse(
                couple.getHusband().getEmotion(),
                couple.getWife().getEmotion(),
                couple.findPrimaryStatus(),
                couple.getHusband().getId().equals(id) ?
                        couple.getHusband().isNotificationStatus() :
                        couple.getWife().isNotificationStatus()
        );
    }
}
