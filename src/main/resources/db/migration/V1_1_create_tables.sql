-- Criação da tabela Topic
CREATE TABLE topic (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Criação da tabela Session
CREATE TABLE session (
    id SERIAL PRIMARY KEY,
    topic_id BIGINT UNIQUE NOT NULL,
    begging TIMESTAMP NOT NULL,
    finish TIMESTAMP NOT NULL,
    CONSTRAINT fk_session_topic FOREIGN KEY (topic_id) REFERENCES topic (id) ON DELETE CASCADE
);

-- Criação da tabela Vote
CREATE TABLE vote (
    id SERIAL PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    vote BOOLEAN NOT NULL,
    CONSTRAINT fk_vote_topic FOREIGN KEY (topic_id) REFERENCES topic (id) ON DELETE CASCADE
);

-- Criação da tabela User
CREATE TABLE voters (
    id CHAR(36) PRIMARY KEY,  -- Armazenando o UUID como String
    cpf VARCHAR(14) NOT NULL UNIQUE
);