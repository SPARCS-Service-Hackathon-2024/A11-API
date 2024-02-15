package com.partybbangbbang.global.stt.dto;

import org.springframework.web.multipart.MultipartFile;

public record SttRequest(
        MultipartFile content
) {
}
