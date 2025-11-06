# Project Status and Roadmap

Last updated: 2025-11-06

## Snapshot
- Baseline: Java 17; multi-module Maven build is green locally and in GitHub Actions (Docker/Testcontainers required).
- Cloud SPI in place (`test-core`), provider adapter for AWS present (`test-cloud-aws`).
- S3 capability implemented (`BlobStorage`) with a passing integration test via LocalStack.
- CI standardized on GitHub Actions; legacy Travis/Coveralls removed.

## Current Milestones
- M1 — Docs & Examples: ✓ complete
  - README quick start, examples scaffold, troubleshooting
- M2 — AWS S3 minimal (BlobStorage): ✓ complete
  - Ensure bucket, put/get, list, exists; IT against LocalStack
- M3 — Coverage & Gates: * in progress
  - JaCoCo (report-only), Spotless + .editorconfig, Checkstyle, Maven Enforcer
- M4 — AWS SQS minimal (Queue): planned
  - Ensure queue, send, receive-one (timeout), delete; tests (JUnit + Cucumber)
- M5 — CI hardening: planned
  - Split jobs (lint/static/deps/tests), artifacts, badges
- M6 — Private alpha packaging: planned
  - 0.3.0-alpha1-private.1 to private registry; consumption notes

Legend: ✓ done, * in progress

## Next Actions (Short Term)
1) Add JaCoCo (report-only) and publish HTML reports from Actions
2) Introduce Spotless + .editorconfig (fail fast), Checkstyle (pragmatic), Maven Enforcer (Java 17, convergence, duplicates, reproducible builds)
3) Build hygiene: upgrade root maven-compiler-plugin to 3.11.0; modern surefire (3.2.5)
4) README: add concise "Run AWS S3 example" + expand Troubleshooting
5) Implement AWS SQS minimal + tests; un-pend example queue scenario

## Vision
Build a tiny, pragmatic Java testing library that:
- Works out-of-the-box with emulator-first strategy via Testcontainers
- Stays provider-agnostic at the core with pluggable adapters (AWS → Azure → GCP)
- Offers a consistent developer experience across JUnit 5 and Cucumber
- Keeps the core slim, with vendor SDKs isolated in provider modules

## Roadmap (v0.3 → v0.6)
- v0.3 (private alpha): AWS S3 + SQS minimal, CI, coverage reports, basic gates
- v0.4: Pub/Sub/DynamoDB basics, minimal JUnit 5 extension (`@WithCloud`)
- v0.5: Azure adapter baseline (Azurite + docs for Service Bus/Cosmos constraints)
- v0.6: GCP adapter baseline (official emulators), JSON matchers improvements

## CI
- GitHub Actions only (`.github/workflows/build.yml`)
- Requires Docker for emulator-backed tests
- Artifacts: surefire/failsafe (and soon JaCoCo + OWASP reports)

## Quality Gates (target state)
- Formatting: Spotless + .editorconfig (enforced)
- Style: Checkstyle (pragmatic rules; fail on critical)
- Build hygiene: Maven Enforcer (Java 17, convergence, duplicates, reproducible builds)
- Static analysis: Error Prone (soft → harden later)
- Coverage: JaCoCo (report-only → thresholds later)
- Supply chain: OWASP Dependency-Check (report-only → fail on High/Critical later)

## How to Verify Locally
- Prereqs: Java 17, Maven 3.8+, Docker running
- Command: `mvn -q -DskipTests=false verify`
- Expected: LocalStack container starts; S3 IT passes; build is green

## Contact
Internal phase: trunk-based development with small, frequent commits to `main`. Open a PR if changing build/CI/gates; keep main green.
