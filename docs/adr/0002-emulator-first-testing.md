# 2. Emulator-First Testing Strategy

Date: 2025-11-01
Status: Accepted

## Context
Integration tests need cloud services but hitting real cloud is slow/expensive.

## Decision
Default to EMULATOR mode (LocalStack, Azurite) with explicit opt-in to REAL mode.

## Consequences
+ Fast, free, offline testing
+ Consistent CI environment
+ Lower cloud costs
- Emulators may not match production exactly
- Requires Docker/Testcontainers

---
