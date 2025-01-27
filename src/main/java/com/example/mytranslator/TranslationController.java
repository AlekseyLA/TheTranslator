package com.example.mytranslator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping("/api/translate")
    public String translate(@RequestBody TranslationRequestBody requestBody) {
        return translationService.translateText(requestBody);
    }
}
