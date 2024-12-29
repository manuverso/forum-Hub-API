-- Tabela perfis
CREATE TABLE perfis (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Tabela cursos
CREATE TABLE cursos (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(255)
);

-- Tabela usuarios
CREATE TABLE usuarios (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL, -- Armazenar hash da senha, NUNCA a senha em texto plano
    perfil_id INT UNSIGNED,
    FOREIGN KEY (perfil_id) REFERENCES perfis(id)
);

-- Tabela topicos
CREATE TABLE topicos (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50),
    autor_id INT UNSIGNED,
    curso_id INT UNSIGNED,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Tabela respostas
CREATE TABLE respostas (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    mensagem TEXT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    autor_id INT UNSIGNED,
    topico_id INT UNSIGNED,
    solucao BOOLEAN DEFAULT FALSE, -- Indica se a resposta é a solução
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (topico_id) REFERENCES topicos(id)
);