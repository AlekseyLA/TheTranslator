package com.example.mytranslator.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    public TranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TranslationRecord> findAll() {
        String sql = "SELECT * FROM TRANSLATION_RECORD";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TranslationRecord(
                UUID.fromString(rs.getString("id")),
                rs.getString("ip_address"),
                rs.getString("source_text"),
                rs.getString("translated_text")
        ));
    }

    public void save(TranslationRecord record) {
        String sql = "INSERT INTO TRANSLATION_RECORD (id, ip_address, source_text, translated_text) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                record.getId().toString(),
                record.getIpAddress(),
                record.getSourceText(),
                record.getTranslatedText()
        );
    }
}
