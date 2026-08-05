# ADR-0004 — Usar Angular somente como painel de aprendizado

## Status

Aceito

## Contexto

O portfólio precisa demonstrar Angular sem transformar o laboratório de CQRS em um produto amplo ou duplicar regras do backend.

## Decisão

Construir uma SPA Angular 22.1 standalone que cria um pedido, consulta a URL de status retornada pela API e mostra os quatro estágios e contadores. Toda regra e classificação da projeção permanecem no backend.

## Consequências

- O fluxo distribuído fica demonstrável visualmente.
- Existe um segundo build e uma pequena manutenção de contrato TypeScript.
- A UI não oferece edição, autenticação ou recursos comerciais.

## Alternativas rejeitadas

### Dashboard embutido no backend

Reduz ferramentas, mas não evidencia Angular 16+ pedido nas vagas-alvo.
