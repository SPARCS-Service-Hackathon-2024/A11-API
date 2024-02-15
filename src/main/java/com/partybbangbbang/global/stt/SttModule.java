package com.partybbangbbang.global.stt;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import com.partybbangbbang.global.exception.BusinessException;
import com.partybbangbbang.global.stt.dto.SttResponse;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.partybbangbbang.global.exception.error.GlobalError.INVALID_REQUEST_PARAM;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class SttModule {

    public static SttResponse convert(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw BusinessException.of(INVALID_REQUEST_PARAM);
        }

        String tmpDir = "/tmp";
        File convertedFile = new File(tmpDir + File.separator + file.getOriginalFilename());

        file.transferTo(convertedFile);

        try (SpeechClient speechClient = SpeechClient.create()) {
            byte[] audioBytes = Files.readAllBytes(convertedFile.toPath());

            ByteString audioData = ByteString.copyFrom(audioBytes);
            RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
                    .setContent(audioData)
                    .build();

            RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.ENCODING_UNSPECIFIED)
                    .setSampleRateHertz(44100)
                    .setLanguageCode("ko-KR") // 한국어로 설정
                    .build();

            RecognizeResponse response = speechClient.recognize(recognitionConfig, recognitionAudio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            SpeechRecognitionResult result = results.get(0);

            return new SttResponse((result.getAlternatives(0).getTranscript()));
        } catch (Exception e) {
            throw new RuntimeException("Error processing audio file: " + file.getOriginalFilename(), e);
        }
    }
}
