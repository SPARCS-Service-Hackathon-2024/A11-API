package com.partybbangbbang.couple.application.dto.response;

import com.partybbangbbang.couple.domain.constants.Emotion;
import com.partybbangbbang.member.domain.constants.Sex;

public record CoupleMainResponse(
        Emotion mailEmotion,
        Emotion femailEmotion,
        Sex primaryGuardian,
        boolean notificationStatus
) {
}
