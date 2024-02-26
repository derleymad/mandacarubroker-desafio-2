# MandaCaru Broker API

## Descrição
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).

## Recursos

### Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis.

**Header da Solicitação:**
Bearer token : YourJWTToken

**Endpoint:**
```http
GET /stocks
```

### Obter uma Ação por ID

**Header da Solicitação:**
Bearer token : YourJWTToken

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
GET /stocks/{id}
```

### Criar uma Nova Ação
**Header da Solicitação:**
Bearer token : YourJWTToken

Cria uma nova ação com base nos dados fornecidos.

**Endpoint:**
```http
POST /stocks
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}

```
### Atualizar uma Ação por ID
**Header da Solicitação:**
Bearer token : YourJWTToken

Atualiza os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
PUT /stocks/{id}
```
**Header da Solicitação:**
Bearer token : YourJWTToken
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 59.97
}

```

### Excluir uma Ação por ID
Exclui uma ação específica com base no ID.

**Header da Solicitação:**
Bearer token : YourJWTToken

**Endpoint:**
```http
DELETE /stocks/{id}
```

### Adicionar usuário
Adiciona um novo usuário à aplicação, usuários com menos de 18 anos não podem se registrar.

**Header da Solicitação:**
Bearer token : YourJWTToken

**Endpoint:**
```http
POST /auth/register
```
**Corpo da Solicitação (Request Body):**

```JSON
{
"login": "user",
"password": "password",
"role": "USER",
"firstName": "USER",
"lastName": "user",
"birthDate": "01/01/2000",	
"balace": 0.0
}      
```

### Login usuário
Faz login com seu usuário registrado.

**Header da Solicitação:**
Bearer token : YourJWTToken

**Endpoint:**
```http
POST /auth/login
```
**Corpo da Solicitação (Request Body):**

```JSON
{
"login": "user",
"password": "password",
}      
```

### WithDraw da conta
Fazer um saque de acordo com seu saldo e seu usuário logado.

**Header da Solicitação:**
Bearer token : YourJWTToken

**Endpoint:**
```http
POST /withdraw
```
**Query**
```http
amount?="double"
```

### Deposit da conta
Fazer um depósito na sua conta de acordo com o usuário logado.

**Header da Solicitação:**
Bearer token : YourJWTToken

**Endpoint:**
```http
POST /deposit
```
**Query**
```http
amount?="double"
```







## Uso
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`.

## Requisitos
- Java 11 ou superior
- Maven
- Banco de dados

## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

## Contribuições
Contribuições são bem-vindas!

## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).

