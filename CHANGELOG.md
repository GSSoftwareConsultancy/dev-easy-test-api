# Changelog

## [0.3.0-alpha1-private.1] - 2025-02-24

### Added
- AWS BlobStorage (S3) capability with LocalStack support
- AWS Queue (SQS) capability with delete-on-receive semantics
- AWS PubSub (SNS+SQS) capability
- AWS NoSqlTable (DynamoDB) capability
- Core SPI for cloud provider adapters
- JUnit 5 @WithCloud extension for capability injection
- Cucumber step definitions for all capabilities
- Quality gates: JaCoCo coverage, Spotless, Checkstyle, Error Prone, OWASP

### Changed
- Migrated from Cucumber 6 to Cucumber 7
- Upgraded to JUnit 5 Jupiter
- Modernized all dependencies

### Known Limitations
- Only AWS provider implemented
- EMULATOR mode only (REAL mode needs additional testing)
- No Azure or GCP adapters yet
