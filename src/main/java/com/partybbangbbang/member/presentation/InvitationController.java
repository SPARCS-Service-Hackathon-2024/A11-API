package com.partybbangbbang.member.presentation;

import com.partybbangbbang.global.security.annotation.AuthId;
import com.partybbangbbang.member.application.InvitationService;
import com.partybbangbbang.member.application.dto.response.InvitationCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    public ResponseEntity<InvitationCodeResponse> getInvitationCode(@AuthId Long id) {
        InvitationCodeResponse invitationCodeResponse = invitationService.getInvitationCodeResponse(id);
        return ResponseEntity.ok(invitationCodeResponse);
    }
}
