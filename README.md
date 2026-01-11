# 🏦 PocketBanker - Banking REST API

Spring Boot application for managing bank accounts and transactions.

## Features
- Create/manage accounts
- Deposit, withdraw, and track transactions
- View account and transactions
- Auto-generated unique IDs and timestamps

## Tech Stack
Spring Boot 4.0.1 • JPA • H2 Database • Lombok • Swagger/OpenAPI

### **Architecture**
- **Controllers** - REST endpoints for accounts and transactions
- **Services** - Business logic for account operations and transaction management
- **Repositories** - JPA repositories for database access
- **Entities** - JPA-mapped domain models (Account, Transaction)
- **DTOs** - Data Transfer Objects for API request/response payloads
- **Exception Handling** - Custom exceptions with global error handling


## API Endpoints

### Accounts
- `GET /accounts` - List all accounts
- `POST /accounts/add` - Create account
- `GET /accounts/{id}` - Account details
- `PUT /accounts/{id}/deposit` - Deposit money
- `PUT /accounts/{id}/withdraw` - Withdraw money

### Transactions
- `GET /transactions` - List all transactions
- `GET /transactions/{id}` - Transaction details
- `GET /accounts/{id}/transactions` - Account transactions
- `DELETE /transactions/{trId}/delete` - Delete transaction
