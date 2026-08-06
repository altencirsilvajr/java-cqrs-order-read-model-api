# 008 - Endurecer toolchain de CI

## Commit

`ci: eliminate toolchain warnings`

## Objetivo

Remover alertas de dependencias Angular e avisos de runtime das Actions.

## Implementacao

- Substitui a dependencia transitiva vulneravel por `@hono/node-server` 2.1.0.
- Aprova de forma versionada os scripts de instalacao revisados.
- Atualiza Actions para Node 24 e adiciona `npm audit` ao pipeline.

## Rastreabilidade ADR

Decisao local sem ADR novo: endurecimento de supply chain sem alterar CQRS ou projecoes.

## Verificacao

- `npm audit`: 0 vulnerabilidades e nenhum script pendente.
- Teste frontend: 1 aprovado; build Angular aprovado.
- Workflow validado como YAML e sem Actions antigas.
