# Dev Easy Test API for Java

[![Build](https://github.com/GSSoftwareConsultancy/dev-easy-test-api/actions/workflows/build.yml/badge.svg)](https://github.com/GSSoftwareConsultancy/dev-easy-test-api/actions/workflows/build.yml)
![Java](https://img.shields.io/badge/Java-17-blue)
![License](https://img.shields.io/badge/License-Apache_2.0-green)

A community-driven toolkit to make testing Java applications easier and more consistent across frameworks.

This project aims to reduce boilerplate and provide a unified, framework-agnostic approach for writing integration and end-to-end tests, with first-class support for BDD using Cucumber.

Status: Early-stage. GitHub Actions is our only CI. We follow trunk-based development with small, frequent commits that keep main green.


## Why this project?
Modern Java teams build services with a wide range of frameworks (Spring, Spring Boot, Dropwizard, Guice, etc.). Each ecosystem offers different guidance and utilities for testing. That diversity is great, but developers often:
- Re-implement the same test utilities and fixtures across projects.
- Struggle to find a consistent way to do integration testing.
- Spend time wiring test infrastructure (databases, migrations, HTTP clients, mock servers, containers) instead of focusing on business logic.

Dev Easy Test brings a curated set of helpers, step definitions, and patterns that can be shared across projects, helping teams standardize testing without being locked into a single framework.


## Modules
This is a Maven multi-module project:
- test-core: Core utilities shared by features and step libraries.
- test-feature: Cucumber/BDD-oriented steps and state helpers for common concerns (HTTP, JWT, DB, mock services, AWS stubs, etc.).


## Supported/Targeted Technologies
Planned and/or partially implemented support for:
- Application frameworks: Spring, Spring Boot, Dropwizard, Guice
- Data and migrations: MySQL (embedded), Flyway
- HTTP testing: Apache HttpClient, WireMock
- BDD: Cucumber (to be modernized to io.cucumber)
- Cloud/platform helpers: AWS service stubs (S3, SQS, SNS, SES, Lambda, DynamoDB, Kinesis, ElastiCache)


## Quick Start (Build and Test)
Requirements:
- Java 17 (LTS)
- Maven 3.8+ (3.9+ recommended)
- Docker running locally (for emulator-backed tests via Testcontainers)

Build the whole project:

```bash
mvn -q -DskipTests clean install
```

Run tests (no provider capabilities yet):

```bash
mvn -q -DskipTests=false test
```

Select a cloud provider and mode in Cucumber (example):

```gherkin
Given cloud provider is "aws"
And cloud mode is "emulator"
And cloud region is "eu-west-1"
```

How provider adapters are discovered:
- Adapters implement `org.deveasy.test.core.cloud.spi.CloudAdapter` and are discovered via Java `ServiceLoader`.
- If `test-cloud-aws` is on the classpath, the AWS adapter will be picked up automatically.

If Docker is not running or no adapter is present, steps will fail gracefully with a helpful message.


## Troubleshooting (Known Issues in Current Version)
This repo uses older dependencies and group IDs that may no longer resolve from Maven Central, for example:
- info.cukes (Cucumber 1.x) artifacts
- com.wix:wix-embedded-mysql:1.0.1
- com.spotify:docker-maven-plugin:1.0.0
- Older Flyway and plugin versions

Until the upgrade is completed, builds may fail to resolve some of the above. If you’re trying to experiment right now, you can:
- Build with offline-friendly mirrors or your company’s artifact proxy if it contains the legacy artifacts.
- Comment out failing dependencies temporarily while exploring code.

Better yet—help us modernize (see Roadmap and Contributing)!


## Roadmap (Help Wanted)
We want to bring this project up to date and make it welcoming for contributors. Proposed steps:
1) Modernize build and dependencies
   - Move from info.cukes to io.cucumber (Cucumber 7/8+)
   - Move to JUnit 5 (JUnit Jupiter)
   - Update Flyway, WireMock, HttpClient, Guava, Jackson to current stable versions
   - Adopt Java 17 LTS as baseline (toolchains for Java 8 if necessary)
   - Replace deprecated plugins (e.g., com.spotify:docker-maven-plugin) with maintained alternatives (e.g., jib, or testcontainers for integration testing)
2) Improve developer experience
   - Standard test fixtures (DB, migrations, mock servers)
   - Consistent package naming and API polish
   - Richer examples and documentation
3) CI/CD
   - Add GitHub Actions for build and test on pull requests
   - Add code style and static analysis (Spotless/Checkstyle, ErrorProne, etc.)
4) Releases
   - Set up automated releases to Maven Central (via OSSRH) with signed artifacts

If you agree with this direction or want changes, please open a discussion or issue.


## Contributing
We welcome contributions! Here’s how to get started:
1) Fork the repo and create a feature branch from main.
2) If proposing large changes (e.g., moving to io.cucumber), please open a design issue first to align on the approach.
3) Follow conventional commit messages where possible (e.g., feat:, fix:, docs:, chore:).
4) Write tests for new functionality or behavior changes.
5) Ensure `mvn -q -DskipTests clean install` and `mvn test` succeed locally.
6) Open a Pull Request with a clear description and checklist of changes.

We will add a CODE_OF_CONDUCT and CONTRIBUTING guide as part of the modernization. For now, please keep discussions respectful and constructive.


## Using the Features (high-level)
The `test-feature` module includes Cucumber step definitions and scenario state helpers for common tasks such as:
- Starting/stopping application under test
- Interacting with HTTP endpoints
- Working with JSON Web Tokens
- Managing databases and migrations
- Mocking external services (e.g., via WireMock)
- Interacting with AWS-like services (using local stubs or emulators)

Once dependencies are modernized (io.cucumber + JUnit 5), we will publish concrete examples and ready-to-use templates in the repository.


## License
Apache License 2.0. See LICENSE file for details.


## Acknowledgements
- Inspired by years of testing across diverse Java stacks
- Thanks to the Cucumber, WireMock, Flyway, and wider Java OSS communities


---
Last updated: 2025-11-05
