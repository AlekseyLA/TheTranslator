package com.example.mytranslator.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRecordRepository extends CrudRepository<TranslationRecord, Long> {
}
