# 3. Capability Interfaces for Cloud Services

Date: 2025-11-01
Status: Accepted

## Context
We need a provider-neutral API surface that allows tests to use common cloud capabilities without direct dependency on vendor SDKs. Providers may support only a subset (e.g., S3/Blob storage and SQS/Queue first), so the design must enable partial, incremental adoption.

## Decision
Define minimal, cohesive capability interfaces in `test-core` (e.g., `BlobStorage`, `Queue`, `PubSub`, `NoSqlTable`). The provider adapter implements only the capabilities it supports. The `CloudAdapter` acts as the capability registry and returns `Optional` capability implementations.

Evolution strategy:
- Prefer additive changes; extend via new interfaces rather than breaking changes.
- Use Java default methods only for truly safe, trivial behaviors; avoid masking unsupported features.
- Keep capability methods small and task-focused to reduce surface area and vendor lock-in.

## Consequences
+ Providers can be added iteratively without touching consumer tests.
+ Clear separation keeps vendor SDKs out of `test-core`.
+ Fewer breaking changes by evolving via new capabilities.
- Potential fragmentation if too many small interfaces are created.
- Capability discovery adds a bit of indirection for users.

---
