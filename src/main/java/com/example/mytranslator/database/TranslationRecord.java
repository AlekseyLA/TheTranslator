package com.example.mytranslator.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("translation_record")
public class TranslationRecord {

    @Id
    private Long id;
    private String ipAddress;
    private String sourceText;
    private String translatedText;
}
