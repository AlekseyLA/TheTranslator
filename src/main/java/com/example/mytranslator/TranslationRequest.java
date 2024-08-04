package com.example.mytranslator;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TranslationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String originalText;
    private String translatedText;
}
