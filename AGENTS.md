# Agents Instructions

### Behavior Guidelines

- When using templates, replace {placeholders} with actual values.
- Business_Domain_Language: `English`
- Chat responses should be in the user's language.
- Code and documentation should be in English.
- Be concise and clear in communication. Sacrifice grammar for concision.
- When in doubt, ask questions one by one with closed answers.

### Environment

- **Agents Folder**: `.agents`
- **Product Folder**: `.product`
- **Source Folders**: `back`, `front`
- **OS dev**: `Windows`
- **Terminal**: `PowerShell`
- **Git remote**: `https://github.com/AlbertoBasaloAcademy/dipta-sdd-java-2`
- **Default branch**: `main`

### Folder structure
```text
.                         # Project root  
├── AGENTS.md             # This file with the main guidelines for agents
├── .agents/              # Files related to agents (skills, commands, etc)
│   ├── agents/           # Specific agent role definitions
│   ├── prompts/          # Reusable prompts directory
│   └── skills/           # Custom agent skills
├── .product/             # Files related to the product (specs, plans, etc)
│   └── specs/            # Specifications and plans
├── README.md             # Human-friendly project summary
├── back/                 # Backend source code (Java/Spring Boot)
├── front/                # Frontend source code (Vue.js)
└── docs/                 # Documentation files
```

### Naming Conventions

— Use conventional commit messaging
— Code branches prefixes must be: `feat/` | `fix/` | `chore/`
— Generate short slugs for artifacts and names (fewer-than-20-chars).

## Product

AstroBookings is a REST API and a web application for the fictional space tourism company.

- Manage the rocket fleet.
- Plan and confirm or cancel launches.
- Record passenger bookings without overbooking.

## Technology

### Backend Tier (back)

- **Tier**: Backend
- **Language**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Testing**: JUnit 5, Mockito
- **Storage**: In-memory (MVP)
- **Security**: None (MVP)
- **Logging**: Standard Spring Boot

- **Development workflow**:
  - Init: `./mvnw install`
  - Build: `./mvnw compile`
  - Run: `./mvnw spring-boot:run`
  - Test: `./mvnw test`
  - Lint: `./mvnw checkstyle:check`
  - Deploy: `N/A`

- **Folder structure**:
```text
.                                 # Tier source root
├── pom.xml                       # Maven configuration
├── src/main/java                 # Source code
└── src/test/java                 # Test code
```

### Frontend Tier (front)

- **Tier**: Frontend
- **Language**: JavaScript (Vue 3)
- **Framework**: Vue CLI
- **Testing**: NPM test
- **Storage**: Browser memory
- **Security**: None (MVP)
- **Logging**: Console

- **Development workflow**:
  - Init: `npm install`
  - Build: `npm run build`
  - Run: `npm run serve`
  - Test: `npm test`
  - Lint: `npm run lint`
  - Deploy: `N/A`

- **Folder structure**:
```text
.                                 # Tier source root
├── package.json                  # NPM configuration
├── src/                          # Vue source code
└── public/                       # Static assets
```

- **Agent Skills**:
  - initialize: Initialize the environment by loading all necessary commands, rules, and skills.
  - codify: Write code to implement a feature.

## Principles

1. Think Before Coding
   Don't assume it. Don't hide confusion. Surface tradeoffs.
2. Simplicity-First
   Minimum code that solves the problem. Nothing speculative.
3. Surgical Changes
   Touch only what you must. Clean up only your own mess.
4. Goal-Driven Execution
   Defines success criteria. Loop until verified.

> last updated: May 2026
