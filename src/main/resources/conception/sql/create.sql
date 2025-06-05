CREATE TABLE IF NOT EXISTS users (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     username TEXT NOT NULL,
                                     email TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     remote_id INTEGER,

                                     CONSTRAINT email_unique UNIQUE (email)
    );

-- Create trigger to auto-update updated_at on row modification
CREATE TRIGGER IF NOT EXISTS update_user_timestamp
    AFTER UPDATE ON users
BEGIN
UPDATE users SET updated_at = CURRENT_TIMESTAMP WHERE id = NEW.id;
END;

-- Creates the accounts table
CREATE TABLE IF NOT EXISTS accounts (
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        name TEXT NOT NULL,
                                        description TEXT,
                                        id_owner INTEGER NOT NULL,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,


                                        FOREIGN KEY (id_owner) REFERENCES users(id) ON DELETE CASCADE
);
CREATE TRIGGER IF NOT EXISTS update_accounts_timestamp
    AFTER UPDATE ON accounts
    FOR EACH ROW
BEGIN
    UPDATE accounts SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;

CREATE INDEX IF NOT EXISTS idx_accounts_owner ON accounts(id_owner);


-- Cre
CREATE TABLE IF NOT EXISTS credentials (
                                           id INTEGER PRIMARY KEY AUTOINCREMENT,
                                           id_account INTEGER NOT NULL,
                                           title TEXT NOT NULL,
                                           content TEXT NOT NULL,  -- Should store encrypted data in production
                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           FOREIGN KEY (id_account) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TRIGGER IF NOT EXISTS update_credentials_timestamp
    AFTER UPDATE ON credentials
    FOR EACH ROW
BEGIN
    UPDATE credentials SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;

CREATE INDEX IF NOT EXISTS idx_credentials_account ON credentials(id_account);