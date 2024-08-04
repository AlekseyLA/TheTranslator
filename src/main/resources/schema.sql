CREATE TABLE IF NOT EXISTS translation_record (
                                                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                  ip_address TEXT NOT NULL,
                                                  source_text TEXT NOT NULL,
                                                  translated_text TEXT NOT NULL
);