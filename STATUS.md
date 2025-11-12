# Project Status and Roadmap

Last updated: 2025-11-08

## Snapshot
- Baseline: Java 17; multi-module Maven build is green locally and in GitHub Actions (Docker/Testcontainers required).
- Cloud SPI in place (`test-core`), provider adapter for AWS present (`test-cloud-aws`).
- S3 capability implemented (`BlobStorage`) with a passing integration test via LocalStack.
- SQS capability implemented (`Queue`) with a passing integration test via LocalStack.
- CI standardized on GitHub Actions; legacy Travis/Coveralls removed.

## Current Milestones
- M1 — Docs & Examples: ✓ complete
  - README quick start, examples scaffold, troubleshooting
- M2 — AWS S3 minimal (BlobStorage): ✓ complete
  - Ensure bucket, put/get, list, exists; IT against LocalStack
- M3 — Coverage & Gates: * in progress
  - JaCoCo (report-only), Spotless + .editorconfig, Checkstyle, Maven Enforcer
- M4 — AWS SQS minimal (Queue): ✓ complete
  - Ensure queue, send, receive-one (timeout), delete; JUnit tests
- M5 — CI hardening: * in progress
  - Split jobs (lint/static/tests/coverage/deps): ✓ done
  - Artifacts: test reports + coverage HTML: * in progress
  - Badges and dependency reports: planned
- M6 — Private alpha packaging: planned
  - 0.3.0-alpha1-private.1 to private registry; consumption notes

Legend: ✓ done, * in progress

## Next Actions (Short Term)
1) Keep CI split stable (two green runs) and upload coverage HTML per run
2) Finalize provider-neutral Cucumber suites (S3 + SQS) under `test-feature` and document run steps
3) Stabilize gates: Spotless/Checkstyle/Enforcer across all modules (two consecutive green runs)
4) Add soft static & supply chain checks: Error Prone (WARN), OWASP (report-only)
5) Prepare private alpha packaging and README private consumption notes

## Vision
Build a tiny, pragmatic Java testing library that:
- Works out-of-the-box with emulator-first strategy via Testcontainers
- Stays provider-agnostic at the core with pluggable adapters (AWS → Azure → GCP)
- Offers a consistent developer experience across JUnit 5 and Cucumber
- Keeps the core slim, with vendor SDKs isolated in provider modules

## Roadmap (v0.3 → v0.6)
- v0.3 (private alpha): AWS S3 + SQS minimal, CI split, coverage reports, basic gates
- v0.4: Pub/Sub/DynamoDB basics, minimal JUnit 5 extension (`@WithCloud`)
- v0.5: Azure adapter baseline (Azurite + docs for Service Bus/Cosmos constraints)
- v0.6: GCP adapter baseline (official emulators), JSON matchers improvements

## CI
- GitHub Actions only (`.github/workflows/build.yml`), split into jobs: lint → static → tests → coverage (+ deps)
- Requires Docker for emulator-backed tests
- Artifacts: surefire/failsafe and coverage (JaCoCo HTML); dep-check to be added

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
- Expected: LocalStack container starts; S3 & SQS ITs pass; build is green

## Contact
Internal phase: trunk-based development with small, frequent commits to `main`. Open a PR if changing build/CI/gates; keep main green.
