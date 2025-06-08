-- AquaMind: Script Completo para MySQL

-- =============== SCHEMA ===============





 
CREATE TABLE IF NOT EXISTS estados (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
sigla CHAR(2) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(512) NOT NULL, 
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('produtor', 'admin', 'tecnico')),
    ativo TINYINT DEFAULT 1,
    data_criacao DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    data_atualizacao DATETIME
);


CREATE TABLE IF NOT EXISTS propriedades (
    id_propriedade INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    id_usuario INT NOT NULL,
    id_estado INT NOT NULL,
    area_hectares DECIMAL(10,2) NOT NULL CHECK (area_hectares > 0),
    ativo TINYINT DEFAULT 1,
    data_criacao DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    data_atualizacao DATETIME,
    CONSTRAINT fk_prop_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_prop_estado FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
);


CREATE TABLE IF NOT EXISTS zonas (
    id_zona INT AUTO_INCREMENT PRIMARY KEY,
    id_propriedade INT NOT NULL,
    nome VARCHAR(150) NOT NULL,
    area_hectares DECIMAL(10,2) NOT NULL CHECK (area_hectares > 0),
    ativo TINYINT DEFAULT 1,
    data_criacao DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    data_atualizacao DATETIME,
    CONSTRAINT fk_zona_propriedade FOREIGN KEY (id_propriedade) REFERENCES propriedades(id_propriedade)
);


CREATE TABLE IF NOT EXISTS sensores (
    id_sensor INT AUTO_INCREMENT PRIMARY KEY,
    id_zona INT NOT NULL,
    tipo_sensor VARCHAR(50) NOT NULL, 
    modelo VARCHAR(100),
    ativo TINYINT DEFAULT 1,
    data_instalacao DATETIME,
    data_criacao DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    data_atualizacao DATETIME,
    CONSTRAINT fk_sensor_zona FOREIGN KEY (id_zona) REFERENCES zonas(id_zona)
);


CREATE TABLE IF NOT EXISTS registros_sensor (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,
    id_sensor INT NOT NULL,
    data_hora DATETIME NOT NULL,
    valor NUMERIC(10,4) NOT NULL,
    CONSTRAINT fk_registro_sensor FOREIGN KEY (id_sensor) REFERENCES sensores(id_sensor)
);


CREATE TABLE IF NOT EXISTS bombas (
    id_bomba INT AUTO_INCREMENT PRIMARY KEY,
    id_zona INT NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('ligada', 'desligada', 'manutencao')),
    ativo TINYINT DEFAULT 1,
    data_instalacao DATETIME,
    data_criacao DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    data_atualizacao DATETIME,
    CONSTRAINT fk_bomba_zona FOREIGN KEY (id_zona) REFERENCES zonas(id_zona)
);


CREATE TABLE IF NOT EXISTS logs_acao_bomba (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_bomba INT NOT NULL,
    data_hora DATETIME NOT NULL,
    acao VARCHAR(20) NOT NULL CHECK (acao IN ('ligar', 'desligar')),
    id_usuario INT NOT NULL, 
    CONSTRAINT fk_log_bomba FOREIGN KEY (id_bomba) REFERENCES bombas(id_bomba),
    CONSTRAINT fk_log_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);


CREATE TABLE IF NOT EXISTS configuracoes_zona (
    id_config INT AUTO_INCREMENT PRIMARY KEY,
    id_zona INT NOT NULL,
    limite_umidade_min DECIMAL(5,2) CHECK (limite_umidade_min >= 0 AND limite_umidade_min <= 100),
    horario_inicio_irriga VARCHAR(5) CHECK (REGEXP_LIKE(horario_inicio_irriga, '^\d{2}:\d{2}$')), 
    horario_fim_irriga VARCHAR(5) CHECK (REGEXP_LIKE(horario_fim_irriga, '^\d{2}:\d{2}$')),
    ativo TINYINT DEFAULT 1,
    data_criacao DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    data_atualizacao DATETIME,
    CONSTRAINT fk_config_zona FOREIGN KEY (id_zona) REFERENCES zonas(id_zona)
);


CREATE TABLE IF NOT EXISTS alertas_umidade (
    id_alerta INT AUTO_INCREMENT PRIMARY KEY,
    id_zona INT NOT NULL,
    data_hora DATETIME NOT NULL,
    umidade_atual DECIMAL(5,2) NOT NULL CHECK (umidade_atual >= 0 AND umidade_atual <= 100),
    descricao VARCHAR(400),
    resolvido TINYINT DEFAULT 0, 
    data_criacao DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    data_atualizacao DATETIME,
    CONSTRAINT fk_alerta_zona FOREIGN KEY (id_zona) REFERENCES zonas(id_zona)
);


CREATE TABLE IF NOT EXISTS historico_acoes_usuario (
    id_acao INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    descricao VARCHAR(400) NOT NULL,
    data_hora DATETIME DEFAULT CURRENT_DATETIME NOT NULL,
    CONSTRAINT fk_hist_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);




BEGIN
  FOR s IN (
    SELECT sequence_name FROM user_sequences
    WHERE sequence_name IN (
      'SEQ_USUARIOS', 'SEQ_PROPRIEDADES', 'SEQ_ZONAS', 'SEQ_SENSORES', 'SEQ_REGISTROS_SENSOR',
      'SEQ_BOMBAS', 'SEQ_LOGS_ACAO_BOMBA', 'SEQ_CONFIGURACOES_ZONA', 'SEQ_ALERTAS_UMIDADE', 'SEQ_HISTORICO_ACOES_USUARIO'
    )
  ) LOOP
    BEGIN
      EXECUTE IMMEDIATE 'DROP AUTO_INCREMENT ' || s.sequence_name;
    EXCEPTION
      WHEN OTHERS THEN NULL;
    END;
  END LOOP;
END;
CREATE AUTO_INCREMENT seq_usuarios START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_propriedades START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_zonas START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_sensores START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_registros_sensor START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_bombas START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_logs_acao_bomba START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_configuracoes_zona START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_alertas_umidade START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE AUTO_INCREMENT seq_historico_acoes_usuario START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO estados (id_estado, nome, sigla) VALUES (1, 'Estado_1', 'SP');
INSERT INTO estados (id_estado, nome, sigla) VALUES (2, 'Estado_2', 'RJ');
INSERT INTO estados (id_estado, nome, sigla) VALUES (3, 'Estado_3', 'MG');
INSERT INTO estados (id_estado, nome, sigla) VALUES (4, 'Estado_4', 'BA');
INSERT INTO estados (id_estado, nome, sigla) VALUES (5, 'Estado_5', 'ES');


INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (1, 'Usuário_1', 'usuario1@exemplo.com', 'senhaHash1', 'produtor', 1, CURRENT_DATETIME);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (2, 'Usuário_2', 'usuario2@exemplo.com', 'senhaHash2', 'produtor', 1, CURRENT_DATETIME);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (3, 'Usuário_3', 'usuario3@exemplo.com', 'senhaHash3', 'produtor', 1, CURRENT_DATETIME);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (4, 'Usuário_4', 'usuario4@exemplo.com', 'senhaHash4', 'produtor', 1, CURRENT_DATETIME);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (5, 'Usuário_5', 'usuario5@exemplo.com', 'senhaHash5', 'produtor', 1, CURRENT_DATETIME);


INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (1, 'Propriedade_1', 1, 1, 11.0, 1, CURRENT_DATETIME);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (2, 'Propriedade_2', 2, 2, 12.0, 1, CURRENT_DATETIME);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (3, 'Propriedade_3', 3, 3, 13.0, 1, CURRENT_DATETIME);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (4, 'Propriedade_4', 4, 4, 14.0, 1, CURRENT_DATETIME);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (5, 'Propriedade_5', 5, 5, 15.0, 1, CURRENT_DATETIME);


INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (1, 1, 'Zona_1', 6.0, 1, CURRENT_DATETIME);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (2, 2, 'Zona_2', 7.0, 1, CURRENT_DATETIME);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (3, 3, 'Zona_3', 8.0, 1, CURRENT_DATETIME);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (4, 4, 'Zona_4', 9.0, 1, CURRENT_DATETIME);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (5, 5, 'Zona_5', 10.0, 1, CURRENT_DATETIME);


INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (1, 1, 'umidade', 'Sensor_Modelo_1', 1, NOW(), CURRENT_DATETIME);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (2, 2, 'umidade', 'Sensor_Modelo_2', 1, NOW(), CURRENT_DATETIME);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (3, 3, 'umidade', 'Sensor_Modelo_3', 1, NOW(), CURRENT_DATETIME);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (4, 4, 'umidade', 'Sensor_Modelo_4', 1, NOW(), CURRENT_DATETIME);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (5, 5, 'umidade', 'Sensor_Modelo_5', 1, NOW(), CURRENT_DATETIME);


INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (1, 1, CURRENT_DATETIME, 56.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (2, 2, CURRENT_DATETIME, 57.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (3, 3, CURRENT_DATETIME, 58.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (4, 4, CURRENT_DATETIME, 59.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (5, 5, CURRENT_DATETIME, 60.5);


INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (1, 1, 'Modelo Bomba 1', 'ligada', 1, NOW(), CURRENT_DATETIME);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (2, 2, 'Modelo Bomba 2', 'ligada', 1, NOW(), CURRENT_DATETIME);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (3, 3, 'Modelo Bomba 3', 'ligada', 1, NOW(), CURRENT_DATETIME);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (4, 4, 'Modelo Bomba 4', 'ligada', 1, NOW(), CURRENT_DATETIME);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (5, 5, 'Modelo Bomba 5', 'ligada', 1, NOW(), CURRENT_DATETIME);


INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (1, 1, CURRENT_DATETIME, 'ligar', 1);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (2, 2, CURRENT_DATETIME, 'ligar', 2);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (3, 3, CURRENT_DATETIME, 'ligar', 3);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (4, 4, CURRENT_DATETIME, 'ligar', 4);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (5, 5, CURRENT_DATETIME, 'ligar', 5);


INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (1, 1, 31.0, '06:01', '08:01', 1, CURRENT_DATETIME);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (2, 2, 32.0, '06:02', '08:02', 1, CURRENT_DATETIME);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (3, 3, 33.0, '06:03', '08:03', 1, CURRENT_DATETIME);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (4, 4, 34.0, '06:04', '08:04', 1, CURRENT_DATETIME);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (5, 5, 35.0, '06:05', '08:05', 1, CURRENT_DATETIME);


INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (1, 1, CURRENT_DATETIME, 21.0, 'Alerta gerado automaticamente', 0, CURRENT_DATETIME);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (2, 2, CURRENT_DATETIME, 22.0, 'Alerta gerado automaticamente', 0, CURRENT_DATETIME);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (3, 3, CURRENT_DATETIME, 23.0, 'Alerta gerado automaticamente', 0, CURRENT_DATETIME);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (4, 4, CURRENT_DATETIME, 24.0, 'Alerta gerado automaticamente', 0, CURRENT_DATETIME);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (5, 5, CURRENT_DATETIME, 25.0, 'Alerta gerado automaticamente', 0, CURRENT_DATETIME);


INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (1, 1, 'Usuário realizou ação 1', CURRENT_DATETIME);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (2, 2, 'Usuário realizou ação 2', CURRENT_DATETIME);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (3, 3, 'Usuário realizou ação 3', CURRENT_DATETIME);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (4, 4, 'Usuário realizou ação 4', CURRENT_DATETIME);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (5, 5, 'Usuário realizou ação 5', CURRENT_DATETIME);





CREATE OR REPLACE TRIGGER trg_update_usuarios
BEFORE UPDATETIME ON usuarios
FOR EACH ROW
BEGIN
  :NEW.data_atualizacao := CURRENT_DATETIME;
END;
CREATE OR REPLACE TRIGGER trg_update_propriedades
BEFORE UPDATETIME ON propriedades
FOR EACH ROW
BEGIN
  :NEW.data_atualizacao := CURRENT_DATETIME;
END;
CREATE OR REPLACE TRIGGER trg_update_zonas
BEFORE UPDATETIME ON zonas
FOR EACH ROW
BEGIN
  :NEW.data_atualizacao := CURRENT_DATETIME;
END;
CREATE OR REPLACE TRIGGER trg_update_sensores
BEFORE UPDATETIME ON sensores
FOR EACH ROW
BEGIN
  :NEW.data_atualizacao := CURRENT_DATETIME;
END;
CREATE OR REPLACE TRIGGER trg_update_bombas
BEFORE UPDATETIME ON bombas
FOR EACH ROW
BEGIN
  :NEW.data_atualizacao := CURRENT_DATETIME;
END;




CREATE OR REPLACE TRIGGER trg_usuarios_nao_desativa_admin
BEFORE UPDATETIME ON usuarios
FOR EACH ROW
DECLARE
  v_count INT;
BEGIN
  IF :OLD.tipo_usuario = 'admin' AND :NEW.ativo = 0 THEN
    SELECT COUNT(*) INTO v_count FROM usuarios WHERE tipo_usuario = 'admin' AND ativo = 1;
    IF v_count <= 1 THEN
      RAISE_APPLICATION_ERROR(-20002, 'Não pode desativar o último admin ativo.');
    END IF;
  END IF;
END;
CREATE OR REPLACE TRIGGER trg_bomba_checa_umidade
BEFORE UPDATETIME ON bombas
FOR EACH ROW
DECLARE
  v_umidade INT;
BEGIN
  IF :NEW.status = 'ligada' THEN
    SELECT fn_ultimo_valor_umidade((SELECT id_zona FROM bombas WHERE id_bomba = :OLD.id_bomba)) INTO v_umidade FROM dual;
    IF v_umidade > 60 THEN
      RAISE_APPLICATION_ERROR(-20003, 'Umidade está alta, bomba não pode ser ligada.');
    END IF;
  END IF;
END;
CREATE OR REPLACE TRIGGER trg_logs_acao_bomba_data
BEFORE INSERT ON logs_acao_bomba
FOR EACH ROW
BEGIN
  :NEW.data_hora := CURRENT_DATETIME;
END;
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_sensores_tipo_ativo ON sensores(tipo_sensor, ativo);
CREATE INDEX idx_bombas_status_ativo ON bombas(status, ativo);
CREATE INDEX idx_zonas_id_propriedade ON zonas(id_propriedade);
CREATE INDEX idx_alertas_resolvido ON alertas_umidade(resolvido);
CREATE INDEX idx_propriedades_id_usuario ON propriedades(id_usuario);
CREATE INDEX idx_alertas_data_hora ON alertas_umidade(data_hora);
CREATE INDEX idx_logs_acao_bomba_id_bomba ON logs_acao_bomba(id_bomba);




CREATE OR REPLACE TRIGGER trg_check_horario_irriga
BEFORE INSERT OR UPDATETIME ON configuracoes_zona
FOR EACH ROW
DECLARE
  v_inicio INT;
  v_fim INT;
BEGIN
  v_inicio := TO_INT(REPLACE(:NEW.horario_inicio_irriga, ':', ''));
  v_fim := TO_INT(REPLACE(:NEW.horario_fim_irriga, ':', ''));
  IF v_fim <= v_inicio THEN
    RAISE_APPLICATION_ERROR(-20001, 'Horario fim deve ser maior que horario inicio');
  END IF;
END;
CREATE OR REPLACE TRIGGER trg_bomba_modelo_nao_vazio
BEFORE INSERT OR UPDATETIME ON bombas
FOR EACH ROW
BEGIN
  IF :NEW.modelo IS NULL OR TRIM(:NEW.modelo) = '' THEN
    RAISE_APPLICATION_ERROR(-20004, 'Modelo da bomba não pode ser vazio.');
  END IF;
END;
CREATE OR REPLACE TRIGGER trg_sensor_tipo_valido
BEFORE INSERT OR UPDATETIME ON sensores
FOR EACH ROW
BEGIN
  IF :NEW.tipo_sensor NOT IN ('umidade', 'temperatura', 'pressao') THEN
    RAISE_APPLICATION_ERROR(-20005, 'Tipo de sensor inválido.');
  END IF;
END;
CREATE OR REPLACE VIEW vw_usuarios_ativos AS
SELECT id_usuario, nome, email, tipo_usuario
FROM usuarios
WHERE ativo = 1;

CREATE OR REPLACE VIEW vw_zonas_ativas AS
SELECT z.id_zona, z.nome, p.nome AS nome_propriedade, z.area_hectares
FROM zonas z
JOIN propriedades p ON z.id_propriedade = p.id_propriedade
WHERE z.ativo = 1;

CREATE OR REPLACE VIEW vw_alertas_pendentes AS
SELECT a.id_alerta, z.nome AS nome_zona, a.data_hora, a.umidade_atual, a.descricao
FROM alertas_umidade a
JOIN zonas z ON a.id_zona = z.id_zona
WHERE a.resolvido = 0;

CREATE OR REPLACE VIEW vw_bombas_ligadas AS
SELECT id_bomba, modelo, id_zona
FROM bombas
WHERE status = 'ligada' AND ativo = 1;

CREATE OR REPLACE VIEW vw_logs_bomba_ultimo_acao AS
SELECT id_bomba, MAX(data_hora) AS ultima_acao
FROM logs_acao_bomba
GROUP BY id_bomba;

CREATE OR REPLACE VIEW vw_bombas_manutencao AS
SELECT id_bomba, modelo, status, data_instalacao
FROM bombas
WHERE status = 'manutencao' AND ativo = 1;

CREATE OR REPLACE VIEW vw_usuarios_admin_ativos AS
SELECT id_usuario, nome, email
FROM usuarios
WHERE tipo_usuario = 'admin' AND ativo = 1;

CREATE OR REPLACE VIEW vw_alertas_mais_recente AS
SELECT id_alerta, id_zona, data_hora, umidade_atual, descricao
FROM alertas_umidade
WHERE data_hora > NOW() - 1
ORDER BY data_hora DESC;



=
CREATE OR REPLACE PROCEDURE verificar_ligar_bomba_por_umidade(p_id_zona IN INT) AS
  v_umidade DECIMAL(5,2);
  v_id_bomba INT;
BEGIN
  SELECT fn_ultimo_valor_umidade(p_id_zona) INTO v_umidade FROM dual;

  IF v_umidade < 30 THEN
    SELECT id_bomba INTO v_id_bomba
    FROM bombas
    WHERE id_zona = p_id_zona AND status = 'desligada' AND ativo = 1 AND ROWNUM = 1;

    IF v_id_bomba IS NOT NULL THEN
      UPDATETIME bombas SET status = 'ligada', data_atualizacao = CURRENT_DATETIME WHERE id_bomba = v_id_bomba;
      INSERT INTO logs_acao_bomba(id_log, id_bomba, data_hora, acao, id_usuario)
      VALUES (seq_logs_acao_bomba.NEXTVAL, v_id_bomba, CURRENT_DATETIME, 'ligar', NULL);
      COMMIT;
    END IF;
  END IF;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    NULL;
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END verificar_ligar_bomba_por_umidade;
CREATE OR REPLACE PROCEDURE desativar_usuario_com_historico(p_id_usuario IN INT) AS
BEGIN
  UPDATETIME usuarios SET ativo = 0, data_atualizacao = CURRENT_DATETIME WHERE id_usuario = p_id_usuario;
  INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora)
  VALUES (seq_historico_acoes_usuario.NEXTVAL, p_id_usuario, 'Usuário desativado via procedure', CURRENT_DATETIME);
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
CREATE OR REPLACE PROCEDURE registrar_alerta_umidade(
  p_id_zona IN INT,
  p_umidade IN INT,
  p_descricao IN VARCHAR
) AS
  v_id_alerta INT;
BEGIN
  INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao)
  VALUES (seq_alertas_umidade.NEXTVAL, p_id_zona, CURRENT_DATETIME, p_umidade, p_descricao, 0, CURRENT_DATETIME)
  RETURNING id_alerta INTO v_id_alerta;

  INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora)
  VALUES (seq_historico_acoes_usuario.NEXTVAL, NULL, 'Alerta de umidade registrado ID: ' || v_id_alerta, CURRENT_DATETIME);

  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
CREATE OR REPLACE PROCEDURE desligar_todas_bombas_zona(p_id_zona IN INT) AS
BEGIN
  UPDATETIME bombas SET status = 'desligada', data_atualizacao = CURRENT_DATETIME
  WHERE id_zona = p_id_zona AND ativo = 1 AND status <> 'desligada';
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;
CREATE OR REPLACE PROCEDURE inserir_usuario (
    p_nome IN VARCHAR,
    p_email IN VARCHAR,
    p_senha IN VARCHAR,
    p_tipo_usuario IN VARCHAR 
) AS
BEGIN
    INSERT INTO usuarios (
        id_usuario,
        nome,
        email,
        senha,
        tipo_usuario,
        ativo,
        data_criacao
    ) VALUES (
        seq_usuarios.NEXTVAL,
        p_nome,
        p_email,
        p_senha,
        p_tipo_usuario,
        1, 
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE; 
END inserir_usuario;
CREATE OR REPLACE PROCEDURE atualizar_usuario (
    p_id_usuario IN INT,
    p_nome IN VARCHAR,
    p_email IN VARCHAR,
    p_senha IN VARCHAR,
    p_tipo_usuario IN VARCHAR,
    p_ativo IN INT 
) AS
BEGIN
    UPDATETIME usuarios
    SET
        nome = p_nome,
        email = p_email,
        senha = p_senha,
        tipo_usuario = p_tipo_usuario,
        ativo = p_ativo,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_usuario = p_id_usuario;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_usuario;
CREATE OR REPLACE PROCEDURE excluir_usuario (
    p_id_usuario IN INT
 AS
BEGIN
    UPDATETIME usuarios
    SET
        ativo = 0,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_usuario = p_id_usuario;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_usuario;
CREATE OR REPLACE PROCEDURE inserir_usuario (
    p_nome IN VARCHAR,
    p_email IN VARCHAR,
    p_senha IN VARCHAR,
    p_tipo_usuario IN VARCHAR 
) AS
BEGIN
    INSERT INTO usuarios (
        id_usuario,
        nome,
        email,
        senha,
        tipo_usuario,
        ativo,
        data_criacao
    ) VALUES (
        seq_usuarios.NEXTVAL,
        p_nome,
        p_email,
        p_senha,
        p_tipo_usuario,
        1, 
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE; 
END inserir_usuario;
CREATE OR REPLACE PROCEDURE atualizar_usuario (
    p_id_usuario IN INT,
    p_nome IN VARCHAR,
    p_email IN VARCHAR,
    p_senha IN VARCHAR,
    p_tipo_usuario IN VARCHAR,
    p_ativo IN INT 
) AS
BEGIN
    UPDATETIME usuarios
    SET
        nome = p_nome,
        email = p_email,
        senha = p_senha,
        tipo_usuario = p_tipo_usuario,
        ativo = p_ativo,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_usuario = p_id_usuario;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_usuario;
CREATE OR REPLACE PROCEDURE excluir_usuario (
    p_id_usuario IN INT
) AS
BEGIN
    UPDATETIME usuarios
    SET
        ativo = 0,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_usuario = p_id_usuario;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_usuario;
CREATE OR REPLACE PROCEDURE inserir_propriedade (
    p_nome IN VARCHAR,
    p_id_usuario IN INT,
    p_id_estado IN INT,
    p_area_hectares IN INT
) AS
BEGIN
    INSERT INTO propriedades (
        id_propriedade,
        nome,
        id_usuario,
        id_estado,
        area_hectares,
        ativo,
        data_criacao
    ) VALUES (
        seq_propriedades.NEXTVAL,
        p_nome,
        p_id_usuario,
        p_id_estado,
        p_area_hectares,
        1,
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_propriedade;
CREATE OR REPLACE PROCEDURE atualizar_propriedade (
    p_id_propriedade IN INT,
    p_nome IN VARCHAR,
    p_id_usuario IN INT,
    p_id_estado IN INT,
    p_area_hectares IN INT,
    p_ativo IN INT
) AS
BEGIN
    UPDATETIME propriedades
    SET
        nome = p_nome,
        id_usuario = p_id_usuario,
        id_estado = p_id_estado,
        area_hectares = p_area_hectares,
        ativo = p_ativo,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_propriedade = p_id_propriedade;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_propriedade;
CREATE OR REPLACE PROCEDURE excluir_propriedade (
    p_id_propriedade IN INT
) AS
BEGIN
    UPDATETIME propriedades
    SET
        ativo = 0,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_propriedade = p_id_propriedade;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_propriedade;
CREATE OR REPLACE PROCEDURE inserir_zona (
    p_id_propriedade IN INT,
    p_nome IN VARCHAR,
    p_area_hectares IN INT
) AS
BEGIN
    INSERT INTO zonas (
        id_zona,
        id_propriedade,
        nome,
        area_hectares,
        ativo,
        data_criacao
    ) VALUES (
        seq_zonas.NEXTVAL,
        p_id_propriedade,
        p_nome,
        p_area_hectares,
        1,
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_zona;
CREATE OR REPLACE PROCEDURE atualizar_zona (
    p_id_zona IN INT,
    p_id_propriedade IN INT,
    p_nome IN VARCHAR,
    p_area_hectares IN INT,
    p_ativo IN INT
) AS
BEGIN
    UPDATETIME zonas
    SET
        id_propriedade = p_id_propriedade,
        nome = p_nome,
        area_hectares = p_area_hectares,
        ativo = p_ativo,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_zona = p_id_zona;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_zona;
CREATE OR REPLACE PROCEDURE excluir_zona (
    p_id_zona IN INT
) AS
BEGIN
    UPDATETIME zonas
    SET
        ativo = 0,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_zona = p_id_zona;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_zona;
CREATE OR REPLACE PROCEDURE inserir_sensor (
    p_id_zona IN INT,
    p_tipo_sensor IN VARCHAR,
    p_modelo IN VARCHAR,
    p_data_instalacao IN DATETIME
) AS
BEGIN
    INSERT INTO sensores (
        id_sensor,
        id_zona,
        tipo_sensor,
        modelo,
        ativo,
        data_instalacao,
        data_criacao
    ) VALUES (
        seq_sensores.NEXTVAL,
        p_id_zona,
        p_tipo_sensor,
        p_modelo,
        1,
        p_data_instalacao,
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_sensor;
CREATE OR REPLACE PROCEDURE atualizar_sensor (
    p_id_sensor IN INT,
    p_id_zona IN INT,
    p_tipo_sensor IN VARCHAR,
    p_modelo IN VARCHAR,
    p_ativo IN INT,
    p_data_instalacao IN DATETIME
) AS
BEGIN
    UPDATETIME sensores
    SET
        id_zona = p_id_zona,
        tipo_sensor = p_tipo_sensor,
        modelo = p_modelo,
        ativo = p_ativo,
        data_instalacao = p_data_instalacao,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_sensor = p_id_sensor;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_sensor;
CREATE OR REPLACE PROCEDURE excluir_sensor (
    p_id_sensor IN INT
) AS
BEGIN
    UPDATETIME sensores
    SET
        ativo = 0,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_sensor = p_id_sensor;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_sensor;
CREATE OR REPLACE PROCEDURE inserir_registro_sensor (
    p_id_sensor IN INT,
    p_data_hora IN DATETIME,
    p_valor IN INT
) AS
BEGIN
    INSERT INTO registros_sensor (
        id_registro,
        id_sensor,
        data_hora,
        valor
    ) VALUES (
        seq_registros_sensor.NEXTVAL,
        p_id_sensor,
        p_data_hora,
        p_valor
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_registro_sensor;
CREATE OR REPLACE PROCEDURE excluir_registro_sensor (
    p_id_registro IN INT
) AS
BEGIN
    DELETE FROM registros_sensor
    WHERE id_registro = p_id_registro;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_registro_sensor;
CREATE OR REPLACE PROCEDURE inserir_bomba (
    p_id_zona IN INT,
    p_modelo IN VARCHAR,
    p_status IN VARCHAR,
    p_data_instalacao IN DATETIME
) AS
BEGIN
    INSERT INTO bombas (
        id_bomba,
        id_zona,
        modelo,
        status,
        ativo,
        data_instalacao,
        data_criacao
    ) VALUES (
        seq_bombas.NEXTVAL,
        p_id_zona,
        p_modelo,
        p_status,
        1,
        p_data_instalacao,
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_bomba;
CREATE OR REPLACE PROCEDURE atualizar_bomba (
    p_id_bomba IN INT,
    p_id_zona IN INT,
    p_modelo IN VARCHAR,
    p_status IN VARCHAR,
    p_ativo IN INT,
    p_data_instalacao IN DATETIME
) AS
BEGIN
    UPDATETIME bombas
    SET
        id_zona = p_id_zona,
        modelo = p_modelo,
        status = p_status,
        ativo = p_ativo,
        data_instalacao = p_data_instalacao,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_bomba = p_id_bomba;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_bomba;
CREATE OR REPLACE PROCEDURE excluir_bomba (
    p_id_bomba IN INT
) AS
BEGIN
    UPDATETIME bombas
    SET
        ativo = 0,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_bomba = p_id_bomba;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_bomba;
CREATE OR REPLACE PROCEDURE inserir_log_acao_bomba (
    p_id_bomba IN INT,
    p_data_hora IN DATETIME,
    p_acao IN VARCHAR,
    p_id_usuario IN INT
) AS
BEGIN
    INSERT INTO logs_acao_bomba (
        id_log,
        id_bomba,
        data_hora,
        acao,
        id_usuario
    ) VALUES (
        seq_logs_acao_bomba.NEXTVAL,
        p_id_bomba,
        p_data_hora,
        p_acao,
        p_id_usuario
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_log_acao_bomba;
CREATE OR REPLACE PROCEDURE excluir_log_acao_bomba (
    p_id_log IN INT
) AS
BEGIN
    DELETE FROM logs_acao_bomba
    WHERE id_log = p_id_log;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_log_acao_bomba;
CREATE OR REPLACE PROCEDURE inserir_configuracao_zona (
    p_id_zona IN INT,
    p_limite_umidade_min IN INT,
    p_horario_inicio_irriga IN VARCHAR,
    p_horario_fim_irriga IN VARCHAR
) AS
BEGIN
    INSERT INTO configuracoes_zona (
        id_config,
        id_zona,
        limite_umidade_min,
        horario_inicio_irriga,
        horario_fim_irriga,
        ativo,
        data_criacao
    ) VALUES (
        seq_configuracoes_zona.NEXTVAL,
        p_id_zona,
        p_limite_umidade_min,
        p_horario_inicio_irriga,
        p_horario_fim_irriga,
        1,
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_configuracao_zona;
CREATE OR REPLACE PROCEDURE atualizar_configuracao_zona (
    p_id_config IN INT,
    p_id_zona IN INT,
    p_limite_umidade_min IN INT,
    p_horario_inicio_irriga IN VARCHAR,
    p_horario_fim_irriga IN VARCHAR,
    p_ativo IN INT
) AS
BEGIN
    UPDATETIME configuracoes_zona
    SET
        id_zona = p_id_zona,
        limite_umidade_min = p_limite_umidade_min,
        horario_inicio_irriga = p_horario_inicio_irriga,
        horario_fim_irriga = p_horario_fim_irriga,
        ativo = p_ativo,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_config = p_id_config;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_configuracao_zona;
CREATE OR REPLACE PROCEDURE excluir_configuracao_zona (
    p_id_config IN INT
) AS
BEGIN
    UPDATETIME configuracoes_zona
    SET
        ativo = 0,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_config = p_id_config;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_configuracao_zona;
CREATE OR REPLACE PROCEDURE inserir_alerta_umidade (
    p_id_zona IN INT,
    p_data_hora IN DATETIME,
    p_umidade_atual IN INT,
    p_descricao IN VARCHAR
) AS
BEGIN
    INSERT INTO alertas_umidade (
        id_alerta,
        id_zona,
        data_hora,
        umidade_atual,
        descricao,
        resolvido,
        data_criacao
    ) VALUES (
        seq_alertas_umidade.NEXTVAL,
        p_id_zona,
        p_data_hora,
        p_umidade_atual,
        p_descricao,
        0,
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_alerta_umidade;
CREATE OR REPLACE PROCEDURE atualizar_alerta_umidade (
    p_id_alerta IN INT,
    p_resolvido IN INT
) AS
BEGIN
    UPDATETIME alertas_umidade
    SET
        resolvido = p_resolvido,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_alerta = p_id_alerta;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END atualizar_alerta_umidade;
CREATE OR REPLACE PROCEDURE excluir_alerta_umidade (
    p_id_alerta IN INT
) AS
BEGIN
    UPDATETIME alertas_umidade
    SET
        resolvido = 1,
        data_atualizacao = CURRENT_DATETIME
    WHERE id_alerta = p_id_alerta;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END excluir_alerta_umidade;
CREATE OR REPLACE PROCEDURE inserir_historico_acao_usuario (
    p_id_usuario IN INT,
    p_descricao IN VARCHAR
) AS
BEGIN
    INSERT INTO historico_acoes_usuario (
        id_acao,
        id_usuario,
        descricao,
        data_hora
    ) VALUES (
        seq_historico_acoes_usuario.NEXTVAL,
        p_id_usuario,
        p_descricao,
        CURRENT_DATETIME
    );
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END inserir_historico_acao_usuario;
CREATE OR REPLACE FUNCTION fn_media_umidade_por_zona (
    p_id_zona IN INT,
    p_dias_periodo IN INT
) RETURN INT
AS
    v_media_umidade DECIMAL(5,2);
BEGIN
    SELECT NVL(AVG(rs.valor), 0)
    INTO v_media_umidade
    FROM registros_sensor rs
    JOIN sensores s ON rs.id_sensor = s.id_sensor
    WHERE s.id_zona = p_id_zona
      AND rs.data_hora >= NOW() - p_dias_periodo
      AND s.tipo_sensor = 'umidade';

    RETURN v_media_umidade;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RAISE;
END fn_media_umidade_por_zona;
CREATE OR REPLACE FUNCTION fn_total_alertas_nao_resolvidos (
    p_id_propriedade IN INT
) RETURN INT
AS
    v_total_alertas INT;
BEGIN
    SELECT COUNT(*)
    INTO v_total_alertas
    FROM alertas_umidade au
    JOIN zonas z ON au.id_zona = z.id_zona
    JOIN propriedades p ON z.id_propriedade = p.id_propriedade
    WHERE p.id_propriedade = p_id_propriedade
      AND au.resolvido = 0;

    RETURN NVL(v_total_alertas, 0);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RAISE;
END fn_total_alertas_nao_resolvidos;
CREATE OR REPLACE FUNCTION fn_soma_area_zonas_por_propriedade (
    p_id_propriedade IN INT
) RETURN INT
AS
    v_area_total DECIMAL(10,2);
BEGIN
    SELECT NVL(SUM(area_hectares),0)
    INTO v_area_total
    FROM zonas
    WHERE id_propriedade = p_id_propriedade
      AND ativo = 1;

    RETURN v_area_total;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RAISE;
END fn_soma_area_zonas_por_propriedade;
CREATE OR REPLACE FUNCTION fn_conta_bombas_ativas (
    p_id_zona IN INT
) RETURN INT
AS
    v_total_bombas INT;
BEGIN
    SELECT COUNT(*)
    INTO v_total_bombas
    FROM bombas
    WHERE id_zona = p_id_zona
      AND ativo = 1
      AND status = 'ligada';

    RETURN NVL(v_total_bombas, 0);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RAISE;
END fn_conta_bombas_ativas;
CREATE OR REPLACE FUNCTION fn_ultimo_valor_umidade (
    p_id_zona IN INT
) RETURN INT
AS
    v_ultimo_valor DECIMAL(5,2);
BEGIN
    SELECT valor
    INTO v_ultimo_valor
    FROM registros_sensor rs
    JOIN sensores s ON rs.id_sensor = s.id_sensor
    WHERE s.id_zona = p_id_zona
      AND s.tipo_sensor = 'umidade'
    ORDER BY rs.data_hora DESC
    FETCH FIRST 1 ROWS ONLY;

    RETURN v_ultimo_valor;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RAISE;
END fn_ultimo_valor_umidade;
DECLARE
    CURSOR cur_alertas IS
        SELECT p.id_propriedade,
               p.nome AS nome_propriedade,
               COUNT(au.id_alerta) AS total_alertas,
               fn_media_umidade_por_zona(z.id_zona, 7) AS media_umidade_7dias
          FROM propriedades p
          JOIN zonas z ON p.id_propriedade = z.id_propriedade
          JOIN alertas_umidade au ON z.id_zona = au.id_zona
         WHERE au.resolvido = 0
         GROUP BY p.id_propriedade, p.nome, z.id_zona
         HAVING COUNT(au.id_alerta) > 0
         ORDER BY total_alertas DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Alerta | Propriedade | Total Alertas | Média Umidade Últimos 7 dias');
    FOR rec IN cur_alertas LOOP
        DBMS_OUTPUT.PUT_LINE(
            rec.id_propriedade || ' | ' ||
            rec.nome_propriedade || ' | ' ||
            rec.total_alertas || ' | ' ||
            TO_CHAR(rec.media_umidade_7dias, '90.00')
        );
    END LOOP;
END;
DECLARE
    CURSOR cur_zonas IS
        SELECT z.id_zona, z.nome, z.area_hectares, c.limite_umidade_min,
               fn_media_umidade_por_zona(z.id_zona, 5) AS media_umidade_5dias
          FROM zonas z
          JOIN configuracoes_zona c ON z.id_zona = c.id_zona
         WHERE z.area_hectares > 10
           AND c.ativo = 1;
BEGIN
    FOR rec IN cur_zonas LOOP
        IF rec.media_umidade_5dias < rec.limite_umidade_min THEN
            DBMS_OUTPUT.PUT_LINE('Alerta: Zona ' || rec.nome || 
                                 ' (ID: ' || rec.id_zona || ') está com umidade abaixo do limite!');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Zona ' || rec.nome || 
                                 ' (ID: ' || rec.id_zona || ') com umidade normal.');
        END IF;
    END LOOP;
END;
DECLARE
    CURSOR cur_bombas_manutencao IS
        SELECT b.id_bomba, b.modelo, COUNT(l.id_log) AS total_acionamentos
          FROM bombas b
          LEFT JOIN logs_acao_bomba l ON b.id_bomba = l.id_bomba
         WHERE b.status = 'manutencao'
         GROUP BY b.id_bomba, b.modelo
         ORDER BY total_acionamentos DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bombas em manutenção e total de acionamentos:');
    FOR rec IN cur_bombas_manutencao LOOP
        DBMS_OUTPUT.PUT_LINE('Bomba ID: ' || rec.id_bomba || ', Modelo: ' || rec.modelo || ', Total Acionamentos: ' || rec.total_acionamentos);
    END LOOP;
END;
DECLARE
    CURSOR cur_propriedades_zonas IS
        SELECT p.id_propriedade, p.nome, COUNT(z.id_zona) AS total_zonas, SUM(z.area_hectares) AS area_total
          FROM propriedades p
          JOIN zonas z ON p.id_propriedade = z.id_propriedade
         GROUP BY p.id_propriedade, p.nome
        HAVING COUNT(z.id_zona) > 3
        ORDER BY area_total DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Propriedades com mais de 3 zonas e área total:');
    FOR rec IN cur_propriedades_zonas LOOP
        DBMS_OUTPUT.PUT_LINE('Propriedade ID: ' || rec.id_propriedade || ', Nome: ' || rec.nome || ', Zonas: ' || rec.total_zonas || ', Área total: ' || TO_CHAR(rec.area_total, '999999.99') || ' ha');
    END LOOP;
END;
DECLARE
    CURSOR cur_usuarios_ativos IS
        SELECT u.id_usuario, u.nome, COUNT(h.id_acao) AS total_acoes
          FROM usuarios u
          LEFT JOIN historico_acoes_usuario h ON u.id_usuario = h.id_usuario
         WHERE u.ativo = 1
         GROUP BY u.id_usuario, u.nome
         ORDER BY total_acoes DESC;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Usuários ativos e quantidade de ações registradas:');
    FOR rec IN cur_usuarios_ativos LOOP
        DBMS_OUTPUT.PUT_LINE('Usuário ID: ' || rec.id_usuario || ', Nome: ' || rec.nome || ', Ações: ' || rec.total_acoes);
    END LOOP;
END;
DECLARE
    CURSOR cur_alertas IS
        SELECT id_alerta, id_zona, data_hora, umidade_atual, descricao
        FROM alertas_umidade
        WHERE resolvido = 0
        ORDER BY data_hora DESC;

    v_id_alerta alertas_umidade.id_alerta%TYPE;
    v_id_zona alertas_umidade.id_zona%TYPE;
    v_data_hora alertas_umidade.data_hora%TYPE;
    v_umidade_atual alertas_umidade.umidade_atual%TYPE;
    v_descricao alertas_umidade.descricao%TYPE;
BEGIN
    OPEN cur_alertas;
    LOOP
        FETCH cur_alertas INTO v_id_alerta, v_id_zona, v_data_hora, v_umidade_atual, v_descricao;
        EXIT WHEN cur_alertas%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Alerta ID: ' || v_id_alerta || 
                             ', Zona ID: ' || v_id_zona || 
                             ', Data: ' || TO_CHAR(v_data_hora, 'DDMMYYYY HH24:MI:SS') ||
                             ', Umidade: ' || v_umidade_atual || '%' ||
                             ', Descrição: ' || v_descricao);
    END LOOP;
    CLOSE cur_alertas;
END;
DECLARE
    CURSOR cur_usuarios IS
        SELECT u.id_usuario, u.nome, COUNT(h.id_acao) AS total_acoes
        FROM usuarios u
        LEFT JOIN historico_acoes_usuario h ON u.id_usuario = h.id_usuario
        WHERE u.ativo = 1
        GROUP BY u.id_usuario, u.nome
        ORDER BY total_acoes DESC;

    v_id_usuario usuarios.id_usuario%TYPE;
    v_nome usuarios.nome%TYPE;
    v_total_acoes INT;
BEGIN
    OPEN cur_usuarios;
    LOOP
        FETCH cur_usuarios INTO v_id_usuario, v_nome, v_total_acoes;
        EXIT WHEN cur_usuarios%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Usuário ID: ' || v_id_usuario || ', Nome: ' || v_nome || ', Ações: ' || v_total_acoes);
    END LOOP;
    CLOSE cur_usuarios;
END;
DECLARE
    CURSOR cur_bombas IS
        SELECT b.id_bomba, b.modelo, COUNT(l.id_log) AS total_acionamentos
        FROM bombas b
        LEFT JOIN logs_acao_bomba l ON b.id_bomba = l.id_bomba
        WHERE b.status = 'manutencao'
        GROUP BY b.id_bomba, b.modelo
        ORDER BY total_acionamentos DESC;

    v_id_bomba bombas.id_bomba%TYPE;
    v_modelo bombas.modelo%TYPE;
    v_total_acionamentos INT;
BEGIN
    OPEN cur_bombas;
    LOOP
        FETCH cur_bombas INTO v_id_bomba, v_modelo, v_total_acionamentos;
        EXIT WHEN cur_bombas%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Bomba ID: ' || v_id_bomba || ', Modelo: ' || v_modelo || ', Acionamentos: ' || v_total_acionamentos);
    END LOOP;
    CLOSE cur_bombas;
END;
DECLARE
    CURSOR cur_zonas IS
        SELECT z.id_zona, z.nome, z.area_hectares
        FROM zonas z
        WHERE z.area_hectares > 10
        ORDER BY z.area_hectares DESC;

    v_id_zona zonas.id_zona%TYPE;
    v_nome zonas.nome%TYPE;
    v_area zonas.area_hectares%TYPE;
BEGIN
    OPEN cur_zonas;
    LOOP
        FETCH cur_zonas INTO v_id_zona, v_nome, v_area;
        EXIT WHEN cur_zonas%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Zona ID: ' || v_id_zona || ', Nome: ' || v_nome || ', Área: ' || TO_CHAR(v_area, '999999.99') || ' ha');
    END LOOP;
    CLOSE cur_zonas;
END;
DECLARE
    CURSOR cur_propriedades IS
        SELECT p.id_propriedade, p.nome, COUNT(z.id_zona) AS total_zonas, SUM(z.area_hectares) AS area_total
        FROM propriedades p
        JOIN zonas z ON p.id_propriedade = z.id_propriedade
        GROUP BY p.id_propriedade, p.nome
        HAVING COUNT(z.id_zona) > 3
        ORDER BY area_total DESC;

    v_id_propriedade propriedades.id_propriedade%TYPE;
    v_nome propriedades.nome%TYPE;
    v_total_zonas INT;
    v_area_total INT;
BEGIN
    OPEN cur_propriedades;
    LOOP
        FETCH cur_propriedades INTO v_id_propriedade, v_nome, v_total_zonas, v_area_total;
        EXIT WHEN cur_propriedades%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Propriedade ID: ' || v_id_propriedade || ', Nome: ' || v_nome || ', Zonas: ' || v_total_zonas || ', Área Total: ' || TO_CHAR(v_area_total, '999999.99') || ' ha');
    END LOOP;
    CLOSE cur_propriedades;
END;
SELECT p.id_propriedade, p.nome, COUNT(z.id_zona) AS total_zonas, SUM(z.area_hectares) AS area_total
FROM propriedades p
JOIN zonas z ON p.id_propriedade = z.id_propriedade
GROUP BY p.id_propriedade, p.nome
HAVING SUM(z.area_hectares) > 50
ORDER BY area_total DESC;




SELECT s.tipo_sensor, s.id_zona, COUNT(*) AS total_sensores_ativos
FROM sensores s
WHERE s.ativo = 1
GROUP BY s.tipo_sensor, s.id_zona
ORDER BY s.tipo_sensor, total_sensores_ativos DESC;




SELECT b.id_bomba, b.modelo, b.status, COUNT(l.id_log) AS total_acionamentos
FROM bombas b
LEFT JOIN logs_acao_bomba l ON b.id_bomba = l.id_bomba
GROUP BY b.id_bomba, b.modelo, b.status
ORDER BY total_acionamentos DESC;




SELECT u.id_usuario, u.nome, COUNT(h.id_acao) AS total_acoes_30d
FROM usuarios u
LEFT JOIN historico_acoes_usuario h ON u.id_usuario = h.id_usuario AND h.data_hora >= NOW() - 30
WHERE u.ativo = 1
GROUP BY u.id_usuario, u.nome
ORDER BY total_acoes_30d DESC;




SELECT au.id_alerta, z.nome AS nome_zona, au.data_hora, au.umidade_atual, au.descricao
FROM alertas_umidade au
JOIN zonas z ON au.id_zona = z.id_zona
WHERE au.resolvido = 0
ORDER BY au.data_hora DESC;

-- =============== DADOS ===============

INSERT INTO estados (id_estado, nome, sigla) VALUES (1, 'Estado_1', 'SP');
INSERT INTO estados (id_estado, nome, sigla) VALUES (2, 'Estado_2', 'RJ');
INSERT INTO estados (id_estado, nome, sigla) VALUES (3, 'Estado_3', 'MG');
INSERT INTO estados (id_estado, nome, sigla) VALUES (4, 'Estado_4', 'BA');
INSERT INTO estados (id_estado, nome, sigla) VALUES (5, 'Estado_5', 'ES');
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (1, 'Usuário_1', 'usuario1@exemplo.com', 'senhaHash1', 'produtor', 1, CURRENT_TIMESTAMP);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (2, 'Usuário_2', 'usuario2@exemplo.com', 'senhaHash2', 'produtor', 1, CURRENT_TIMESTAMP);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (3, 'Usuário_3', 'usuario3@exemplo.com', 'senhaHash3', 'produtor', 1, CURRENT_TIMESTAMP);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (4, 'Usuário_4', 'usuario4@exemplo.com', 'senhaHash4', 'produtor', 1, CURRENT_TIMESTAMP);
INSERT INTO usuarios (id_usuario, nome, email, senha, tipo_usuario, ativo, data_criacao) VALUES (5, 'Usuário_5', 'usuario5@exemplo.com', 'senhaHash5', 'produtor', 1, CURRENT_TIMESTAMP);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (1, 'Propriedade_1', 1, 1, 11.0, 1, CURRENT_TIMESTAMP);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (2, 'Propriedade_2', 2, 2, 12.0, 1, CURRENT_TIMESTAMP);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (3, 'Propriedade_3', 3, 3, 13.0, 1, CURRENT_TIMESTAMP);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (4, 'Propriedade_4', 4, 4, 14.0, 1, CURRENT_TIMESTAMP);
INSERT INTO propriedades (id_propriedade, nome, id_usuario, id_estado, area_hectares, ativo, data_criacao) VALUES (5, 'Propriedade_5', 5, 5, 15.0, 1, CURRENT_TIMESTAMP);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (1, 1, 'Zona_1', 6.0, 1, CURRENT_TIMESTAMP);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (2, 2, 'Zona_2', 7.0, 1, CURRENT_TIMESTAMP);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (3, 3, 'Zona_3', 8.0, 1, CURRENT_TIMESTAMP);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (4, 4, 'Zona_4', 9.0, 1, CURRENT_TIMESTAMP);
INSERT INTO zonas (id_zona, id_propriedade, nome, area_hectares, ativo, data_criacao) VALUES (5, 5, 'Zona_5', 10.0, 1, CURRENT_TIMESTAMP);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (1, 1, 'umidade', 'Sensor_Modelo_1', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (2, 2, 'umidade', 'Sensor_Modelo_2', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (3, 3, 'umidade', 'Sensor_Modelo_3', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (4, 4, 'umidade', 'Sensor_Modelo_4', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO sensores (id_sensor, id_zona, tipo_sensor, modelo, ativo, data_instalacao, data_criacao) VALUES (5, 5, 'umidade', 'Sensor_Modelo_5', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (1, 1, SYSTIMESTAMP, 56.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (2, 2, SYSTIMESTAMP, 57.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (3, 3, SYSTIMESTAMP, 58.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (4, 4, SYSTIMESTAMP, 59.5);
INSERT INTO registros_sensor (id_registro, id_sensor, data_hora, valor) VALUES (5, 5, SYSTIMESTAMP, 60.5);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (1, 1, 'Modelo Bomba 1', 'ligada', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (2, 2, 'Modelo Bomba 2', 'ligada', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (3, 3, 'Modelo Bomba 3', 'ligada', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (4, 4, 'Modelo Bomba 4', 'ligada', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO bombas (id_bomba, id_zona, modelo, status, ativo, data_instalacao, data_criacao) VALUES (5, 5, 'Modelo Bomba 5', 'ligada', 1, SYSDATE, CURRENT_TIMESTAMP);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (1, 1, SYSTIMESTAMP, 'ligar', 1);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (2, 2, SYSTIMESTAMP, 'ligar', 2);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (3, 3, SYSTIMESTAMP, 'ligar', 3);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (4, 4, SYSTIMESTAMP, 'ligar', 4);
INSERT INTO logs_acao_bomba (id_log, id_bomba, data_hora, acao, id_usuario) VALUES (5, 5, SYSTIMESTAMP, 'ligar', 5);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (1, 1, 31.0, '06:01', '08:01', 1, CURRENT_TIMESTAMP);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (2, 2, 32.0, '06:02', '08:02', 1, CURRENT_TIMESTAMP);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (3, 3, 33.0, '06:03', '08:03', 1, CURRENT_TIMESTAMP);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (4, 4, 34.0, '06:04', '08:04', 1, CURRENT_TIMESTAMP);
INSERT INTO configuracoes_zona (id_config, id_zona, limite_umidade_min, horario_inicio_irriga, horario_fim_irriga, ativo, data_criacao) VALUES (5, 5, 35.0, '06:05', '08:05', 1, CURRENT_TIMESTAMP);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (1, 1, SYSTIMESTAMP, 21.0, 'Alerta gerado automaticamente', 0, CURRENT_TIMESTAMP);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (2, 2, SYSTIMESTAMP, 22.0, 'Alerta gerado automaticamente', 0, CURRENT_TIMESTAMP);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (3, 3, SYSTIMESTAMP, 23.0, 'Alerta gerado automaticamente', 0, CURRENT_TIMESTAMP);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (4, 4, SYSTIMESTAMP, 24.0, 'Alerta gerado automaticamente', 0, CURRENT_TIMESTAMP);
INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao) VALUES (5, 5, SYSTIMESTAMP, 25.0, 'Alerta gerado automaticamente', 0, CURRENT_TIMESTAMP);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (1, 1, 'Usuário realizou ação 1', CURRENT_TIMESTAMP);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (2, 2, 'Usuário realizou ação 2', CURRENT_TIMESTAMP);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (3, 3, 'Usuário realizou ação 3', CURRENT_TIMESTAMP);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (4, 4, 'Usuário realizou ação 4', CURRENT_TIMESTAMP);
INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora) VALUES (5, 5, 'Usuário realizou ação 5', CURRENT_TIMESTAMP);
      INSERT INTO logs_acao_bomba(id_log, id_bomba, data_hora, acao, id_usuario)
  INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora)
  INSERT INTO alertas_umidade (id_alerta, id_zona, data_hora, umidade_atual, descricao, resolvido, data_criacao)
  INSERT INTO historico_acoes_usuario (id_acao, id_usuario, descricao, data_hora)
    INSERT INTO usuarios (
    INSERT INTO usuarios (
    INSERT INTO propriedades (
    INSERT INTO zonas (
    INSERT INTO sensores (
    INSERT INTO registros_sensor (
    INSERT INTO bombas (
    INSERT INTO logs_acao_bomba (
    INSERT INTO configuracoes_zona (
    INSERT INTO alertas_umidade (
    INSERT INTO historico_acoes_usuario (