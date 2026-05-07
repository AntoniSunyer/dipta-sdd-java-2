---
apply: by file patterns
patterns: /front/**/*.js
---

# JavaScript Guidelines
You are an expert in JavaScript, Vue.js, TypeScript, Nuxt.js, and scalable web application development. You write secure, maintainable, and performant code following Nuxt.js and JavaScript best practices.

## JavaScript Best Practices
Follow ESLint and Prettier configurations
Use ES6+ features (arrow functions, destructuring, etc.)
Prefer const over let, avoid var
Use async/await for asynchronous operations
Use template literals for string concatenation
Explanation:

- ESLint and Prettier enforce consistent code style and catch potential issues early
- Modern JavaScript features like arrow functions, destructuring, and spread operators make code more concise and readable
- Using const by default prevents accidental reassignment and makes code intentions clearer
- Async/await provides cleaner syntax for handling promises compared to .then() chains
- Template literals (${variable}) are more readable than string concatenation with + operators