package com.example.mytranslator;

import com.example.mytranslator.database.TranslationRecord;
import com.example.mytranslator.database.TranslationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Service
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final RestTemplate restTemplate;
    private TranslationTaskExecutor translationTaskExecutor;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorParser errorParser;


    @Value("${yandex.api.token}")
    private String apiToken;

    @Value("${yandex.folder.id}")
    private String folderId;

    @Value("${yandex.api.url}")
    private String apiUrl;

    @PostConstruct
    public void init() {
        this.translationTaskExecutor = new TranslationTaskExecutor(restTemplate, 10);
    }

    public String translateText(TranslationRequestBody requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);
        requestBody.setFolderId(folderId);

        String[] words = requestBody.getTexts().split("\\s+");

        List<Callable<String>> tasks = new ArrayList<>();
        for (String word : words) {
            tasks.add(translationTaskExecutor.createTranslationTask(requestBody, apiUrl, headers, word));
        }

        try {
            List<String> translations = translationTaskExecutor.executeTranslations(tasks);
            String translatedText = String.join(" ", translations);
            String ipAddress = getExternalIpAddress();
            saveTranslationRecord(ipAddress, requestBody.getTexts(), translatedText);
            return "http 200" + " " + translatedText;
        } catch (Exception e) {
            return errorParser.describeError(e.getMessage());
        }
    }

    private String getExternalIpAddress() {
        try {
            String response = restTemplate.getForObject("https://api.ipify.org?format=json", String.class);
            return objectMapper.readTree(response).get("ip").asText();
        } catch (Exception e) {
            return "Failed to get IP address from api.ipify.org";
        }
    }

    private void saveTranslationRecord(String ipAddress, String sourceText, String translatedText) {
        TranslationRecord record = new TranslationRecord(UUID.randomUUID(), ipAddress, sourceText, translatedText);
        translationRepository.save(record);
    }

    @PreDestroy
    public void shutdown() {
        translationTaskExecutor.shutdown();
    }
}
