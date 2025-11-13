# 5. Dependency Modernization and Baseline Strategy

Date: 2025-11-01
Status: Accepted

## Context
Keeping a healthy developer experience and reliable CI requires a coherent dependency baseline across modules. The project spans JDK, Maven plugins, testing frameworks (JUnit/Cucumber), static analysis (Spotless/Checkstyle), Testcontainers, and provider SDKs (e.g., AWS SDK v2). We need clear rules to modernize safely without breaking consumers or the provider‑neutral API.

## Decision
- Baseline runtimes and tools
  - Java 17 (Temurin recommended). Enforced via Maven Enforcer. 
  - Maven 3.9+; use reproducible builds with `project.build.outputTimestamp`.
  - Surefire/Failsafe: 3.2.5 as the global default; prefer JUnit Platform.
- Test frameworks
  - Prefer JUnit 5 (Jupiter) for all new tests. Keep JUnit 4 only where legacy exists; migrate opportunistically.
  - Cucumber runs on JUnit Platform; avoid mixing engines in one module unless necessary.
- Dependency management
  - Centralize versions in parent `dependencyManagement`. Use BOMs where available (e.g., AWS SDK v2 BOM, Testcontainers BOM).
  - Keep vendor SDKs confined to provider modules (e.g., `test-cloud-aws`). `test-core` must remain provider‑agnostic (no vendor SDK deps).
  - `test-feature` depends on provider modules with `scope=test` only to avoid duplicate classes/conflicts.
- Quality gates
  - Formatting with Spotless (Google Java Format) and Checkstyle for style; both enforced in `verify`.
  - Maven Enforcer: Java version, dependency convergence, and reproducible build rules.
- Upgrade policy
  - Patch upgrades: as soon as practical, especially for security fixes.
  - Minor upgrades: batched, monthly or as needed; require CI green.
  - Major upgrades: gated by ADRs or release notes review; may require migration tasks and deprecation windows.

## Consequences
+ Predictable builds and fewer dependency conflicts across modules.
+ Clear separation keeps core module lean and SDK‑free.
+ Faster iteration on tests with modern JUnit Platform and plugins.
- Occasional churn when BOMs bump transitives; need vigilance for compatibility.
- Some duplication of test dependencies across modules to respect separation of concerns.

---
