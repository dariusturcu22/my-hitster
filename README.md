# My Hitster

My Hitster is a full-stack application designed to generate AI-assisted game cards based on music tracks.  
It combines structured metadata retrieval with LLM-based enrichment to produce detailed, playable content from YouTube links.

The project is structured as a monorepo containing:

- `backend/` – Spring Boot API  
- `frontend/` – Next.js web client  
- `mobile/` – Standalone Flutter app for music playback (YouTube iframe-based)  

---

## Architecture Overview

### Backend
- Java + Spring Boot
- Spring Security (OAuth2 + JWT)
- PostgreSQL
- JPA (Hibernate) with annotation-based entity mapping
- Lombok
- Spring AI (ChatClient) for LLM integration

### Frontend
- Next.js
- Axios for HTTP communication
- Token auto-refresh via interceptor logic
- TanStack Query
- tailwindcss & shadcn/ui
- react-hook-form

### Mobile
- Flutter
- Standalone application for playing music via YouTube iframe

---
### Core Functional Flow

1. **Authentication**: Secured via Spring Security (OAuth2/JWT).
2. **Collaborative Playlists**: Shared state management allowing multi-user CRUD operations.
3. **AI-Driven Card Generation**: 
   - **Validation**: Client-side YouTube embed verification.
   - **Enrichment**: Parallel data fetching from YouTube, MusicBrainz, and Genius.
   - **Synthesis**: Structured JSON generation via LLM (Spring AI) to automate data entry.
4. **Export**: High-fidelity PDF generation for physical card printing.
---

## Authentication & Security

The application implements:

- OAuth2 authentication
- Email/password authentication
- JWT-based access tokens
- Refresh token mechanism
- `/refresh` endpoint for token renewal
- Axios interceptor logic to automatically refresh expired access tokens
- Role-based access control (USER role)

Access tokens are short-lived and automatically renewed when expired.  
If refresh fails, the user is logged out.

---

## Database

- PostgreSQL
- JPA (Hibernate) for ORM
- Annotation-based schema definition
- Docker Compose used for local PostgreSQL container

---

## Project Structure
```
root/
│
├── backend/
│ └── Spring Boot application
│ └── .env
│
├── frontend/
│ └── Next.js application
│ └── .env
│
├── mobile/
│ └── Flutter application (music playback)
│
└── docker-compose.yml
```
---

## Running Locally

### Prerequisites

- Java 17+
- Node.js
- PostgreSQL (or Docker)
- Flutter (optional, for mobile app)

---

### 1. Start Database (Docker)

```bash
docker-compose up -d
```
### 2. Start Backend
```bash
cd backend
./mvnw spring-boot:run
```
Required backend environment variables (example):
```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=
OPENAI_API_KEY=
YOUTUBE_API_KEY=
OAUTH2_CLIENT_ID=
OAUTH2_CLIENT_SECRET=
```
Required frontend environment variables (example):
```
NEXT_PUBLIC_API_URL=
```
### 3. Start Frontend
```bash
cd frontend
npm install
npm run dev
```
Ensure backend base URL is configured correctly in environment variables.
### 4. Run Mobile (Optional)
```bash
cd mobile
flutter pub get
flutter run
```
The mobile app is standalone and does not connect to the backend.
---

## Technical Highlights

- Monorepo structure separating backend, web, and mobile concerns  
- Secure JWT authentication with refresh token flow  
- AI-powered content generation via Spring AI  
- Hybrid metadata aggregation (YouTube API + external music sources + LLM)  
- Layered backend architecture (controller, service, repository)  

---

## Current Status

- Runs locally  
- Database containerized with Docker  
- Backend and frontend functional  
- Public deployment planned  

---

## Future Improvements

- Production deployment (Railway / Vercel)  
- Backend containerization  
- Unit and integration testing  
- Rate limiting  
- Caching layer 
