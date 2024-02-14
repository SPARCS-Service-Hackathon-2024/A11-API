package com.partybbangbbang.member.domain;

import com.partybbangbbang.member.domain.constants.RoleType;
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

    @Enumerated(STRING)
    @Column(name = "role")
    private RoleType role;

    private MemberInfo(
            String email,
            String password
    ) {
        this.email = email;
        this.password = password;
        this.role = RoleType.ROLE_USER;
    }

    public static MemberInfo of(
            String email,
            String password
    ) {
        return new MemberInfo(
                email,
                password
        );
    }
}
