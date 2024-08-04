package com.example.mytranslator;

import com.example.mytranslator.database.TranslationRecord;
import com.example.mytranslator.database.TranslationRepository;
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

    @GetMapping("/records")
    public String getRecords(Model model) {
        List<TranslationRecord> records = translationRepository.findAll();
        model.addAttribute("records", records);
        return "records";
    }
}
