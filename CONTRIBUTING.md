# Contributing to Dev Easy Test API

We welcome contributions! This guide will help you get started.

## Development Setup

1. **Prerequisites:**
   - Java 17+
   - Maven 3.8+
   - Docker (for LocalStack integration tests)

2. **Clone and Build:**

```bash
git clone https://github.com/GSSoftwareConsultancy/dev-easy-test-api.git
cd dev-easy-test-api
mvn clean verify
```

3. **Run Tests:**

```bash
mvn verify # All tests including integration
mvn verify -DskipITs # Skip integration tests
```

## Code Standards

- **Java 17:** Use modern Java features (records, sealed classes where appropriate)
- **Formatting:** Run `mvn spotless:apply` before committing
- **Static Analysis:** Fix Checkstyle and Error Prone warnings
- **Coverage:** Maintain 80%+ line coverage for new code
- **Tests:** Every capability implementation needs integration tests

## Pull Request Process

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Make changes with clear, atomic commits
4. Run `mvn verify` and ensure all checks pass
5. Push to your fork and create a pull request
6. PR must pass all GitHub Actions checks

## Commit Messages

Follow conventional commits:
- `feat: Add Azure Blob Storage adapter`
- `fix: Handle null values in DynamoDB items`
- `docs: Update README with PubSub examples`
- `test: Add edge case tests for S3 pagination`

## Questions?

Open an issue or discussion on GitHub!
