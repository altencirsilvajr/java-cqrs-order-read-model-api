# ADR-0001 — Separar os bancos de escrita e leitura

## Status

Aceito

## Contexto

CQRS perde valor pedagógico quando dois modelos são apenas tabelas acessadas pelo mesmo contexto transacional.

## Decisão

Usar dois PostgreSQL independentes, com DataSources, EntityManagers, migrations e credenciais separados. Comandos dependem somente do write model; consultas públicas de pedido dependem somente do read model.

## Consequências

- A independência e a janela de inconsistência ficam reais e observáveis.
- O ambiente local e os testes precisam operar duas instâncias lógicas.
- O read model pode ser reconstruído sem afetar pedidos aceitos.

## Alternativas rejeitadas

### Um banco com schemas diferentes

Reduz infraestrutura, mas permite acoplamento transacional acidental e enfraquece o laboratório.
