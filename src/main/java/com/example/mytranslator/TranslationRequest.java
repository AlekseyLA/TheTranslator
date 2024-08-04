package com.example.mytranslator;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("translation_requests")
public class TranslationRequest {
    @Id
    private UUID id;
    private String sourceLanguage;
    private String targetLanguage;
    private String text;

}