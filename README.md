# SwiftSpend: Full-Stack Digital Wallet MVP

SwiftSpend is a modern fintech digital wallet application designed for secure peer-to-peer transfers and personal fund management. Built with a focus on **transactional integrity** and **clean architecture**, it demonstrates a robust integration between a Spring Boot backend and a Vue.js frontend.

## Tech Stack

- **Backend:** Java 21, Spring Boot 3, Spring Security (JWT), Hibernate/JPA
- **Frontend:** Vue 3 (Composition API), Pinia, Tailwind CSS, Axios
- **Database:** PostgreSQL
- **Architecture:** RESTful API with Global Exception Handling and Transactional Management

## Key Features

- **Secure Authentication:** JWT-based login with automatic session expiration handling via Axios interceptors.
- **Peer-to-Peer Transfers:** Robust transfer logic ensuring atomic operations—money is never lost or duplicated.
- **Real-time Validation:** Backend-driven form validation that maps directly to UI components for a seamless UX.
- **Financial Ledger:** Built using `BigDecimal` for precision and a structured account management system.

---

## Installation & Setup

### 1. Backend Configuration

Clone the repository and navigate to the backend folder. The application uses a `.env` file to manage sensitive environment variables.

For a quick start, copy the provided example file:

```bash
cp .env.example .env
```

Then, update the values in your new `.env` file with your local credentials:

```env
# Database Settings
DB_URL=jdbc:postgresql://localhost:5432/swiftspend_db
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Security Settings
JWT_SECRET_KEY=your_secure_hex_string
JWT_EXPIRATION=3600000
```

### 2. Database Setup

Ensure PostgreSQL is running and create a database named `swiftspend_db`. Hibernate will automatically generate the schema on startup due to the `spring.jpa.hibernate.ddl-auto=update` configuration.

### 3. Frontend Configuration

Navigate to the frontend directory and install dependencies:

```bash
npm install
npm run dev
```

---

## Engineering Highlights

### Transactional Integrity

All money movements are wrapped in `@Transactional` boundaries. This ensures that if the recipient's account fails to update, the sender's balance is automatically rolled back, preventing data inconsistency.

### Global Error Mapping

The application uses a custom `GlobalExceptionHandler` that returns `ProblemDetail` (RFC 7807) objects. This allows the Vue frontend to dynamically highlight specific form fields (like `amount` or `email`) based on backend validation errors.

### Secure API Communication

Implemented custom Axios interceptors to globally handle `401 Unauthorized` responses. If a session expires, the interceptor automatically clears the Pinia store and redirects the user to the login page, ensuring a secure and synchronized user experience.

---
