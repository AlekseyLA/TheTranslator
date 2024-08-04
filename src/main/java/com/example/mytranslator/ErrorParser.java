package com.example.mytranslator;

import org.springframework.stereotype.Component;

@Component
public class ErrorParser {

    public String describeError(String error) {
        if (error.toLowerCase().contains("error on post request for"))
            return "http 404 Ошибка доступа к ресурсу перевода (проверьте URL)";
        if (error.toLowerCase().contains("unauthorized"))
            return "http 401 Ошибка доступа к ресурсу перевода (незарегестрированный пользователь, проверьте iam-token)";
        if (error.toLowerCase().contains("permission denied"))
            return "http 403 Доступ запрещен (проверьте folderID)";
        if (error.toLowerCase().contains("unsupported source_language_code"))
            return "http 400 Проверьте код языка источника";
        if (error.toLowerCase().contains("unsupported target_language_cod"))
            return "http 400 Проверьте код языка для перевода";
        if (error.toLowerCase().contains("target_language_code must be set"))
            return "http 400 Необходимо установить код языка для перевода";
        if (error.toLowerCase().contains("texts are empty"))
            return "http 400 Отсутствует текст для перевода";
        return "http Неизвестная ошибка: " + error;
    }
}
