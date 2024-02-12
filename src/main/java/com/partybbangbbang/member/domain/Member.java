package com.partybbangbbang.member.domain;

import com.partybbangbbang.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
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

    public Member(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    @Builder
    private Member(
            String email,
            String password,
            String nickname
    ) {
        this.memberInfo = MemberInfo.of(email, password, nickname);
    }

    public static Member of(
            String email,
            String password,
            String nickname
    ) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
