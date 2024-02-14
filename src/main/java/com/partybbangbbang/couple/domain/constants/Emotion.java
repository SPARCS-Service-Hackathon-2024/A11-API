package com.partybbangbbang.couple.domain.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Emotion {
    AMUSEMENT("행복"),
    JOY("놀람"),
    LOVE("사랑"),
    NEUTRAL("중립"),
    SADNESS("슬픔"),
    ANGER("분노"),
    CONFUSION("혼란");

    private final String mean;
}
