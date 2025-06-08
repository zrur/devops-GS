# Testes da API AquaMind

# 1. Verificar se a API está rodando
echo "=== Testando conectividade básica ==="
curl -i http://localhost:8080/

echo -e "\n=== Testando endpoint de registro com dados completos ==="
# Registrar um usuário com todos os campos obrigatórios
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@teste.com", 
    "senha": "123456",
    "tipoUsuario": "PROPRIETARIO"
  }'

echo -e "\n=== Testando login ==="
# Fazer login com o usuário criado
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@teste.com",
    "senha": "123456"
  }'

echo -e "\n=== Testando endpoints públicos ==="
# Testar outros endpoints que podem existir
curl -i http://localhost:8080/api/auth/
curl -i http://localhost:8080/api/usuarios/
curl -i http://localhost:8080/api/propriedades/

# Verificar documentação da API (se houver Swagger)
echo -e "\n=== Verificando documentação ==="
curl -i http://localhost:8080/swagger-ui.html
curl -i http://localhost:8080/v3/api-docs


# Verificar containers rodando
echo -e "\n=== Verificando containers Docker ativos ==="
docker ps
# Verificar logs do container da aplicação
echo -e "\n=== Verificando logs do container da aplicação ==="
docker logs $(docker ps -q --filter "ancestor=aquamind/app")    
# Verificar logs do container do banco de dados
echo -e "\n=== Verificando logs do container do banco de dados ==="
docker logs $(docker ps -q --filter "ancestor=mysql:8.4.5")
# Verificar status do banco de dados
echo -e "\n=== Verificando status do banco de dados ==="
docker exec -it $(docker ps -q --filter "ancestor=mysql:8.4.5") mysqladmin ping -h localhost
