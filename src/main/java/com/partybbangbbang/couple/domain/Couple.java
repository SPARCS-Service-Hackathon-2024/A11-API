package com.partybbangbbang.couple.domain;

import com.partybbangbbang.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_couple")
@NoArgsConstructor(access = PROTECTED)
public class Couple {

    @Id
    @Column(name = "couple_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member husband;

    @OneToOne(fetch = FetchType.LAZY)
    private Member wife;

    @Column(name = "baby_name")
    private String babyName;

    @Builder
    private Couple(
            Member receiver,
            Member sender,
            String babyName,
            boolean receiverPrimaryStatus
    ) {
        receiver.validateSameSex(sender);
        receiver.validateIsMatched();
        sender.validateIsMatched();

        if (receiverPrimaryStatus) {
            receiver.updatePrimaryStatus(true);
            sender.updatePrimaryStatus(false);
        } else {
            receiver.updatePrimaryStatus(true);
            sender.updatePrimaryStatus(false);
        }

        if (receiver.isHusband()) {
            this.husband = receiver;
            this.wife = sender;
        } else {
            this.wife = receiver;
            this.husband = sender;
        }

        this.babyName = babyName;
    }

    public static Couple of(
            Member receiver,
            Member sender,
            String babyName,
            boolean receiverPrimaryStatus
    ) {
        return Couple.builder()
                .receiver(receiver)
                .sender(sender)
                .babyName(babyName)
                .receiverPrimaryStatus(receiverPrimaryStatus)
                .build();
    }
}
