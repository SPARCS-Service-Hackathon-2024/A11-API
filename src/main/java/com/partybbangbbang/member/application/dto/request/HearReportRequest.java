package com.partybbangbbang.member.application.dto.request;

import com.partybbangbbang.couple.domain.constants.Emotion;

public record HearReportRequest(
        String babyContext,
        Emotion emotion
) {
}
