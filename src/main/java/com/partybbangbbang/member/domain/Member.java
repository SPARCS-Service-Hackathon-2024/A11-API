package com.partybbangbbang.member.domain;

import com.partybbangbbang.global.auditing.BaseEntity;
import com.partybbangbbang.member.domain.constants.Sex;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
