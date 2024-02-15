package com.partybbangbbang.couple.presentation;

import com.partybbangbbang.couple.application.CoupleService;
import com.partybbangbbang.couple.application.dto.response.CoupleMainResponse;
import com.partybbangbbang.global.security.annotation.AuthId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couple")
public class CoupleController {

    private final CoupleService coupleService;

    @GetMapping
    public ResponseEntity<CoupleMainResponse> getCoupleMainResponse(@AuthId Long id) {
        CoupleMainResponse response = coupleService.getCoupleMainResponse(id);
        return ResponseEntity.ok(response);
    }
}
