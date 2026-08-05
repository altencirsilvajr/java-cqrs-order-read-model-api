# 004 — Fluxo com infraestrutura real

## Commit

`test: verify CQRS flow with PostgreSQL and Kafka`

## Objetivo

Demonstrar que o fluxo completo converge usando duas instâncias PostgreSQL e um broker Kafka reais.

## Implementação

- Adiciona teste de integração Testcontainers do POST até o read model.
- Entrega Compose com bancos, Kafka e API configurados por ambiente.
- Cria imagem Java 21 multi-stage e não-root.

## Rastreabilidade ADR

ADR aplicado: ADR-0001 - Separar os bancos de escrita e leitura; ADR-0002 - Publicar OrderPlaced no Kafka por transactional outbox; ADR-0003 - Tornar projeção idempotente e atraso explícito.

## Verificação

- `JAVA_HOME=/opt/homebrew/opt/openjdk@21 ./mvnw verify`: aprovado, incluindo Testcontainers.
- `docker compose config --quiet`: aprovado.
- `./scripts/traceability-gate.sh --staged`: aprovado.
- `git diff --cached --check`: aprovado.

## Alternativas e trade-offs

O teste usa um broker e dois containers de banco, aumentando o tempo de CI em troca de evidência na fronteira real de persistência e mensageria.

## Próximo passo

Adicionar painel Angular mínimo para observar aceitação, outbox e convergência.
