# 003 — Kafka e read model idempotente

## Commit

`feat: project Kafka events into an idempotent read model`

## Objetivo

Fazer pedidos aceitos convergirem para um banco de leitura independente com atraso e duplicatas observáveis.

## Implementação

- Adiciona migrations e configuração independente dos dois PostgreSQL.
- Publica a outbox no Kafka com acknowledgement antes de marcar o evento.
- Consome `OrderPlaced.v1` e grava projeção e checkpoint na mesma transação.
- Expõe leitura, status de projeção, overview e métricas Micrometer.
- Propaga correlation ID nos logs e respostas.

## Rastreabilidade ADR

Novo ADR criado: ADR-0003 - Tornar projeção idempotente e atraso explícito. ADR aplicado: ADR-0001 e ADR-0002.

## Verificação

- `JAVA_HOME=/opt/homebrew/opt/openjdk@21 ./mvnw test`: aprovado.
- `./scripts/traceability-gate.sh --staged`: aprovado.
- `git diff --cached --check`: aprovado.

## Alternativas e trade-offs

O relay espera acknowledgement em cada mensagem, reduzindo throughput em troca de um fluxo pequeno e fácil de explicar; batching assíncrono seria uma evolução mensurada.

## Próximo passo

Validar o fluxo completo com Testcontainers e entregar execução local reproduzível.
