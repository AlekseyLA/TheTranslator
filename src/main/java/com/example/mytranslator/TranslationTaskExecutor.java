package com.example.mytranslator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TranslationTaskExecutor {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    public TranslationTaskExecutor(RestTemplate restTemplate, int threadPoolSize) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public List<String> executeTranslations(List<Callable<String>> tasks) throws InterruptedException, ExecutionException {
        List<String> translations = new ArrayList<>();
        List<Future<String>> results = executorService.invokeAll(tasks);
        for (Future<String> result : results) {
            translations.add(result.get());
        }
        return translations;
    }

    public Callable<String> createTranslationTask(TranslationRequestBody requestBody, String apiUrl, HttpHeaders headers, String word) {
        return () -> {
            TranslationRequestBody tempRequestBody = new TranslationRequestBody();
            tempRequestBody.setSourceLanguageCode(requestBody.getSourceLanguageCode());
            tempRequestBody.setTargetLanguageCode(requestBody.getTargetLanguageCode());
            tempRequestBody.setFolderId(requestBody.getFolderId());
            tempRequestBody.setTexts(word);

            HttpEntity<TranslationRequestBody> requestEntity = new HttpEntity<>(tempRequestBody, headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    TranslationResponse translationResponse = objectMapper.readValue(response.getBody(), TranslationResponse.class);
                    if (translationResponse != null && !translationResponse.getTranslations().isEmpty()) {
                        return translationResponse.getTranslations().get(0).getText();
                    }
                }
                throw new RuntimeException("Failed with status code: " + response.getStatusCode() + ", body: " + response.getBody());
            } catch (Exception e) {
                throw new RuntimeException("Failed to translate text: " + e.getMessage(), e);
            }
        };
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
