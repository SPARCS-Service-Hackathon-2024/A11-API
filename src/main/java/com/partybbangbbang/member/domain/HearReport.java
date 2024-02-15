package com.partybbangbbang.member.domain;

import com.partybbangbbang.couple.domain.constants.Emotion;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_hear_report")
@NoArgsConstructor(access = PROTECTED)
public class HearReport {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member recorder;

    @OneToOne(fetch = FetchType.LAZY)
    private Member receiver;

    @Column(name = "baby_context")
    private String babyContext;

    @Column(name = "emotion")
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @Column(name = "is_checked")
    private boolean isChecked;

    @Builder
    private HearReport(
            Member recorder,
            Member receiver,
            String babyContext,
            Emotion emotion
    ) {
        recorder.updateEmotion(emotion);
        receiver.updateNotificationStatus(true);

        this.recorder = recorder;
        this.receiver = receiver;
        this.babyContext = babyContext;
        this.emotion = emotion;
        this.isChecked = false;
    }

    public static HearReport of(
            Member recorder,
            Member receiver,
            String babyContext,
            Emotion emotion
    ) {
        return HearReport.builder()
                .recorder(recorder)
                .receiver(receiver)
                .babyContext(babyContext)
                .emotion(emotion)
                .build();
    }
}
