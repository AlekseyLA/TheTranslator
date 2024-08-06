package com.example.mytranslator.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationRecord {
    private UUID id;
    private String ipAddress;
    private String sourceText;
    private String translatedText;
}
