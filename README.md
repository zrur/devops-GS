# AquaMind – Sistema Inteligente de Irrigação

AquaMind é um sistema inteligente de irrigação voltado para pequenos e médios produtores rurais que desejam otimizar o uso de água em suas plantações. Ele combina sensores de umidade conectados via IoT, uma API backend em Java Spring Boot e um dashboard web para fornecer monitoramento em tempo real das condições do solo, alertas automáticos e controle remoto de bombas de irrigação. O objetivo é reduzir o desperdício de água, melhorar a eficiência na irrigação e permitir que o produtor tome decisões informadas com base em dados históricos e em tempo real.

AquaMind é uma solução completa para pequenos e médios produtores rurais que desejam monitorar e automatizar a irrigação de suas propriedades,  
especialmente em regiões com escassez hídrica. Com integração IoT, backend Java Spring Boot, dashboard web, API REST, segurança JWT e containerização,  
o AquaMind oferece eficiência no uso da água, histórico de dados e controle remoto das bombas.

---

## 🌎 Visão Geral

O AquaMind foi concebido para enfrentar os desafios da seca e escassez hídrica em áreas agrícolas, oferecendo:

- Monitoramento em tempo real da umidade do solo por meio de sensores IoT (ESP32 + sensores capacitivos).
- Dashboard Web para visualizar gráficos históricos de umidade, alertas e status das zonas de plantio.
- Controle remoto de irrigação: acione bombas manualmente ou de forma automática quando a umidade estiver abaixo de um limiar configurado.
- Segurança JWT: autenticação de usuários e proteção de endpoints.
- API RESTful com documentação interativa via Swagger.
- Estrutura modular (Propriedade → Zona → Sensor → Leitura → Irrigação), permitindo futuras expansões (integração com previsão climática, novos sensores, relatórios avançados).

Este projeto faz parte do desafio Java Advanced da FIAP Global Solution 2025 e visa demonstrar boas práticas de arquitetura, design de API e containerização.

---

## 🚀 Principais Funcionalidades

1. **Autenticação e Autorização**
   - Cadastro e login de usuários (Produtores) via JWT.
   - Papéis (“ROLE_USER” e “ROLE_ADMIN”) para controlar acessos.

2. **CRUD de Propriedades Agrícolas**
   - Cada usuário pode cadastrar múltiplas propriedades (fazendas).
   - Informações básicas: nome, endereço, coordenadas.

3. **Gerenciamento de Zonas de Plantio**
   - Cada propriedade possui várias zonas (áreas) de cultivo.
   - Definição de “umidade alvo” para automação.

4. **Cadastro de Sensores**
   - Associe sensores (ESP32) a uma zona específica.
   - Cada sensor publica leituras de umidade via MQTT/HTTP → Node-RED → API.

5. **Registro de Leituras de Umidade**
   - Persistência das leituras no banco relacional (H2/PostgreSQL/Oracle).
   - Marcação de alertas quando o valor de umidade < umidade alvo da zona.

6. **Histórico de Irrigação**
   - Registrar cada acionamento de irrigação (manualmente ou automático).
   - Informar data/hora de início, fim e volume de água bombeado (litros).

7. **Dashboard Web (Swagger / futuros Web UI)**
   - Documentação interativa (Swagger UI) para testar e visualizar todos os endpoints.
   - Em versões futuras, um front-end em React ou Next.js pode consumir esta API para exibir gráficos dinâmicos.

8. **Tratamento Global de Exceções**
   - Erros padronizados (404 Not Found, 400 Bad Request, 401 Unauthorized) via GlobalExceptionHandler.

---

## 🔗 Link para Documentação Interativa (Swagger UI)

Assim que a aplicação estiver rodando (porta 8080 por padrão), abra no navegador:

> **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

Lá você encontrará todos os endpoints agrupados por controller, poderá preencher parâmetros, clicar em **Try it out** e ver as respostas (com exemplo de cURL, payloads, headers etc.).  


Para endpoints protegidos, clique em **Authorize** (ícone de cadeado no canto superior direito) e insira:

#### **Bearer <seu_token_JWT_obtido_no_login>**

---

## 🏗️ Arquitetura e Estrutura de Pastas

```

src/
├── main/
│   ├── java/
│   │   └── br/
│   │       └── com/
│   │           └── fiap/
│   │               └── aquamind/
│   │                   ├── AquaMindApplication.java
│   │                   │   └── • Classe principal do Spring Boot
│   │                   │
│   │                   ├── config/
│   │                   │   ├── CorsConfig.java
│   │                   │   │   └── • Configuração global de CORS
│   │                   │   │
│   │                   │   ├── SecurityConfig.java
│   │                   │   │   └── • Configurações de Spring Security (JWT, roles, etc.)
│   │                   │   │
│   │                   │   └── SwaggerConfig.java
│   │                   │       └── • Customização da documentação OpenAPI (Swagger)
│   │                   │
│   │                   ├── controller/
│   │                   │   ├── AlertaUmidadeController.java
│   │                   │   │   └── • CRUD de Alertas de Umidade (usa AlertaUmidadeDTO)
│   │                   │   │
│   │                   │   ├── AuthController.java
│   │                   │   │   └── • Endpoints de login e registro (JWT)
│   │                   │   │
│   │                   │   ├── BombaController.java
│   │                   │   │   └── • CRUD de Bombas (usa BombaDTO)
│   │                   │   │
│   │                   │   ├── ConfiguracaoZonaController.java
│   │                   │   │   └── • CRUD de Configurações de Zona (usa ConfiguracaoZonaDTO)
│   │                   │   │
│   │                   │   ├── EstadoController.java
│   │                   │   │   └── • CRUD de Estados (usa EstadoDTO)
│   │                   │   │
│   │                   │   ├── HistoricoAcaoUsuarioController.java
│   │                   │   │   └── • CRUD de Histórico de Ações de Usuário (usa HistoricoAcaoUsuarioDTO)
│   │                   │   │
│   │                   │   ├── IrrigacaoController.java
│   │                   │   │   └── • CRUD de Irrigações (usa IrrigacaoDTO)
│   │                   │   │
│   │                   │   ├── LogAcaoBombaController.java
│   │                   │   │   └── • CRUD de Logs de Ação de Bomba (usa LogAcaoBombaDTO)
│   │                   │   │
│   │                   │   ├── PropriedadeController.java
│   │                   │   │   └── • CRUD de Propriedades (usa PropriedadeDTO)
│   │                   │   │
│   │                   │   ├── RegistroSensorController.java
│   │                   │   │   └── • CRUD de Leituras de Sensor (usa RegistroSensorDTO)
│   │                   │   │
│   │                   │   ├── SensorController.java
│   │                   │   │   └── • CRUD de Sensores (usa SensorDTO)
│   │                   │   │
│   │                   │   ├── UsuarioController.java
│   │                   │   │   └── • CRUD de Usuários (usa UsuarioDTO)
│   │                   │   │
│   │                   │   └── ZonaController.java
│   │                   │       └── • CRUD de Zonas (usa ZonaDTO)
│   │                   │
│   │                   ├── dto/
│   │                   │   ├── AlertaUmidadeDTO.java
│   │                   │   ├── BombaDTO.java
│   │                   │   ├── ConfiguracaoZonaDTO.java
│   │                   │   ├── EstadoDTO.java
│   │                   │   ├── HistoricoAcaoUsuarioDTO.java
│   │                   │   ├── IrrigacaoDTO.java
│   │                   │   ├── LogAcaoBombaDTO.java
│   │                   │   ├── PropriedadeDTO.java
│   │                   │   ├── RegistroSensorDTO.java
│   │                   │   ├── SensorDTO.java
│   │                   │   ├── UsuarioDTO.java
│   │                   │   └── ZonaDTO.java
│   │                   │       └── • Cada DTO converte entre entidade e JSON
│   │                   │
│   │                   ├── exception/
│   │                   │   ├── BadRequestException.java
│   │                   │   │   └── • Exceção para requisições inválidas (HTTP 400)
│   │                   │   │
│   │                   │   ├── ErrorResponse.java
│   │                   │   │   └── • Formato padrão de resposta de erro
│   │                   │   │
│   │                   │   ├── GlobalExceptionHandler.java
│   │                   │   │   └── • Trata exceções globalmente e retorna JSON adequado
│   │                   │   │
│   │                   │   └── ResourceNotFoundException.java
│   │                   │       └── • Exceção para recurso não encontrado (HTTP 404)
│   │                   │
│   │                   ├── model/
│   │                   │   ├── AlertaUmidade.java
│   │                   │   ├── Bomba.java
│   │                   │   ├── ConfiguracaoZona.java
│   │                   │   ├── Estado.java
│   │                   │   ├── HistoricoAcaoUsuario.java
│   │                   │   ├── Irrigacao.java
│   │                   │   ├── LogAcaoBomba.java
│   │                   │   ├── Propriedade.java
│   │                   │   ├── RegistroSensor.java
│   │                   │   ├── Sensor.java
│   │                   │   ├── Usuario.java
│   │                   │   └── Zona.java
│   │                   │       └── • Entidades JPA com anotações @Entity, @OneToMany, etc.
│   │                   │
│   │                   ├── repository/
│   │                   │   ├── AlertaUmidadeRepository.java
│   │                   │   ├── BombaRepository.java
│   │                   │   ├── ConfiguracaoZonaRepository.java
│   │                   │   ├── EstadoRepository.java
│   │                   │   ├── HistoricoAcaoUsuarioRepository.java
│   │                   │   ├── IrrigacaoRepository.java
│   │                   │   ├── LogAcaoBombaRepository.java
│   │                   │   ├── PropriedadeRepository.java
│   │                   │   ├── RegistroSensorRepository.java
│   │                   │   ├── SensorRepository.java
│   │                   │   ├── UsuarioRepository.java
│   │                   │   └── ZonaRepository.java
│   │                   │       └── • Interfaces estendendo JpaRepository<Entidade, Long>
│   │                   │
│   │                   ├── security/
│   │                   │   ├── CustomUserDetailsService.java
│   │                   │   │   └── • Carrega usuário (UserDetails) para Spring Security
│   │                   │   │
│   │                   │   ├── JwtAuthenticationFilter.java
│   │                   │   │   └── • Filtro que extrai/valida JWT em cada requisição
│   │                   │   │
│   │                   │   └── JwtUtil.java
│   │                   │       └── • Utilitário para gerar/parsear/validar tokens JWT
│   │                   │
│   │                   └── service/
│   │                       ├── AlertaUmidadeService.java
│   │                       ├── AuthService.java
│   │                       ├── BombaService.java
│   │                       ├── ConfiguracaoZonaService.java
│   │                       ├── EstadoService.java
│   │                       ├── HistoricoAcaoUsuarioService.java
│   │                       ├── IrrigacaoService.java
│   │                       ├── LogAcaoBombaService.java
│   │                       ├── PropriedadeService.java
│   │                       ├── RegistroSensorService.java
│   │                       ├── SensorService.java
│   │                       ├── UsuarioService.java
│   │                       └── ZonaService.java
│   │                           └── • Cada serviço implementa a lógica de negócio (CRUD, validações, etc.)
│   │
│   └── resources/
│       ├── static/                    
│       ├── templates/                 
│       ├── application.properties     
│      
└── test/
└── java/
└── br/
└── com/
└── fiap/
└── aquamind/
├── controller/   ── • Testes de integração dos endpoints
├── service/      ── • Testes unitários dos serviços
└── repository/   ── • Testes de repositório (H2)

```


---

## 📦 Entidades de Domínio
**Usuario**
- **Campos:** `id`, `nome`, `email`, `senha (BCrypt)`, `perfis (List<String>)`, `propriedades (List<Propriedade>)`.
- **Relacionamentos:** 1:N para **Propriedade**.

**Propriedade**
- **Campos:** `id`, `nome`, `endereco`, `usuario (N:1)`, `zonas (List<Zona>)`.
- **Relacionamentos:** N:1 para **Usuario**, 1:N para **Zona**.

**Zona**
- **Campos:** `id`, `nome`, `umidadeAlvo`, `propriedade (N:1)`, `sensores (List<Sensor>)`, `irrigacoes (List<Irrigacao>)`.
- **Relacionamentos:** N:1 para **Propriedade**, 1:N para **Sensor** e **Irrigacao**.

**Sensor**
- **Campos:** `id`, `tipoSensor`, `modelo`, `zona (N:1)`, `leituras (List<Leitura>)`.
- **Relacionamentos:** N:1 para **Zona**, 1:N para **Leitura**.

**Leitura**
- **Campos:** `id`, `sensor (N:1)`, `umidade (Double)`, `timestamp (LocalDateTime)`, `alerta (Boolean)`.
- **Relacionamentos:** N:1 para **Sensor**.

**Irrigacao**
- **Campos:** `id`, `zona (N:1)`, `dataHoraInicio`, `dataHoraFim`, `volumeAgua (Double)`.
- **Relacionamentos:** N:1 para **Zona**.

---

## 💻 Tecnologias Utilizadas
- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.5.0
   - Módulos: Web, Data JPA, Security, Validation
- **Banco de Dados:** H2 (em memória) → fácil setup para testes.
- **Persistência:** Spring Data JPA (Hibernate)
- **Autenticação:** JWT (io.jsonwebtoken:jjwt) + Spring Security
- **Documentação:** SpringDoc OpenAPI (Swagger UI)
- **Validação:** Bean Validation (jakarta.validation)
- **Lombok:** Redução de boilerplate (getters, setters, construtores)
- **MQTT / Node-RED:** Processamento de leituras IoT
- **Containerização (Opcional):** Docker & Docker Compose
- **Testes:** JUnit 5 + Spring Boot Test + Spring Security Test

---

## 📋 Pré-requisitos
- JDK 21 instalado
- Gradle (wrapper incluído no projeto)
- Git
- IDE recomendada: IntelliJ IDEA (Community ou Ultimate)
- (Opcional) Docker e Docker Compose

---

## ▶️ Como Executar Localmente
1. **Backend (API Spring Boot)**  
   Clone o repositório:
   ```bash
   git clone https://github.com/Danie-Anx/AquaMind-Java.git
   cd AquaMind-Java


## 🔗 Rotas Principais da API

| Método | Caminho                    | Descrição                                |
| ------ |----------------------------| ---------------------------------------- |
| POST   | `/api/auth/register`       | Registrar novo usuário (JWT)             |
| POST   | `/api/auth/login`          | Fazer login e obter token JWT            |
| GET    | `/api/propriedades`        | Listar propriedades do usuário           |
| GET    | `/api/propriedades/{id}`   | Obter detalhes de uma propriedade        |
| POST   | `/api/propriedades`        | Criar nova propriedade                   |
| PUT    | `/api/propriedades/{id}`   | Atualizar propriedade                    |
| DELETE | `/api/propriedades/{id}`   | Deletar propriedade                      |
| GET    | `/api/zonas`               | Listar zonas                             |
| GET    | `/api/zonas/{id}`          | Obter detalhes da zona                   |
| POST   | `/api/zonas`               | Criar nova zona                          |
| PUT    | `/api/zonas/{id}`          | Atualizar zona                           |
| DELETE | `/api/zonas/{id}`          | Deletar zona                             |
| GET    | `/api/sensores`            | Listar sensores                          |
| GET    | `/api/sensores/{id}`       | Obter detalhes do sensor                 |
| POST   | `/api/sensores`            | Criar novo sensor                        |
| PUT    | `/api/sensores/{id}`       | Atualizar sensor                         |
| DELETE | `/api/sensores/{id}`       | Deletar sensor                           |
| GET    | `/api/RegistraSensor`      | Listar leituras de sensores              |
| GET    | `/api/RegistraSensor/{id}` | Obter leitura específica                 |
| POST   | `/api/RegistraSensor`      | Registrar nova leitura                   |
| PUT    | `/api/RegistraSensor/{id}` | Atualizar leitura existente              |
| DELETE | `/api/RegistraSensor/{id}` | Deletar leitura                          |
| GET    | `/api/irrigacoes`          | Listar histórico de irrigações           |
| GET    | `/api/irrigacoes/{id}`     | Obter detalhes de irrigação              |
| POST   | `/api/irrigacoes`          | Criar novo registro de irrigação         |
| PUT    | `/api/irrigacoes/{id}`     | Atualizar irrigação existente            |
| DELETE | `/api/irrigacoes/{id}`     | Deletar irrigação                        |

### Observações:

- **"O header Authorization: Bearer {token} deve ser enviado em todas as chamadas após o login."**
- **"Cada rota de CRUD segue as boas práticas REST (status codes 200/201/204/400/401/403/404)."**

# Alguns Testes Manuais via Swagger UI e Obter Token

## Registrar Usuário

No Auth Controller → `POST /api/auth/register`, envie um JSON como:

```json
{
   "nome": "daniel",
   "email": "daniel@gmail.com",
   "senha": "daniel12345",
   "tipoUsuario": "ROLE_USER",
   "ativo": true
}
```
Clique em Execute.

## Login de Usuario

No Auth Controller → `POST /api/auth/login`, envie um JSON como:

```json
{
   "email": "daniel@gmail.com",
   "senha": "daniel12345"
}
```
Clique em Execute.

## “Authorize” no Swagger
No canto superior direito do Swagger UI, clique no botão Authorize (ícone de cadeado).

No modal que abrir, cole o token obtido com o prefixo Bearer (exemplo: Bearer eyJhbGciOiJI...).

Clique em Authorize e depois em Close.

Agora todos os endpoints marcados com ícone de cadeado aceitarão esse token no header Authorization.

## Exemplos de algumas Chamadas CRUD

### Criar Propriedade

Endpoint: POST /api/propriedades

Body:

```json
{
   "nome": "Fazenda Sol",
   "idUsuario": 1,
   "idEstado": 1,
   "areaHectares": 10.0,
   "ativo": true
}
```
Clique em Execute e observe o HTTP 200 com o JSON da nova propriedade.

### Criar Estado 

Endpoint: POST /api/estados

Body:

```json
{
   "nome": "São Paulo",
   "sigla": "SP"
}
```
Clique em Execute. Deve retornar HTTP 201 e o JSON do estado criado.

### Criar Zona

Endpoint: POST /api/zonas

Body:

```json
{
   "idPropriedade": 1,
   "nome": "Zona A",
   "areaHectares": 1.5, 
   "ativo": true
}

```
Execute → HTTP 201 com JSON da zona.

### Pegar zona:  

Endpoint: GET /api/zonas/{id}

Clicar no botão TRY IT OUT

colocacar o ID

Execute → HTTP 201 com JSON da zona.

#### Curl

curl -X 'GET' \
- 'http://localhost:8080/api/zonas/1' \
- -H 'accept: */*' \
- -H 'Authorization: Bearer (TOKEN)'

#### Request URL

- http://localhost:8080/api/zonas/1

#### Response body

```json
{
"id": 1,
"idPropriedade": 1,
"nome": "Zona A",
"areaHectares": 1.5,
"ativo": true
}
```
   
# 👥 Equipe AquaMind

- Robert Daniel da Silva Coimbra - RM555881 – Desenvolvedor Full Stack

- Marcos Antonio Ramalho Neto - RM554611 – Arquiteto de Solução / UX Designer

- Arthur Ramos Dos Santos - RM558798 – Desenvolvedor Full Stack / DevOps