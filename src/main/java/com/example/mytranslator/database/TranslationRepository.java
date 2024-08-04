package com.example.mytranslator.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    public TranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TranslationRecord> findAll() {
        String sql = "SELECT * FROM translation_record";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TranslationRecord record = new TranslationRecord();
            record.setId(rs.getLong("id"));
            record.setIpAddress(rs.getString("ip_address"));
            record.setSourceText(rs.getString("source_text"));
            record.setTranslatedText(rs.getString("translated_text"));
            return record;
        });
    }
}
