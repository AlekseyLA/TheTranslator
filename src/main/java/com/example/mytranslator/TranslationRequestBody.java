package com.example.mytranslator;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TranslationRequestBody {
    private String targetLanguageCode;
    private String sourceLanguageCode;
    private String texts;
    private String folderId;
}
