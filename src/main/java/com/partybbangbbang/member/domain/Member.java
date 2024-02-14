package com.partybbangbbang.member.domain;

import com.partybbangbbang.couple.domain.constants.Emotion;
import com.partybbangbbang.global.auditing.BaseEntity;
import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.member.domain.constants.Sex;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.partybbangbbang.couple.domain.constants.Emotion.NEUTRAL;
import static com.partybbangbbang.couple.exception.CoupleError.ALREADY_COUPLE;
import static com.partybbangbbang.couple.exception.CoupleError.COUPLE_SAME_SEX;
import static com.partybbangbbang.member.domain.constants.Sex.MALE;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_member")
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private MemberInfo memberInfo;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "invitation_code")
    private String invitationCode;

    @Column(name = "is_matched")
    private boolean isMatched;


    @Enumerated(EnumType.STRING)
    @Column(name = "emotion")
    private Emotion emotion;

    @Column(name = "primary_status")
    private boolean primaryStatus;

    @Builder
    private Member(
            String email,
            String password,
            String nickname,
            Sex sex
    ) {
        this.memberInfo = MemberInfo.of(email, password);
        this.nickname = nickname;
        this.sex = sex;
        this.invitationCode = randomUUID().toString().substring(0, 6);
        this.isMatched = false;
        this.emotion = NEUTRAL;
    }

    public static Member of(
            String email,
            String password,
            String nickname,
            Sex sex
    ) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .sex(sex)
                .build();
    }

    public boolean isHusband() {
        return this.sex.equals(MALE);
    }

    public void updateMatchStatus(boolean newStatus) {
        this.isMatched = newStatus;
    }

    public void validateSameSex(Member member) {
        if (this.sex.equals(member.getSex())) {
            throw BusinessException.of(COUPLE_SAME_SEX);
        }
    }

    public void updatePrimaryStatus(boolean newStatus) {
        this.primaryStatus = newStatus;
    }

    public void validateIsMatched() {
        if (isMatched()) {
            throw BusinessException.of(ALREADY_COUPLE);
        }
    }

}
