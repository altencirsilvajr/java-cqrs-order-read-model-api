# SDD 001 — Laboratório CQRS de pedidos

## Resultado esperado

Uma API Spring Boot 3.5 em Java 21 cria pedidos no banco de escrita e registra `OrderPlaced` em uma outbox na mesma transação. Um relay publica mensagens pendentes no Kafka. Um projetor idempotente mantém `OrderSummary` em outro PostgreSQL. A leitura informa `PENDING`, `PROJECTED` ou `STALE` e métricas tornam o atraso observável.

## Contratos públicos

- `POST /api/orders`: aceita cliente e itens, retorna `202 Accepted`, identificador e URL de status.
- `GET /api/orders/{id}`: consulta exclusivamente o read model e retorna `404` enquanto não existir projeção.
- `GET /api/orders/{id}/projection-status`: compara os checkpoints de escrita e leitura e informa estado e atraso.
- `GET /api/observability/overview`: resume pedidos, outbox e projeções para a interface de aprendizado.
- Evento `OrderPlaced.v1`: envelope versionado com identificadores, instante, sequência e payload.

## Restrições

- Write e read model usam bancos PostgreSQL separados e credenciais/configurações independentes.
- O write model nunca depende do read model para aceitar comandos.
- A outbox é gravada na transação do pedido e publicada com semântica at-least-once.
- O projetor registra o `eventId` processado na mesma transação da projeção.
- Código e commits em inglês; documentação em PT-BR.
- Java 21, Spring Boot 3.5.x, Maven Wrapper e Angular 22.

## Evidência de aceite

- Testes unitários e de arquitetura passam sem infraestrutura externa.
- Testes de integração com PostgreSQL e Kafka validam o fluxo real quando Docker está disponível.
- O build Angular e a validação do Docker Compose passam.
- OpenAPI, Problem Details, health checks, métricas e logs com correlation ID estão disponíveis.
