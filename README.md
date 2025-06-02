# AquaMind – Sistema Inteligente de Irrigação


> **AquaMind** é uma solução completa para pequenos e médios produtores rurais que desejam monitorar e automatizar a irrigação de suas propriedades, 
> especialmente em regiões com escassez hídrica. Com integração IoT, backend Java Spring Boot, dashboard web, API REST, segurança JWT e containerização, 
> o AquaMind oferece eficiência no uso da água, histórico de dados e controle remoto das bombas.

---

## 📋 Sumário

1. [Visão Geral](#visão-geral)
2. [Principais Funcionalidades](#principais-funcionalidades)
3. [Arquitetura e Estrutura de Pastas](#arquitetura-e-estrutura-de-pastas)
4. [Entidades de Dominio](#entidades-de-dominio)
5. [Fluxo de Dados](#fluxo-de-dados)
6. [Tecnologias Utilizadas](#tecnologias-utilizadas)
7. [Pré-requisitos](#pré-requisitos)
8. [Como Executar Localmente](#como-executar-localmente)
    1. [1. Backend (API Spring Boot)](#1-backend-api-spring-boot)
    2. [2. Banco de Dados H2](#2-banco-de-dados-h2)
    3. [3. Frontend Web (Swagger UI)](#3-frontend-web-swagger-ui)
    4. [4. Execução de Testes](#4-execução-de-testes)
9. [Rotas Principais da API](#rotas-principais-da-api)
10. [Exemplos de Uso](#exemplos-de-uso)
11. [Containerização com Docker (Opcional)](#containerização-com-docker-opcional)
12. [Contribuição](#contribuição)

---

## 🌎 Visão Geral

O **AquaMind** foi concebido para enfrentar os desafios da seca e escassez hídrica em áreas agrícolas, oferecendo:

- **Monitoramento em tempo real** da umidade do solo por meio de sensores IoT (ESP32 + sensores capacitivos).
- **Dashboard Web** para visualizar gráficos históricos de umidade, alertas e status das zonas de plantio.
- **Controle remoto de irrigação**: acione bombas manualmente ou de forma automática quando a umidade estiver abaixo de um limiar configurado.
- **Segurança JWT**: autenticação de usuários e proteção de endpoints.
- **API RESTful** com documentação interativa via Swagger.
- **Estrutura modular** (Propriedade → Zona → Sensor → Leitura → Irrigação), permitindo futuras expansões (integração com previsão climática, novos sensores, relatórios avançados).

Este projeto faz parte do desafio Java Advanced da FIAP Global Solution 2025-1 e visa demonstrar boas práticas de arquitetura, design de API, containerização e DevOps em apenas duas semanas de desenvolvimento.

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
    - Erros padronizados (`404 Not Found`, `400 Bad Request`, `401 Unauthorized`) via `GlobalExceptionHandler`.

---

## 🏗️ Arquitetura e Estrutura de Pastas

src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── fiap/
│   │           └── aquamind/
│   │               ├── AquamindApplication.java
│   │               │   └── • Classe principal do Spring Boot
│   │               │
│   │               ├── config/
│   │               │   ├── CorsConfig.java
│   │               │   │   └── • Configuração global de CORS
│   │               │   │
│   │               │   ├── SecurityConfig.java
│   │               │   │   └── • HTTP Basic / JWT / roles / autorizações
│   │               │   │
│   │               │   └── SwaggerConfig.java
│   │               │       └── • Personalização da documentação OpenAPI (Swagger)
│   │               │
│   │               ├── controller/
│   │               │   ├── AuthController.java
│   │               │   │   └── • Registro e login (JWT)
│   │               │   │
│   │               │   ├── PropriedadeController.java
│   │               │   │   └── • CRUD de Propriedades
│   │               │   │
│   │               │   ├── ZonaController.java
│   │               │   │   └── • CRUD de Zonas
│   │               │   │
│   │               │   ├── SensorController.java
│   │               │   │   └── • CRUD de Sensores
│   │               │   │
│   │               │   ├── LeituraController.java
│   │               │   │   └── • Persistência de Leituras
│   │               │   │
│   │               │   └── IrrigacaoController.java
│   │               │       └── • Histórico de Irrigação
│   │               │
│   │               ├── dto/
│   │               │   ├── auth/
│   │               │   │   ├── LoginRequest.java
│   │               │   │   ├── RegisterRequest.java
│   │               │   │   └── AuthResponse.java
│   │               │   │
│   │               │   ├── PropriedadeDTO.java
│   │               │   ├── ZonaDTO.java
│   │               │   ├── SensorDTO.java
│   │               │   ├── LeituraDTO.java
│   │               │   └── IrrigacaoDTO.java
│   │               │
│   │               ├── model/
│   │               │   ├── Usuario.java
│   │               │   ├── Propriedade.java
│   │               │   ├── Zona.java
│   │               │   ├── Sensor.java
│   │               │   ├── Leitura.java
│   │               │   └── Irrigacao.java
│   │               │
│   │               ├── repository/
│   │               │   ├── UsuarioRepository.java
│   │               │   ├── PropriedadeRepository.java
│   │               │   ├── ZonaRepository.java
│   │               │   ├── SensorRepository.java
│   │               │   ├── LeituraRepository.java
│   │               │   └── IrrigacaoRepository.java
│   │               │
│   │               ├── service/
│   │               │   ├── AuthService.java
│   │               │   ├── PropriedadeService.java
│   │               │   ├── ZonaService.java
│   │               │   ├── SensorService.java
│   │               │   ├── LeituraService.java
│   │               │   └── IrrigacaoService.java
│   │               │
│   │               ├── security/
│   │               │   ├── JwtUtil.java
│   │               │   ├── JwtAuthenticationFilter.java
│   │               │   └── CustomUserDetailsService.java
│   │               │
│   │               └── exception/
│   │                   ├── GlobalExceptionHandler.java
│   │                   ├── ResourceNotFoundException.java
│   │                   └── BadRequestException.java
│   │
│   └── resources/
│       ├── application.properties   ── • Configurações de conexão, JPA, H2, JWT, etc.
│       └── data.sql                  ── (Opcional) scripts de preenchimento inicial do banco
│
└── test/
└── java/
└── com/
└── fiap/
└── aquamind/
├── controller/   ── • Testes de integração dos endpoints
├── service/      ── • Testes unitários dos serviços
└── repository/   ── • Testes de repositório (H2)



---

## 📦 Entidades de Domínio

1. **Usuario**
    - Campos: `id`, `nome`, `email`, `senha` (BCrypt), `perfis (List<String>)`, `propriedades (List<Propriedade>)`.
    - Relacionamentos: 1:N para Propriedade.

2. **Propriedade**
    - Campos: `id`, `nome`, `endereco`, `usuario (N:1)`, `zonas (List<Zona>)`.
    - Relacionamentos: N:1 para Usuario, 1:N para Zona.

3. **Zona**
    - Campos: `id`, `nome`, `localizacao`, `umidadeAlvo`, `propriedade (N:1)`, `sensores (List<Sensor>)`, `irrigacoes (List<Irrigacao>)`.
    - Relacionamentos: N:1 para Propriedade, 1:N para Sensor e Irrigacao.

4. **Sensor**
    - Campos: `id`, `nome`, `zona (N:1)`, `leituras (List<Leitura>)`.
    - Relacionamentos: N:1 para Zona, 1:N para Leitura.

5. **Leitura**
    - Campos: `id`, `sensor (N:1)`, `umidade (Double)`, `timestamp (LocalDateTime)`, `alerta (Boolean)`.
    - Relacionamentos: N:1 para Sensor.

6. **Irrigacao**
    - Campos: `id`, `zona (N:1)`, `dataHoraInicio`, `dataHoraFim`, `volumeAgua (Double)`.
    - Relacionamentos: N:1 para Zona.

---

## 🔄 Fluxo de Dados

1. **Sensor (ESP32) → MQTT / HTTP → Node-RED**
    - O ESP32 coleta umidade do solo e publica JSON em tópico MQTT:
      ```json
      {
        "sensorId": 1,
        "umidade": 45.3,
        "timestamp": "2025-06-01T10:00:00"
      }
      ```
    - Node-RED recebe, mapeia e faz POST em `/api/leituras` (API Java).

2. **API Java (Spring Boot)**
    - Valida payload via Bean Validation (`@Valid @RequestBody LeituraDTO`).
    - Persiste no banco (`LeituraRepository.save(...)`).
    - Calcula `alerta = (umidade < sensor.getZona().getUmidadeAlvo())` e armazena no campo `alerta`.

3. **Dashboard / Cliente**
    - (Futuro front-end React ou Dashboard Web) consome endpoints seguros (JWT) para listar zonas e leituras:
        - `GET /api/zonas` → Lista de zonas do usuário.
        - `GET /api/leituras/sensor/{sensorId}?period={dias}` → Histórico de leituras.
    - Botão **Acionar Bomba** dispara POST em `/api/irrigacoes` com `zonaId`, e a API cria registro de irrigação (dataHoraInicio, dataHoraFim simulado/apenas marcando histórico).

---

## 💻 Tecnologias Utilizadas

- **Linguagem**: Java 21
- **Framework**: Spring Boot 3.5.0
    - Modules: Web, Data JPA, Security, Validation
- **Banco de Dados**: H2 (em memória) → fácil setup para testes.
- **Persistência**: Spring Data JPA (Hibernate)
- **Autenticação**: JWT (io.jsonwebtoken:jjwt) + Spring Security
- **Documentação**: SpringDoc OpenAPI (Swagger UI)
- **Validação**: Bean Validation (jakarta.validation)
- **Lombok**: Redução de boilerplate (getters, setters, construtores)
- **MQTT / Node-RED**: Processamento de leituras IoT
- **Containerização (Opcional)**: Docker & Docker Compose
- **Testes**: JUnit 5 + Spring Boot Test + Spring Security Test

---

## 📋 Pré-requisitos

- JDK 21 instalado
- Gradle (wrapper incluído no projeto)
- Git
- IDE recomendada: IntelliJ IDEA (Community ou Ultimate)
- (Opcional) Docker e Docker Compose

---

## ▶️ Como Executar Localmente

### 1. Backend (API Spring Boot)

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Danie-Anx/AquaMind-Java.git
   cd AquaMind-Java
