package com.example.mytranslator;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TranslationRequestRepository extends CrudRepository<TranslationRequest, UUID> {
}
