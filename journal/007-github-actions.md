# 007 - Publicar pipeline GitHub Actions

## Commit

`ci: publish github actions workflow`

## Objetivo

Executar automaticamente os gates do laboratorio CQRS no host publico.

## Implementacao

- Adiciona pipeline Java 21/Maven e Angular/Node 24.
- Valida rastreabilidade, PostgreSQL/Kafka via testes e configuracao Compose.
- Mantem permissao do workflow somente para leitura.

## Rastreabilidade ADR

Decisao local sem ADR novo: o pipeline automatiza verificacoes existentes sem alterar os modelos de escrita ou leitura.

## Verificacao

- Ruby/Psych carregou o YAML sem erro de sintaxe.
- `./mvnw verify`: aprovado com 9 testes.
- `npm --prefix frontend ci`, teste CI e build: aprovados com 1 teste.
- `./scripts/traceability-gate.sh HEAD`: aprovado no incremento operacional anterior.

## Alternativas e trade-offs

Testcontainers aumenta a duracao do pipeline, mas valida a semantica real de PostgreSQL e Kafka.

## Proximo passo

Acompanhar a primeira execucao publica do pipeline.
