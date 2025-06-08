#!/bin/bash

echo "🔧 Ajustando permissões do Gradle Wrapper..."
chmod +x gradlew

echo "📦 Verificando e corrigindo build.gradle..."
# Backup do build.gradle original
cp build.gradle build.gradle.backup 2>/dev/null || true

# Adicionar dependência MySQL se não existir
if ! grep -q "mysql-connector" build.gradle; then
    echo "Adicionando dependência MySQL ao build.gradle..."
    sed -i '/dependencies {/a\	runtimeOnly '\''mysql:mysql-connector-java:8.0.33'\''' build.gradle
fi

echo "🐳 Gerando Dockerfile..."
cat > Dockerfile << 'EOF'
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN ./gradlew clean build -x test

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
EOF

echo "🐳 Gerando docker-compose.yml..."
cat > docker-compose.yml << 'EOF'
version: '3.8'
services:
  db:
    image: mysql:8.4.5
    environment:
      MYSQL_DATABASE: aquamind
      MYSQL_ALLOW_EMPTY_PASSWORD: "yes"
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      timeout: 20s
      retries: 10

  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      db:
        condition: service_healthy
    environment:
      - SPRING_PROFILES_ACTIVE=docker

volumes:
  mysql-data:
EOF

echo "⚙️ Atualizando application.properties com configuração completa..."
cat > src/main/resources/application.properties << 'EOF'
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/aquamind
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
app.jwt.secret=mySecretKey123456789012345678901234567890123456789012345678901234567890
app.jwt.expiration-ms=86400000

# Logging
logging.level.org.springframework.security=DEBUG
logging.level.br.com.fiap.aquamind=DEBUG
EOF

echo "⚙️ Criando configuração específica do Docker..."
cat > src/main/resources/application-docker.properties << 'EOF'
# Database Configuration for Docker
spring.datasource.url=jdbc:mysql://db:3306/aquamind
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
app.jwt.secret=mySecretKey123456789012345678901234567890123456789012345678901234567890
app.jwt.expiration-ms=86400000

# Logging
logging.level.org.springframework.security=DEBUG
logging.level.br.com.fiap.aquamind=DEBUG
EOF

echo "🚀 Iniciando aplicação com Docker Compose..."
docker-compose up --build