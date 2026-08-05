# Java CQRS Order Read Model API

Laboratório vertical Java 21 que demonstra CQRS, transactional outbox, Kafka, projeção idempotente e consistência eventual sem mascarar seus custos. Foi criado como evidência prática para entrevistas Backend/Full Stack Java Senior.

```text
POST /api/orders -> PostgreSQL write + outbox (uma transação)
                  -> relay at-least-once -> Kafka OrderPlaced.v1
                  -> projector idempotente -> PostgreSQL read
GET /api/orders/{id} -> somente read model
```

## O que é possível observar

- `202 Accepted` devolve a URL de acompanhamento sem prometer leitura imediata.
- `PENDING`, `PROJECTED` e `STALE` tornam a convergência parte do contrato.
- Bancos de escrita e leitura possuem DataSources, JPA contexts, migrations e credenciais independentes.
- O pedido e a outbox são atômicos; Kafka pode ficar indisponível sem perder o comando aceito.
- A publicação é at-least-once e o projetor deduplica por `eventId` na mesma transação da projeção.
- Métricas, health probes, OpenAPI, Problem Details e correlation ID tornam o fluxo operável.

## Stack

- Java 21, Spring Boot 3.5.7, Spring MVC, Spring Data JPA/Hibernate e Flyway
- Kafka, dois PostgreSQL 17, Docker Compose
- Angular 22.1 standalone, TypeScript 6 e Vitest
- JUnit 5, Mockito, AssertJ, ArchUnit e Testcontainers
- GitHub Actions; exemplos equivalentes de Jenkins e GitLab CI
- Kubernetes base e overlay OpenShift com ConfigMap, Secret reference, probes, limits e Route

## Executar

Pré-requisitos: Docker Desktop. O caminho mais curto sobe toda a topologia e a interface:

```bash
./scripts/start-local.sh
```

- Dashboard: `http://localhost:4200`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health: `http://localhost:8080/actuator/health`
- Prometheus: `http://localhost:8080/actuator/prometheus`

Para encerrar preservando os volumes anônimos do laboratório:

```bash
./scripts/stop-local.sh
```

Também é possível subir somente `write-db`, `read-db` e `kafka`, executar a API com `JAVA_HOME=/opt/homebrew/opt/openjdk@21 ./mvnw spring-boot:run` e a UI com `PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend start`.

## Contratos principais

| Método | Rota | Semântica |
|---|---|---|
| `POST` | `/api/orders` | aceita comando e retorna status URL |
| `GET` | `/api/orders/{id}` | lê somente a projeção; 404 durante a janela eventual |
| `GET` | `/api/orders/{id}/projection-status` | estado e lag explícitos |
| `GET` | `/api/observability/overview` | contadores do pipeline para o dashboard |

O schema versionado do evento está em `docs/contracts/order-placed.v1.schema.json`. Entradas inválidas usam RFC 9457 Problem Details.

## Verificação

```bash
JAVA_HOME=/opt/homebrew/opt/openjdk@21 ./mvnw verify
PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend ci
PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend test -- --watch=false
PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend run build
docker compose config --quiet
```

O teste de integração inicia dois PostgreSQL e Kafka reais. Quando Docker não está disponível, Testcontainers pula apenas esse teste; testes unitários e arquiteturais continuam obrigatórios.

## Kubernetes e OpenShift

O deployment espera um Secret externo chamado `cqrs-orders-databases` com `WRITE_DB_URL`, `WRITE_DB_USER`, `WRITE_DB_PASSWORD`, `READ_DB_URL`, `READ_DB_USER` e `READ_DB_PASSWORD`. Nenhum segredo é versionado.

```bash
kubectl apply -k deploy/base
oc apply -k deploy/openshift
```

Kafka e os bancos são dependências externas no cluster. A réplica padrão é uma porque múltiplos relays ainda exigiriam `SKIP LOCKED`/claim explícito na outbox.

## Decisões defendíveis em entrevista

- Dois bancos físicos deixam a independência de CQRS verificável e impedem join acidental entre modelos.
- Outbox resolve o dual write banco/broker; ela não oferece exactly-once e por isso a deduplicação está no consumidor.
- O endpoint de comando retorna `202`, enquanto leitura e status têm contratos distintos.
- A UI faz polling curto deliberadamente; WebSocket pertence ao laboratório específico de realtime.
- O modelo representa convivência de modernização: produtores legados podem publicar o mesmo contrato Kafka enquanto o novo read model assume consultas gradualmente.

ADRs registram as decisões duráveis; Journals ligam cada commit a comandos realmente executados. Consulte também `PROJECT_VISION.md` e `docs/sdd/001-cqrs-order-read-model-lab.md`.

## Limites conscientes

- Um único tipo de evento e uma única evolução de pedido mantêm o foco no pipeline.
- Não há pagamento, estoque, saga, autenticação ou produto de e-commerce.
- Em produção, a outbox receberia claim com `FOR UPDATE SKIP LOCKED`, política de retenção, DLQ e alertas de SLO de lag.
