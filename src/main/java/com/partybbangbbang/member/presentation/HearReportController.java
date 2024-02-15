package com.partybbangbbang.member.presentation;

import com.partybbangbbang.global.security.annotation.AuthId;
import com.partybbangbbang.member.application.HearReportService;
import com.partybbangbbang.member.application.dto.request.HearReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hearReport")
public class HearReportController {

    private final HearReportService hearReportService;

    @PostMapping
    public ResponseEntity<Void> writeHearReport(
            @AuthId Long id,
            @RequestBody HearReportRequest request
    ) {
        hearReportService.writeHearReport(id, request);
        return ResponseEntity.ok().build();
    }
}
