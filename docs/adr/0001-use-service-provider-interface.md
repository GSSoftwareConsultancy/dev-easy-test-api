# 1. Use Java SPI for Cloud Provider Adapters

Date: 2025-11-01
Status: Accepted

## Context
Need pluggable architecture for multiple cloud providers (AWS, Azure, GCP) without coupling test-core to specific implementations.

## Decision
Use Java ServiceLoader with CloudAdapter SPI. Providers register via META-INF/services/.

## Consequences
+ Clean separation of concerns
+ Easy to add new providers
+ No compile-time dependencies on AWS/Azure/GCP SDKs in test-core
- Runtime discovery requires proper classpath setup
- Slightly more complex for users to understand initially

---
