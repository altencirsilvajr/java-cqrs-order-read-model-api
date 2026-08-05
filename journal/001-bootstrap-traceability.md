# 001 — Bootstrap da rastreabilidade

## Commit

`chore: bootstrap tracked development`

## Objetivo

Estabelecer a fonte de verdade de escopo e os gates auditáveis antes da implementação.

## Implementação

- Registra visão, SDD ativo, processo e instruções locais.
- Adiciona um gate executável que exige exatamente um Journal por commit.

## Rastreabilidade ADR

Decisao local sem ADR novo: o incremento apenas estabelece governança de desenvolvimento; decisões arquiteturais serão registradas quando implementadas.

## Verificação

- `./scripts/traceability-gate.sh --staged`: aprovado para os arquivos do bootstrap.
- `git diff --cached --check`: aprovado sem erros de whitespace.

## Alternativas e trade-offs

O trabalho será publicado diretamente em `main`, conforme a entrega solicitada, preservando commits atômicos em vez de usar uma branch temporária.

## Próximo passo

Criar o esqueleto Spring Boot test-first e os limites arquiteturais do laboratório.
