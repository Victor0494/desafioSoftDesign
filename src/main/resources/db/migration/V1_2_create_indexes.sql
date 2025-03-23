-- Índice para acelerar buscas de sessões por tópico
CREATE INDEX idx_session_topic ON session(topic_id);

-- Índice para otimizar contagem de votos por tópico
CREATE INDEX idx_vote_topic ON vote(topic_id);

-- Índice para evitar que o mesmo CPF vote mais de uma vez no mesmo tópico
CREATE UNIQUE INDEX idx_vote_cpf_topic ON vote(topic_id, cpf);
