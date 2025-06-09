# 🌊 AquaMind - Sistema Inteligente de Irrigação

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?style=flat-square&logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue?style=flat-square&logo=docker)
![MySQL](https://img.shields.io/badge/MySQL-8.4.5-blue?style=flat-square&logo=mysql)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

> Sistema de monitoramento e controle inteligente de irrigação para otimização do uso da água na agricultura.

## 📋 Índice

- [📖 Sobre o Projeto](#-sobre-o-projeto)
- [✨ Funcionalidades](#-funcionalidades)
- [🏗️ Arquitetura](#️-arquitetura)
- [🚀 Tecnologias](#-tecnologias)
- [⚙️ Pré-requisitos](#️-pré-requisitos)
- [🐳 Instalação e Execução](#-instalação-e-execução)
- [📚 Documentação da API](#-documentação-da-api)
- [🧪 Testes](#-testes)
- [👥 Integrantes](#-integrantes)
- [🎥 Demonstração](#-demonstração)
- [📄 Licença](#-licença)

## 📖 Sobre o Projeto

O **AquaMind** é uma solução tecnológica desenvolvida para resolver um dos principais desafios do agronegócio moderno: **o uso eficiente da água na irrigação**. O sistema utiliza sensores IoT para monitorar a umidade do solo em tempo real e automatiza o processo de irrigação, garantindo que as plantas recebam a quantidade ideal de água.

### 🎯 Problema Resolvido

- **Desperdício de água** por irrigação desnecessária
- **Falta de dados precisos** sobre umidade do solo
- **Custos elevados** com água e energia
- **Baixa produtividade** por irrigação inadequada
- **Impacto ambiental** do uso excessivo de recursos hídricos

### 💡 Solução Proposta

Sistema inteligente que combina **IoT**, **análise de dados** e **automação** para:
- Monitorar umidade do solo 24/7
- Automatizar irrigação baseada em dados reais
- Gerar alertas para tomada de decisão
- Otimizar uso de água e energia
- Aumentar produtividade agrícola

## ✨ Funcionalidades

### 🔐 **Autenticação & Autorização**
- [x] Sistema de login/registro com JWT
- [x] Controle de acesso por tipo de usuário
- [x] Sessões seguras com tokens

### 🏡 **Gestão de Propriedades**
- [x] Cadastro e gerenciamento de propriedades rurais
- [x] Associação com estados e regiões
- [x] Controle de área em hectares
- [x] Status ativo/inativo

### 🗺️ **Zonas de Irrigação**
- [x] Divisão de propriedades em zonas
- [x] Configuração específica por zona
- [x] Monitoramento independente
- [x] Controle de área por zona

### 📊 **Sensores IoT**
- [x] Cadastro de sensores de umidade
- [x] Registro de leituras em tempo real
- [x] Histórico de medições
- [x] Status e calibração de sensores

### 💧 **Sistema de Irrigação**
- [x] Controle automático de bombas
- [x] Agendamento de irrigação
- [x] Logs de ações realizadas
- [x] Monitoramento de volume de água

### 🚨 **Alertas Inteligentes**
- [x] Alertas automáticos de baixa umidade
- [x] Notificações de falhas em sensores
- [x] Sistema de resolução de alertas
- [x] Histórico de ocorrências

### ⚙️ **Configurações Avançadas**
- [x] Limites personalizados de umidade
- [x] Horários de irrigação programáveis
- [x] Configurações por zona
- [x] Perfis de irrigação

### 📈 **Relatórios e Analytics**
- [x] Histórico de irrigações
- [x] Consumo de água por período
- [x] Relatórios de eficiência
- [x] Dados para tomada de decisão

## 🏗️ Arquitetura

### 🌐 **Visão Geral da Solução**
```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              AQUAMIND ECOSYSTEM                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │
│  │ 📱 Mobile    │  │ 💻 Web App   │  │ 🌡️ IoT       │  │ 📊 Analytics │   │
│  │ React Native │  │ React.js     │  │ Sensors      │  │ Dashboard    │   │
│  │              │  │              │  │              │  │              │   │
│  │ • Alerts     │  │ • Management │  │ • Humidity   │  │ • Reports    │   │
│  │ • Control    │  │ • Reports    │  │ • Temperature│  │ • Insights   │   │
│  │ • Monitor    │  │ • Config     │  │ • pH Level   │  │ • Predictions│   │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘   │
│         │                 │                 │                 │           │
│         └─────────────────┼─────────────────┼─────────────────┘           │
│                           │                 │                             │
└───────────────────────────┼─────────────────┼─────────────────────────────┘
                            │                 │
                ┌───────────┴─────────────────┴───────────┐
                │             API GATEWAY                 │
                │                                         │
                │  • HTTPS/SSL Termination               │
                │  • Rate Limiting & Throttling          │
                │  • Authentication & Authorization      │
                │  • Request/Response Logging            │
                │  • Load Balancing                      │
                └───────────────┬─────────────────────────┘
                                │
                ┌───────────────┴─────────────────────────┐
                │          MICROSERVICES LAYER           │
                │                                         │
                │  ┌─────────────┐    ┌─────────────┐     │
                │  │🔐 Auth      │    │🏡 Property  │     │
                │  │Service      │    │Service      │     │
                │  │             │    │             │     │
                │  │• JWT        │    │• CRUD       │     │
                │  │• Users      │    │• Validation │     │
                │  │• Roles      │    │• Business   │     │
                │  └─────────────┘    └─────────────┘     │
                │                                         │
                │  ┌─────────────┐    ┌─────────────┐     │
                │  │📊 Sensor    │    │💧 Irrigation│     │
                │  │Service      │    │Service      │     │
                │  │             │    │             │     │
                │  │• Monitoring │    │• Automation │     │
                │  │• Analytics  │    │• Scheduling │     │
                │  │• Alerts     │    │• Control    │     │
                │  └─────────────┘    └─────────────┘     │
                └───────────────┬─────────────────────────┘
                                │
                ┌───────────────┴─────────────────────────┐
                │           DATA & PERSISTENCE            │
                │                                         │
                │  ┌─────────────┐    ┌─────────────┐     │
                │  │🗄️ MySQL     │    │📈 Time      │     │
                │  │Primary DB   │    │Series DB    │     │
                │  │             │    │             │     │
                │  │• Users      │    │• Sensor     │     │
                │  │• Properties │    │  Data       │     │
                │  │• Config     │    │• Metrics    │     │
                │  └─────────────┘    └─────────────┘     │
                │                                         │
                │  ┌─────────────┐    ┌─────────────┐     │
                │  │💾 Redis     │    │📁 File      │     │
                │  │Cache        │    │Storage      │     │
                │  │             │    │             │     │
                │  │• Sessions   │    │• Images     │     │
                │  │• Cache      │    │• Documents  │     │
                │  │• Temp Data  │    │• Exports    │     │
                │  └─────────────┘    └─────────────┘     │
                └─────────────────────────────────────────┘
```

### 🐳 **Arquitetura de Containers (DevOps)**
```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              DOCKER ECOSYSTEM                               │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                        INGRESS LAYER                                │   │
│  │                                                                     │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐                │   │
│  │  │🌐 Nginx     │  │🔒 Certbot   │  │📊 Prometheus│                │   │
│  │  │Reverse      │  │SSL Manager  │  │Monitoring   │                │   │
│  │  │Proxy        │  │             │  │             │                │   │
│  │  │Port: 80/443 │  │Auto-renewal │  │Port: 9090   │                │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘                │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    ↕                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                      APPLICATION LAYER                              │   │
│  │                                                                     │   │
│  │  ┌─────────────────┐              ┌─────────────────┐              │   │
│  │  │🚀 AquaMind App  │              │🔄 Health Check  │              │   │
│  │  │                 │              │Service          │              │   │
│  │  │ • Spring Boot   │◄────────────►│                 │              │   │
│  │  │ • Port: 8080    │   Network    │ • Liveness      │              │   │
│  │  │ • JWT Auth      │   Bridge     │ • Readiness     │              │   │
│  │  │ • REST API      │   (Custom)   │ • Startup       │              │   │
│  │  │ • Business      │              │                 │              │   │
│  │  │   Logic         │              └─────────────────┘              │   │
│  │  │                 │                                               │   │
│  │  │ Resources:      │              ┌─────────────────┐              │   │
│  │  │ • CPU: 1 core   │              │📝 Logging       │              │   │
│  │  │ • RAM: 1GB      │◄────────────►│Aggregator       │              │   │
│  │  │ • Disk: 2GB     │              │                 │              │   │
│  │  └─────────────────┘              │ • ELK Stack     │              │   │
│  │             │                      │ • Fluentd       │              │   │
│  │             │                      │ • Centralized   │              │   │
│  │             │                      └─────────────────┘              │   │
│  │  ┌──────────┴────────┐                                              │   │
│  │  │📂 Volume Mounts   │                                              │   │
│  │  │                   │                                              │   │
│  │  │ • /app/logs       │                                              │   │
│  │  │ • /app/config     │                                              │   │
│  │  │ • /app/temp       │                                              │   │
│  │  └───────────────────┘                                              │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    ↕                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                       PERSISTENCE LAYER                             │   │
│  │                                                                     │   │
│  │  ┌─────────────────┐              ┌─────────────────┐              │   │
│  │  │🗄️ MySQL 8.4     │              │💾 Redis Cache   │              │   │
│  │  │Primary Database │              │Session Store    │              │   │
│  │  │                 │              │                 │              │   │
│  │  │ • Port: 3306    │◄────────────►│ • Port: 6379    │              │   │
│  │  │ • aquamind DB   │   Network    │ • Key-Value     │              │   │
│  │  │ • InnoDB Engine │   Bridge     │ • TTL Support   │              │   │
│  │  │ • UTF8 Charset  │              │ • Pub/Sub       │              │   │
│  │  │                 │              │                 │              │   │
│  │  │ Resources:      │              │ Resources:      │              │   │
│  │  │ • CPU: 1 core   │              │ • CPU: 0.5 core │              │   │
│  │  │ • RAM: 2GB      │              │ • RAM: 512MB    │              │   │
│  │  │ • Disk: 10GB    │              │ • Disk: 1GB     │              │   │
│  │  └─────────────────┘              └─────────────────┘              │   │
│  │             │                                │                      │   │
│  │  ┌──────────┴────────┐            ┌────────┴──────────┐            │   │
│  │  │📂 Persistent      │            │📂 Persistent      │            │   │
│  │  │Volume             │            │Volume             │            │   │
│  │  │                   │            │                   │            │   │
│  │  │ • mysql-data      │            │ • redis-data      │            │   │
│  │  │ • Backup Ready    │            │ • AOF Persistence │            │   │
│  │  │ • Point-in-Time   │            │ • RDB Snapshots   │            │   │
│  │  │   Recovery        │            │                   │            │   │
│  │  └───────────────────┘            └───────────────────┘            │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                        NETWORK LAYER                                │   │
│  │                                                                     │   │
│  │  ┌─────────────────────────────────────────────────────────────┐   │   │
│  │  │                    aquamind-network                         │   │   │
│  │  │                                                             │   │   │
│  │  │  Bridge Network: 172.20.0.0/16                            │   │   │
│  │  │                                                             │   │   │
│  │  │  Services:                                                  │   │   │
│  │  │  • app       → 172.20.0.10:8080                           │   │   │
│  │  │  • db        → 172.20.0.20:3306                           │   │   │
│  │  │  • redis     → 172.20.0.30:6379                           │   │   │
│  │  │  • nginx     → 172.20.0.5:80,443                          │   │   │
│  │  │  • monitor   → 172.20.0.40:9090                           │   │   │
│  │  │                                                             │   │   │
│  │  │  DNS Resolution: Automatic service discovery               │   │   │
│  │  │  Isolation: No external access except through nginx       │   │   │
│  │  └─────────────────────────────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────┘
```

### ⚡ **Fluxo de Dados em Tempo Real**
```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              DATA FLOW ARCHITECTURE                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  🌡️ SENSORES IoT                    📱 MOBILE APP                          │
│  ┌─────────────────┐                ┌─────────────────┐                    │
│  │ • Umidade Solo  │                │ • React Native  │                    │
│  │ • Temperatura   │                │ • Push Notifications                │
│  │ • pH Level      │                │ • Real-time Updates                 │
│  │ • Luminosidade  │                │ • Offline Support                   │
│  └─────────┬───────┘                └─────────┬───────┘                    │
│            │                                  │                            │
│            ▼                                  ▼                            │
│  ┌─────────────────┐                ┌─────────────────┐                    │
│  │ 📡 IoT Gateway  │                │ 🌐 API Gateway  │                    │
│  │                 │                │                 │                    │
│  │ • Protocol      │                │ • Rate Limiting │                    │
│  │   Translation   │                │ • Authentication│                    │
│  │ • Data          │                │ • Load Balancing│                    │
│  │   Validation    │                │ • SSL/TLS       │                    │
│  │ • Buffering     │                │                 │                    │
│  └─────────┬───────┘                └─────────┬───────┘                    │
│            │                                  │                            │
│            └─────────────┬────────────────────┘                            │
│                          ▼                                                 │
│            ┌─────────────────────────────────────┐                        │
│            │          🚀 SPRING BOOT API         │                        │
│            │                                     │                        │
│            │  ┌─────────────┐  ┌─────────────┐   │                        │
│            │  │🔐 Security  │  │📊 Business  │   │                        │
│            │  │Layer        │  │Logic Layer  │   │                        │
│            │  │             │  │             │   │                        │
│            │  │• JWT Auth   │  │• Rules      │   │                        │
│            │  │• RBAC       │  │• Validation │   │                        │
│            │  │• CORS       │  │• Processing │   │                        │
│            │  └─────────────┘  └─────────────┘   │                        │
│            │                                     │                        │
│            │  ┌─────────────┐  ┌─────────────┐   │                        │
│            │  │🚨 Alert     │  │⚙️ Automation│   │                        │
│            │  │Engine       │  │Engine       │   │                        │
│            │  │             │  │             │   │                        │
│            │  │• Thresholds │  │• Irrigation │   │                        │
│            │  │• Triggers   │  │• Scheduling │   │                        │
│            │  │• Notifications│ │• Control   │   │                        │
│            │  └─────────────┘  └─────────────┘   │                        │
│            └─────────────┬───────────────────────┘                        │
│                          ▼                                                 │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                      📊 DATA LAYER                                  │   │
│  │                                                                     │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐             │   │
│  │  │🗄️ MySQL     │    │💾 Redis     │    │📈 InfluxDB  │             │   │
│  │  │             │    │             │    │             │             │   │
│  │  │• Relational │◄──►│• Cache      │◄──►│• Time Series│             │   │
│  │  │• ACID       │    │• Sessions   │    │• Metrics    │             │   │
│  │  │• Complex    │    │• Temp Data  │    │• Sensor Data│             │   │
│  │  │  Queries    │    │• Fast R/W   │    │• Analytics  │             │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘             │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                    🎯 CONTROL ACTIONS                               │   │
│  │                                                                     │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐             │   │
│  │  │💧 Irrigation│    │📱 Alerts    │    │📊 Reports   │             │   │
│  │  │System       │    │& Notifications   │& Analytics  │             │   │
│  │  │             │    │             │    │             │             │   │
│  │  │• Pump       │    │• Email      │    │• Daily      │             │   │
│  │  │  Control    │    │• SMS        │    │• Weekly     │             │   │
│  │  │• Valve      │    │• Push       │    │• Monthly    │             │   │
│  │  │  Management │    │• Dashboard  │    │• Custom     │             │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘             │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 🔄 **DevOps Pipeline (CI/CD)**
```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CI/CD PIPELINE                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐         │
│  │📝 Development   │    │🔍 Code Quality  │    │🏗️ Build Stage    │         │
│  │                 │    │                 │    │                 │         │
│  │ • Feature       │───►│ • ESLint        │───►│ • Maven/Gradle  │         │
│  │   Branches      │    │ • SonarQube     │    │ • Unit Tests    │         │
│  │ • Pull Requests │    │ • Security Scan │    │ • Integration   │         │
│  │ • Code Reviews  │    │ • Dependencies  │    │   Tests         │         │
│  └─────────────────┘    └─────────────────┘    └─────────────────┘         │
│                                                           │                 │
│                                                           ▼                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐         │
│  │🐳 Container     │    │🧪 Testing       │    │📦 Artifact      │         │
│  │Build            │    │Environment      │    │Management       │         │
│  │                 │    │                 │    │                 │         │
│  │ • Dockerfile    │◄───│ • E2E Tests     │◄───│ • Docker Images │         │
│  │ • Multi-stage   │    │ • API Tests     │    │ • Version Tags  │         │
│  │ • Optimization  │    │ • Performance   │    │ • Registry Push │         │
│  │ • Security Scan │    │ • Load Tests    │    │ • Vulnerability │         │
│  └─────────────────┘    └─────────────────┘    └─────────────────┘         │
│           │                                                                 │
│           ▼                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                        🚀 DEPLOYMENT STAGES                         │   │
│  │                                                                     │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐             │   │
│  │  │🔧 DEV       │    │🧪 STAGING   │    │🌟 PRODUCTION│             │   │
│  │  │Environment  │    │Environment  │    │Environment  │             │   │
│  │  │             │    │             │    │             │             │   │
│  │  │• Auto       │───►│• Manual     │───►│• Blue/Green │             │   │
│  │  │  Deploy     │    │  Approval   │    │  Deployment │             │   │
│  │  │• Feature    │    │• UAT        │    │• Canary     │             │   │
│  │  │  Testing    │    │• Smoke      │    │  Release    │             │   │
│  │  │• Quick      │    │  Tests      │    │• Monitoring │             │   │
│  │  │  Feedback   │    │• Pre-prod   │    │• Rollback   │             │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘             │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                    📊 MONITORING & OBSERVABILITY                    │   │
│  │                                                                     │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐             │   │
│  │  │📈 Metrics   │    │📝 Logging   │    │🔍 Tracing   │             │   │
│  │  │             │    │             │    │             │             │   │
│  │  │• Prometheus │    │• ELK Stack  │    │• Jaeger     │             │   │
│  │  │• Grafana    │    │• Fluentd    │    │• Zipkin     │             │   │
│  │  │• Alerts     │    │• Kibana     │    │• APM        │             │   │
│  │  │• Dashboards │    │• Log Agg    │    │• Distributed│             │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘             │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────┘
```

## 🚀 Tecnologias

### 🔧 **Backend**
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **MySQL 8.4.5** - Banco de dados relacional
- **JWT** - Tokens de autenticação
- **Swagger/OpenAPI** - Documentação da API

### 🐳 **DevOps & Infrastructure**
- **Docker** - Containerização
- **Docker Compose** - Orquestração de containers
- **Multi-stage Build** - Otimização de imagens
- **Gradle** - Gerenciamento de dependências
- **Health Checks** - Monitoramento de saúde

### 📊 **Monitoramento & Observabilidade**
- **Structured Logging** - Logs padronizados
- **Health Endpoints** - Status da aplicação
- **Metrics Exposure** - Métricas de performance
- **Request Tracking** - Rastreamento de requisições

## ⚙️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **Docker** 20.10+ [📥 Download](https://www.docker.com/get-started)
- **Docker Compose** 2.0+ [📥 Download](https://docs.docker.com/compose/install/)
- **Git** [📥 Download](https://git-scm.com/downloads)

### 🔍 **Verificar Instalação**
```bash
# Verificar versões
docker --version          # Docker version 20.10+
docker-compose --version  # Docker Compose version 2.0+
git --version             # Git version 2.30+
```

## 🐳 Instalação e Execução

### 1️⃣ **Clone o Repositório**
```bash
git clone https://github.com/seu-usuario/aquamind-devops.git
cd aquamind-devops/AquaMind-Java
```

### 2️⃣ **Execute o Script de Deploy**
```bash
# Dar permissão de execução
chmod +x executar_api_completa.sh

# Executar aplicação completa
bash executar_api_completa.sh
```

### 3️⃣ **Ou Execute Manualmente**
```bash
# Parar containers existentes
docker-compose down -v

# Subir aplicação + banco
docker-compose up --build
```

### 4️⃣ **Verificar Execução**
```bash
# Verificar containers rodando
docker-compose ps

# Acompanhar logs
docker-compose logs -f

# Verificar saúde da aplicação
curl http://localhost:8080/api/estados
```

### 🌐 **Acessar Aplicação**
- **API Base**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/actuator/health

## 📚 Documentação da API

### 🔗 **Swagger UI**
Acesse a documentação interativa em: **http://localhost:8080/swagger-ui.html**

### 🔐 **Autenticação**

#### Registrar Usuário
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@teste.com",
    "senha": "123456",
    "tipoUsuario": "PROPRIETARIO"
  }'
```

#### Fazer Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@teste.com",
    "senha": "123456"
  }'
```

#### Usar Token
```bash
# Salvar token retornado no login
TOKEN="seu-jwt-token-aqui"

# Fazer requisições autenticadas
curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/propriedades
```

### 📋 **Principais Endpoints**

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/auth/register` | Registrar usuário |
| `POST` | `/api/auth/login` | Fazer login |
| `GET` | `/api/usuarios` | Listar usuários |
| `GET` | `/api/propriedades` | Listar propriedades |
| `POST` | `/api/propriedades` | Criar propriedade |
| `GET` | `/api/zonas` | Listar zonas |
| `POST` | `/api/zonas` | Criar zona |
| `GET` | `/api/sensores` | Listar sensores |
| `POST` | `/api/sensores` | Criar sensor |
| `GET` | `/api/alertas-umidade` | Listar alertas |
| `GET` | `/api/estados` | Listar estados |

### 💾 **Exemplos de Payloads**

#### Criar Propriedade
```json
{
  "nome": "Fazenda Santa Clara",
  "idUsuario": 1,
  "idEstado": 1,
  "areaHectares": 150.5,
  "ativo": true
}
```

#### Criar Zona
```json
{
  "nome": "Zona A - Soja",
  "idPropriedade": 1,
  "areaHectares": 50.0,
  "ativo": true
}
```

#### Criar Sensor
```json
{
  "idZona": 1,
  "tipoSensor": "UMIDADE",
  "modelo": "DHT22",
  "ativo": true,
  "dataInstalacao": "2025-06-08"
}
```

## 🧪 Testes

### 🔍 **Testes de Conectividade**
```bash
# Testar API básica
curl -i http://localhost:8080/api/estados

# Testar autenticação
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"joao@teste.com","senha":"123456"}'

# Testar health check
curl http://localhost:8080/actuator/health
```

### 📊 **Monitoramento**
```bash
# Ver logs da aplicação
docker-compose logs -f app

# Ver logs do banco
docker-compose logs -f db

# Monitorar recursos
docker stats
```

### 🛠️ **Debug**
```bash
# Acessar container da aplicação
docker-compose exec app bash

# Acessar banco MySQL
docker-compose exec db mysql -u root -p aquamind

# Verificar network
docker network ls
docker network inspect aquamind-java_default
```

## 👥 Integrantes

Arthur Ramos Dos Santos - RM558798  
Marcos Antonio Ramalho Neto RM554611 
Robert Daniel da Silva Coimbra - RM555881 

> **Nota**: Substitua pelos dados reais dos integrantes do seu grupo.

## 🎥 Demonstração

### 📹 **Vídeo no YouTube**
🔗 **Link do vídeo**: [AquaMind - Demonstração Completa]([https://youtube.com/watch?v=SEU_VIDEO_ID](https://youtu.be/mEO2V66IWpk))

### 🎬 **Conteúdo do Vídeo** (5 minutos)
1. **Introdução** (30s) - Apresentação da solução
2. **Arquitetura** (1min) - Explicação técnica e DevOps
3. **Deploy** (1min) - Demonstração do Docker Compose
4. **API** (2min) - Testes dos endpoints principais
5. **Funcionalidades** (1min) - CRUD e business logic
6. **Conclusão** (30s) - Resultados e próximos passos

### 📸 **Screenshots**

#### Swagger Documentation
![Swagger UI](./docs/images/swagger-ui.png)

#### Docker Containers
![Docker Status](./docs/images/docker-containers.png)

#### API Testing
![API Tests](./docs/images/api-tests.png)

## 🔧 Configuração Avançada

### 🌍 **Variáveis de Ambiente**
```bash
# Para produção, configure:
export DATABASE_URL=jdbc:mysql://host:port/database
export DB_USERNAME=usuario
export DB_PASSWORD=senha_segura
export JWT_SECRET=chave_jwt_super_segura
export SPRING_PROFILES_ACTIVE=production
```

### ☁️ **Deploy em Nuvem**
```bash
# Exemplo para Railway
railway login
railway up

# Exemplo para Heroku
heroku create aquamind-api
heroku container:push web
heroku container:release web
```

### 📊 **Monitoramento Avançado**
- **Prometheus**: Metrics collection
- **Grafana**: Dashboards e alertas
- **ELK Stack**: Centralized logging
- **APM Tools**: Performance monitoring

## 🤝 Contribuição

1. **Fork** o projeto
2. **Clone** seu fork
3. **Crie** uma branch para sua feature
4. **Commit** suas mudanças
5. **Push** para a branch
6. **Abra** um Pull Request

### 📋 **Guidelines**
- Siga os padrões de código estabelecidos
- Adicione testes para novas funcionalidades
- Atualize a documentação quando necessário
- Use mensagens de commit descritivas

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 🌟 Reconhecimentos

- **FIAP** - Instituição de ensino
- **Professor(a)** - Orientação técnica
- **Comunidade Spring** - Documentação e suporte
- **Docker Community** - Ferramentas de containerização

---

<div align="center">

### 🌊 **AquaMind - Irrigação Inteligente para um Futuro Sustentável**

![Footer](https://img.shields.io/badge/Made%20with-❤️-red?style=for-the-badge)
![FIAP](https://img.shields.io/badge/FIAP-2025-blue?style=for-the-badge)

**[⬆ Voltar ao topo](#-aquamind---sistema-inteligente-de-irrigação)**

</div>
