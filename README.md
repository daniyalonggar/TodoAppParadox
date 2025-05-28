# TodoAppParadox
# 📝 TodoApp — Spring Boot + JWT + PostgreSQL


---

## 🔧 Используемые технологии

- Java 21
- Spring Boot 3
- Spring Security (JWT)
- PostgreSQL
- Spring Data JPA (Hibernate)
- ModelMapper
- Lombok
- Maven

---

## 📌 Функционал

### 🔐 Аутентификация и авторизация

- Регистрация пользователя — `POST /auth/registration`
- Вход пользователя (аутентификация) — `POST /auth/login`
- Аутентификация через JWT: каждый запрос к защищённым ресурсам требует токен.
- **Роли**: `ROLE_USER`, `ROLE_ADMIN` (по умолчанию — `ROLE_USER`)
- 🔐 Все пароли **надежно зашифрованы с помощью BCrypt**

> ❗ Пользователи всегда создаются с ролью `ROLE_USER`.  
> Чтобы сделать пользователя администратором, **нужно изменить его роль вручную через `AdminController`**.

---

### ✅ Управление задачами (`/todo/tasks`)

Пользователь может:

- `POST /todo/tasks/addTask` — создать задачу
- `GET /todo/tasks` — получить свои задачи (с фильтрацией по статусу)
- `PUT /todo/tasks/{taskId}` — обновить задачу
- `DELETE /todo/tasks/{taskId}` — удалить задачу

Задача имеет поля:
- `id`, `title`, `description`, `status` (`TODO`, `IN_PROGRESS`, `DONE`), `createdAt`, `updatedAt`

---

### 👨‍💼 Админ-функции (`/admin/users`)

Только для пользователей с ролью `ROLE_ADMIN`:
- `GET /admin/users` — получить всех пользователей
- `GET /admin/users/{id}` — получить одного пользователя
- `POST /admin/users` — создать пользователя (указать `role`, `username`, `password`)
- `PUT /admin/users/{id}` — обновить пользователя (например, назначить роль `ROLE_ADMIN`)
- `DELETE /admin/users/{id}` — удалить пользователя

---

## 🔌 Примеры запросов

### 🔐 Регистрация

```http
POST /auth/registration
Content-Type: application/json

{
  "username": "john",
  "password": "pass123",
  "yearOfBirth": 2000
}
```

### 🔐 Вход

```http
POST /auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "pass123"
}
```

Ответ:
```json
{ "jwt-token": "<токен>" }
```

### ✅ Создание задачи

```http
POST /todo/tasks/addTask
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "title": "Сделать тест",
  "description": "Написать тест по Spring"
}
```

---

## ▶️ Запуск проекта

### 1. Склонировать проект

```bash
git clone https://github.com/your-username/TodoApp.git
cd TodoApp
```

### 2. Настроить PostgreSQL

Создайте БД `todoapp`, укажите данные в `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todoapp
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. Запуск

```bash
mvn clean install
mvn spring-boot:run
```

Приложение будет доступно:  
📍 http://localhost:8080

---

## 🧪 Тестовые данные

```text
username: user
password: password123
role: ROLE_USER
```

Затем через `PUT /admin/users/{id}` можно назначить `ROLE_ADMIN`.

---
<img width="781" alt="Снимок экрана 2025-05-29 в 01 15 02" src="https://github.com/user-attachments/assets/91478279-ec32-4e9d-916c-47ea23efd9f3" />


<img width="1126" alt="Снимок экрана 2025-05-29 в 01 15 28" src="https://github.com/user-attachments/assets/6c0bc6d4-0db7-46d9-9f27-4b1818d79da1" />

<img width="869" alt="Снимок экрана 2025-05-29 в 01 16 24" src="https://github.com/user-attachments/assets/55f5d0c1-9430-49ec-9aaa-d023a73d1074" />

<img width="870" alt="Снимок экрана 2025-05-29 в 01 25 13" src="https://github.com/user-attachments/assets/f608b638-1e43-4e12-940a-287f1aca3502" />

<img width="870" alt="Снимок экрана 2025-05-29 в 01 26 30" src="https://github.com/user-attachments/assets/51fa025e-2388-42d5-8a65-4667f197d075" />

