package com.example.mytranslator;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TranslationResponse {
    private List<Translation> translations;

    @Setter
    @Getter
    public static class Translation {
        private String text;

    }
}
