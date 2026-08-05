# ADR-0003 — Tornar projeção idempotente e atraso explícito

## Status

Aceito

## Contexto

A outbox entrega ao menos uma vez e o read model converge depois do comando. Ocultar duplicatas e atraso produz uma API enganosa.

## Decisão

Registrar cada `eventId` processado na mesma transação PostgreSQL que atualiza `OrderSummary`. Expor status separado com `PENDING`, `PROJECTED`, `STALE` e atraso em milissegundos. `GET /api/orders/{id}` lê somente a projeção e retorna 404 antes da convergência.

## Consequências

- Redelivery não duplica efeitos.
- Clientes precisam consultar a URL de status retornada pelo comando.
- A tabela de checkpoints cresce e requer política de retenção em uma operação real.

## Alternativas rejeitadas

### Atualização síncrona do read model

Eliminaria a janela de inconsistência e, junto dela, a demonstração honesta de CQRS distribuído.
