-- Este script é executado automaticamente pelo Spring Boot ao iniciar,
-- devido à configuração 'spring.jpa.hibernate.ddl-auto=create-drop'.
-- Ele cria um usuário administrador padrão para o primeiro acesso.

-- 1. Insere um registro de contato
INSERT INTO contato (id, telefone, email, endereco)
VALUES (1, '(62) 9653-5236', 'admin@postopdv.com', 'Rua do Sistema, 1')
ON CONFLICT (id) DO NOTHING;

-- 2. Insere um registro de acesso com senha criptografada para 'admin123'
-- A senha abaixo é 'admin123' criptografada com BCrypt.
-- Usamos E'...' para garantir que caracteres especiais sejam interpretados corretamente.
INSERT INTO acesso (id, usuario, senha, tipo_acesso)
VALUES (1, 'admin', E'$2a$10$PjfZryHcgK9wGOpOmZVrauxMO/5GjwfhsBPkY1R8zgzRiF6Hp9zAi', 'ADMINISTRADOR')
ON CONFLICT (id) DO NOTHING;

-- 3. Insere a pessoa 'Administrador' e a associa ao contato e acesso criados
INSERT INTO pessoas (id, nome_completo, cpf_cnpj, data_nascimento, tipo_pessoa, contato_id, acesso_id)
VALUES (1, 'Administrador do Sistema', '71253863180', '2000-04-03', 'FISICA', 1, 1)
ON CONFLICT (id) DO NOTHING;

-- Reseta a sequência dos IDs para evitar conflitos ao inserir novos dados pela aplicação
SELECT setval('contato_id_seq', (SELECT MAX(id) FROM contato), true);
SELECT setval('acesso_id_seq', (SELECT MAX(id) FROM acesso), true);
SELECT setval('pessoas_id_seq', (SELECT MAX(id) FROM pessoas), true);