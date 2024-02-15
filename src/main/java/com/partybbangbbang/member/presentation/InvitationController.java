package com.partybbangbbang.member.presentation;

import com.partybbangbbang.couple.application.dto.response.CoupleIdResponse;
import com.partybbangbbang.global.security.annotation.AuthId;
import com.partybbangbbang.member.application.InvitationService;
import com.partybbangbbang.member.application.dto.request.InvitationCodeRequest;
import com.partybbangbbang.member.application.dto.request.MatchRequest;
import com.partybbangbbang.member.application.dto.response.ValidInvitationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    public ResponseEntity<ValidInvitationResponse> isValidInvitation(
            @AuthId Long id,
            @RequestBody InvitationCodeRequest request
    ) {
        ValidInvitationResponse invitationCodeResponse = invitationService.isValidInvitation(request);
        return ResponseEntity.ok(invitationCodeResponse);
    }

    @PostMapping
    public ResponseEntity<CoupleIdResponse> useMatchCode(
            @AuthId Long id,
            @RequestBody MatchRequest request
    ) {
        CoupleIdResponse response = invitationService.matchCouple(id, request);
        return ResponseEntity.ok(response);
    }
}
