package com.example.mytranslator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Arrays.asList;

@Service
public class TranslationService {

    private final ObjectMapper objectMapper;

    @Value("${yandex.api.token}")
    private String apiToken;

    @Value("${yandex.folder.id}")
    private String folderId;

    @Value("${yandex.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final TranslationTaskExecutor translationTaskExecutor;

    public TranslationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
        this.translationTaskExecutor = new TranslationTaskExecutor(restTemplate, 10); // Создаем с пулом в 10 потоков
    }

    public String translateText(TranslationRequestBody requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);
        requestBody.setFolderId(folderId);

        String[] words = requestBody.getTexts().split("\\s+");

        // Создайте список задач
        List<Callable<String>> tasks = new ArrayList<>();
        for (String word : words) {
            tasks.add(translationTaskExecutor.createTranslationTask(requestBody, apiUrl, headers, word));
        }

        // Выполните задачи в пуле потоков и получите результаты
        try {
            List<String> translations = translationTaskExecutor.executeTranslations(tasks);
            getExternalIpAddress();
            return String.join(" ", translations);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute translation tasks: " + e.getMessage(), e);
        }
    }

    public String getExternalIpAddress() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("https://api.ipify.org?format=json", String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readTree(response.getBody()).get("ip").asText();
            } else {
                throw new RuntimeException("Failed to retrieve external IP address: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve external IP address: " + e.getMessage(), e);
        }
    }

    @PreDestroy
    public void shutdown() {
        translationTaskExecutor.shutdown();
    }
}
