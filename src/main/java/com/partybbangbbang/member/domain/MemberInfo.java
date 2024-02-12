package com.partybbangbbang.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class MemberInfo {

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(STRING)
    @Column(name = "role")
    private RoleType role;

    private MemberInfo(
            String email,
            String password,
            String nickname
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = RoleType.ROLE_USER;
    }

    public static MemberInfo of(
            String email,
            String password,
            String nickname
    ) {
        return new MemberInfo(
                email,
                password,
                nickname
        );
    }
}
