# 009 - Reparar lockfile do frontend

## Commit

`fix: synchronize frontend lockfiles`

## Objetivo

Restaurar o lockfile completo e consistente com o manifesto auditado.

## Implementacao

- Recupera a copia integral preservada e regenera seu metadata com npm 11.17.

## Rastreabilidade ADR

Decisao local sem ADR novo: reparo mecanico sem alterar comandos ou projecoes.

## Verificacao

- Lockfile JSON valido; `npm ci` aprovado sem warnings.
- Audit: 0 vulnerabilidades; nenhum script pendente.
