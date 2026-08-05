# ADR-0002 — Publicar OrderPlaced no Kafka por transactional outbox

## Status

Aceito

## Contexto

Gravar o pedido e publicar diretamente no Kafka cria dual write: falhas entre as operações perdem eventos ou anunciam pedidos inexistentes.

## Decisão

Persistir pedido e evento versionado na mesma transação PostgreSQL. Um relay assíncrono publica eventos pendentes no Kafka e marca a outbox depois do acknowledgement. A entrega aceita duplicatas.

## Consequências

- A aceitação do comando não depende da disponibilidade do Kafka.
- Há atraso entre pedido e publicação.
- Falha após publicação e antes da marcação gera duplicata; consumidores precisam ser idempotentes.

## Alternativas rejeitadas

### Transação distribuída

Acopla banco e broker a 2PC, aumenta custo operacional e não representa a prática mais comum das vagas-alvo.
