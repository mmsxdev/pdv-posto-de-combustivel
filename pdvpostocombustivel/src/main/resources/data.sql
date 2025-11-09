-- Este script é executado automaticamente pelo Spring Boot ao iniciar,
-- =================================================================================================
-- INSERÇÃO DE DADOS INICIAIS (SEED)
-- Este script popula o banco de dados com dados de exemplo para desenvolvimento e testes.
-- A cláusula 'ON CONFLICT (id) DO NOTHING' previne erros se os registros já existirem.
-- =================================================================================================

-- 1. PESSOAS E ACESSOS
-- =================================================================================================

-- Contatos
INSERT INTO contato (id, telefone, email, endereco) VALUES
(1, '(62) 99999-0000', 'admin@postopdv.com', 'Rua do Sistema, 1, Centro'),
(2, '(62) 98888-0001', 'gerencia@postopdv.com', 'Avenida Principal, 100, Setor Sul'),
(3, '(62) 97777-0002', 'frentista@postopdv.com', 'Rua dos Frentistas, 20, Setor Oeste')
ON CONFLICT (id) DO NOTHING;

-- Acessos (Senhas: admin123, gerencia123, frentista123)
INSERT INTO acesso (id, usuario, senha, tipo_acesso) VALUES
(1, 'admin', '$2a$10$PjfZryHcgK9wGOpOmZVrauxMO/5GjwfhsBPkY1R8zgzRiF6Hp9zAi', 'ADMINISTRADOR'),
(2, 'gerencia', '$2a$10$y.TRLg3hVb2c9oQJbH/L9.tG/f8ACv2EAXI2Iu9x2Yp3.U/bJgC2e', 'GERENCIA'),
(3, 'frentista', '$2a$10$9gVbVzGqj2dF8zX.Y/b.e.oKjL3C/b.e.oKjL3C/b.e.oKjL3C/b.e', 'FUNCIONARIO')
ON CONFLICT (id) DO NOTHING;

-- Pessoas
INSERT INTO pessoas (id, nome_completo, cpf_cnpj, data_nascimento, tipo_pessoa, numero_ctps, contato_id, acesso_id) VALUES
(1, 'Administrador do Sistema', '00000000000', '2000-01-01', 'FISICA', null, 1, 1),
(2, 'Gerente Padrão', '11122233344', '1990-05-15', 'FISICA', null, 2, 2),
(3, 'João Frentista Silva', '55566677788', '1995-10-20', 'FISICA', 1234567, 3, 3)
ON CONFLICT (id) DO NOTHING;

-- 2. PRODUTOS
-- =================================================================================================
INSERT INTO produto (id, nome, referencia, fornecedor, marca, categoria, quantidade_minima) VALUES
(1, 'Gasolina Comum', 'GAS-COMUM', 'Petrobras Distribuidora', 'Petrobras', 'COMBUSTIVEL', 1000.00),
(2, 'Gasolina Aditivada', 'GAS-ADIT', 'Petrobras Distribuidora', 'Grid', 'COMBUSTIVEL', 500.00),
(3, 'Etanol Comum', 'ETANOL-COMUM', 'Raízen', 'Shell', 'COMBUSTIVEL', 800.00),
(4, 'Diesel S-10 Comum', 'DIESEL-S10', 'Ipiranga', 'Ipiranga', 'COMBUSTIVEL', 1000.00),
(5, 'Óleo Lubrificante 15W40', 'LUB-15W40-SN', 'Cosan', 'Mobil', 'LUBRIFICANTE', 10.00),
(6, 'Aditivo para Radiador', 'ADIT-RAD-01', 'Tirreno', 'Radiex', 'ADITIVO', 5.00),
(7, 'Água Mineral 500ml', 'CONV-AGUA-500', 'Distribuidora Local', 'Cristal', 'CONVENIENCIA', 24.00)
ON CONFLICT (id) DO NOTHING;

-- 3. PREÇOS
-- =================================================================================================
-- Define os preços atuais para os produtos.
INSERT INTO preco (id, produto_id, valor, data_vigencia) VALUES
(1, 1, 5.589, CURRENT_DATE - 1), -- Gasolina Comum
(2, 2, 5.789, CURRENT_DATE - 1), -- Gasolina Aditivada
(3, 3, 3.799, CURRENT_DATE - 1), -- Etanol
(4, 4, 5.999, CURRENT_DATE - 1), -- Diesel S-10
(5, 5, 45.00, CURRENT_DATE - 30), -- Óleo Lubrificante
(6, 6, 25.50, CURRENT_DATE - 30), -- Aditivo Radiador
(7, 7, 3.00, CURRENT_DATE - 30)  -- Água Mineral
ON CONFLICT (id) DO NOTHING;

-- 4. ESTOQUE
-- =================================================================================================
INSERT INTO estoque (id, produto_id, quantidade, local_tanque, local_endereco, lote_fabricacao, data_validade) VALUES
(1, 1, 10000.00, 'Tanque 01', 'Subsolo Pista A', 'LOTE-GC-001', '2026-12-31'),
(2, 2, 8000.00, 'Tanque 02', 'Subsolo Pista A', 'LOTE-GA-001', '2026-12-31'),
(3, 3, 12000.00, 'Tanque 03', 'Subsolo Pista B', 'LOTE-ET-001', '2026-12-31'),
(4, 4, 15000.00, 'Tanque 04', 'Subsolo Pista C', 'LOTE-DS-001', '2026-12-31'),
(5, 5, 50.00, 'Prateleira A-1', 'Depósito Principal', 'LOTE-LUB-050', '2028-10-01'),
(6, 6, 30.00, 'Prateleira B-2', 'Depósito Principal', 'LOTE-ADR-030', '2029-01-01'),
(7, 7, 100.00, 'Geladeira 1', 'Loja de Conveniência', 'LOTE-AGUA-100', '2026-08-01')
ON CONFLICT (id) DO NOTHING;

-- 5. CUSTOS
-- =================================================================================================
INSERT INTO custo (id, tipo_custo, imposto, custo_variavel, custo_fixo, margem_lucro, data_processamento) VALUES
(1, 'ENERGIA', 18.00, 0.50, 2500.00, 15.00, CURRENT_DATE - 30),
(2, 'FRETE', 12.00, 0.15, 1200.00, 10.00, CURRENT_DATE - 30),
(3, 'AGUA', 5.00, 0.05, 800.00, 20.00, CURRENT_DATE - 30)
ON CONFLICT (id) DO NOTHING;

-- 6. ABASTECIMENTO (ESTADO DAS BOMBAS)
-- =================================================================================================
-- Todas as bombas começam como 'AVAILABLE' (Disponível).
INSERT INTO abastecimento (id, numero_bomba, pista, status, litros, valor, duracao_minutos) VALUES
(1, 'Bomba 1', 'A', 'AVAILABLE', 0, 0, 0),
(2, 'Bomba 2', 'A', 'AVAILABLE', 0, 0, 0),
(3, 'Bomba 3', 'A', 'AVAILABLE', 0, 0, 0),
(4, 'Bomba 4', 'A', 'AVAILABLE', 0, 0, 0),
(5, 'Bomba 5', 'B', 'AVAILABLE', 0, 0, 0),
(6, 'Bomba 6', 'B', 'AVAILABLE', 0, 0, 0),
(7, 'Bomba 7', 'B', 'AVAILABLE', 0, 0, 0),
(8, 'Bomba 8', 'B', 'AVAILABLE', 0, 0, 0),
(9, 'Bomba 9', 'C', 'AVAILABLE', 0, 0, 0),
(10, 'Bomba 10', 'C', 'AVAILABLE', 0, 0, 0),
(11, 'Bomba 11', 'C', 'AVAILABLE', 0, 0, 0),
(12, 'Bomba 12', 'C', 'AVAILABLE', 0, 0, 0)
ON CONFLICT (id) DO NOTHING;

-- =================================================================================================
-- ATUALIZAÇÃO DAS SEQUÊNCIAS DOS IDs
-- Garante que os próximos registros inseridos pela aplicação não tenham conflito de ID.
-- =================================================================================================
SELECT setval('contato_id_seq', (SELECT MAX(id) FROM contato), true);
SELECT setval('acesso_id_seq', (SELECT MAX(id) FROM acesso), true);
SELECT setval('pessoas_id_seq', (SELECT MAX(id) FROM pessoas), true);
SELECT setval('produto_id_seq', (SELECT MAX(id) FROM produto), true);
SELECT setval('preco_id_seq', (SELECT MAX(id) FROM preco), true);
SELECT setval('estoque_id_seq', (SELECT MAX(id) FROM estoque), true);
SELECT setval('custo_id_seq', (SELECT MAX(id) FROM custo), true);
SELECT setval('abastecimento_id_seq', (SELECT MAX(id) FROM abastecimento), true);