package com.partybbangbbang.global.stt;

import com.partybbangbbang.global.stt.dto.SttResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/util/stt")
public class SttController {

    @GetMapping
    public ResponseEntity<SttResponse> getSttResponse(@RequestParam(name = "cts") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
            System.out.println("ㅇ = ");
        }
        SttResponse response = SttModule.convert(file);
        return ResponseEntity.ok(response);
    }
}
