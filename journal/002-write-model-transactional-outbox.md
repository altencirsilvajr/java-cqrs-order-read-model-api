# 002 — Write model e transactional outbox

## Commit

`feat: persist orders with transactional outbox`

## Objetivo

Aceitar um pedido por REST e persistir o write model e `OrderPlaced.v1` atomicamente.

## Implementação

- Introduz Spring Boot 3.5.7, Java 21, domínio de pedido e validação HTTP.
- Configura JPA do write model e migration PostgreSQL.
- Persiste pedido e evento serializado na mesma transação.
- Adiciona testes de domínio, caso de uso e regras ArchUnit.

## Rastreabilidade ADR

Novo ADR criado: ADR-0001 - Separar os bancos de escrita e leitura; ADR-0002 - Publicar OrderPlaced no Kafka por transactional outbox.

## Verificação

- `JAVA_HOME=/opt/homebrew/opt/openjdk@21 ./mvnw test`: aprovado.
- `./scripts/traceability-gate.sh --staged`: aprovado.
- `git diff --cached --check`: aprovado.

## Alternativas e trade-offs

O evento carrega um snapshot mínimo necessário à projeção; evita consulta de retorno ao write model, aceitando evolução explícita de schema.

## Próximo passo

Publicar a outbox no Kafka e construir a projeção idempotente no banco de leitura.
