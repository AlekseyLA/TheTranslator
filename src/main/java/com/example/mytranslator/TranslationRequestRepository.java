package com.example.mytranslator;

import com.example.mytranslator.TranslationRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRequestRepository extends CrudRepository<TranslationRequest, Long> {
}
