# 005 — Painel Angular de aprendizado

## Commit

`feat: visualize CQRS convergence in Angular`

## Objetivo

Permitir que um avaliador crie um pedido e observe visualmente a convergência assíncrona.

## Implementação

- Adiciona Angular 22.1 standalone com Node 24.
- Mostra pipeline, contadores, estado da projeção e atraso real retornado pela API.
- Mantém regras no backend e configura proxy local.
- Cobre renderização e chamada inicial com teste Vitest.

## Rastreabilidade ADR

Novo ADR criado: ADR-0004 - Usar Angular somente como painel de aprendizado.

## Verificação

- `PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend ci`: aprovado.
- `PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend test -- --watch=false`: aprovado.
- `PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend run build`: aprovado.
- `./scripts/traceability-gate.sh --staged`: aprovado.
- `git diff --cached --check`: aprovado.

## Alternativas e trade-offs

Polling a cada 500 ms é suficiente para tornar o lag visível no laboratório; WebSocket adicionaria outra especialidade já coberta por projeto próprio.

## Próximo passo

Adicionar pipelines, manifests e documentação operacional final.
