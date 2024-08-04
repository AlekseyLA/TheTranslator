package com.example.mytranslator.database;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecordController {

    private final TranslationRepository translationRepository;

    public RecordController(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @GetMapping("/records.html")
    public String getRecords(Model model) {
        List<TranslationRecord> records = translationRepository.findAll();
        model.addAttribute("records", records);
        return "records.html";
    }
}
