# 🛒 DSCommerce

API REST de um e-commerce desenvolvida com Java e Spring Boot, focada em autenticação segura, boas práticas de arquitetura e separação de responsabilidades.

## 🚀 Sobre o projeto

O **DSCommerce** é um sistema backend completo para um e-commerce, que permite:

- Cadastro e listagem de **produtos** e **categorias**
- Gerenciamento de **pedidos**
- Autenticação e autorização com **JWT e OAuth2**
- Controle de **usuários e perfis de acesso**
- **Tratamento global de exceções**
- Organização modular com uso de **DTOs**, **projections**, e **camadas de serviço**

## 🛠️ Tecnologias e ferramentas

- Java 17
- Spring Boot
- Spring Web
- Spring Security (OAuth2 + JWT)
- Spring Data JPA
- H2 / PostgreSQL
- Maven

## 🧱 Estrutura do projeto

```bash
src/
├── config/                 # Configurações de segurança e tokens
│   └── customgrant/        # Implementação personalizada de OAuth2
├── controllers/            # Camada de controle (REST)
│   └── handlers/           # Tratamento de exceções
├── dtos/                   # Objetos de transferência de dados
├── entities/               # Entidades JPA
├── projections/            # Projeções para consultas otimizadas
├── repositories/           # Interfaces JPA
├── services/               # Regras de negócio
│   └── exceptions/         # Exceções de serviço
└── resources/              # Arquivos de configuração (application.properties)
```

## 🔐 Segurança

A autenticação é feita via **OAuth2 com JWT**, configurada por meio das classes:

- `AuthorizationServerConfig.java`
- `ResourceServerConfig.java`

Apenas usuários autenticados podem acessar endpoints protegidos. Algumas permissões são definidas por perfis de usuário.

## ▶️ Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/BrandaoFelipe/dscommerce.git
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd dscommerce
   ```

3. Execute a aplicação com o Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

4. A API estará disponível em:
   ```
   http://localhost:8080
   ```

> É possível configurar testes automatizados e utilizar bancos de dados em memória como H2 para facilitar o desenvolvimento.

## ✨ Funcionalidades (principais endpoints)

- `GET /products` – Lista todos os produtos
- `POST /orders` – Cria um novo pedido
- `GET /categories` – Lista categorias
- `POST /auth/token` – Gera token JWT
- `GET /users/profile` – Retorna informações do usuário autenticado

## 📚 Aprendizados

Durante o desenvolvimento deste projeto, foram aplicados conhecimentos sobre:

- Arquitetura em camadas (Controller-Service-Repository)
- Segurança com OAuth2 e JWT no Spring Security
- Boas práticas com DTOs e projections
- Tratamento global de exceções
- Separação de ambientes (dev/test)

## 👤 Autor

Desenvolvido por **Felipe Brandao**

- [LinkedIn](https://www.linkedin.com/in/felipe-brandao-08595722a/)
- [GitHub](https://github.com/BrandaoFelipe)
