package com.partybbangbbang.global.stt;

import com.partybbangbbang.global.stt.dto.SttRequest;
import com.partybbangbbang.global.stt.dto.SttResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/util/stt")
public class SttController {

    @GetMapping
    public ResponseEntity<SttResponse> getSttResponse(SttRequest request) throws IOException {
        SttResponse response = SttModule.convert(request.content());
        return ResponseEntity.ok(response);
    }
}
