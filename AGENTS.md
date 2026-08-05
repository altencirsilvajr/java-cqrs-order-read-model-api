# Repository instructions

- Keep production code, identifiers and commits in English; write documentation in PT-BR.
- Work on `main` in atomic vertical slices because this portfolio repository publishes its learning history directly.
- Every substantive non-merge commit must contain exactly one changed file under `journal/`.
- Add an ADR only for a durable architectural decision and reference it from the increment journal.
- Preserve the scope and acceptance criteria in `docs/sdd/001-cqrs-order-read-model-lab.md`.
- The write PostgreSQL database is authoritative for commands; the read PostgreSQL database is a disposable projection.
- Kafka delivery is at-least-once. Consumers must remain idempotent and projection lag must remain observable.

## Verification commands

```bash
./scripts/traceability-gate.sh --staged
JAVA_HOME=/opt/homebrew/opt/openjdk@21 ./mvnw verify
npm --prefix frontend ci
npm --prefix frontend test -- --watch=false
npm --prefix frontend run build
docker compose config --quiet
```
