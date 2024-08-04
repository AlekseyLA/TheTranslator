CREATE TABLE IF NOT EXISTS TRANSLATION_RECORD (
                                      id VARCHAR(36) PRIMARY KEY,
                                      ip_address TEXT NOT NULL,
                                      source_text TEXT NOT NULL,
                                      translated_text TEXT NOT NULL
);