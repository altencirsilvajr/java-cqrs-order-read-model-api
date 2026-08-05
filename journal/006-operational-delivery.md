# 006 — Entrega operacional e documentação

## Commit

`chore: add reproducible delivery assets`

## Objetivo

Permitir build, validação, execução e deploy reproduzíveis nas ferramentas pedidas pelas vagas-alvo.

## Implementação

- Adiciona Jenkinsfile e GitLab CI equivalentes; GitHub Actions sera publicado em incremento atomico separado.
- Empacota frontend Nginx e o conecta à topologia Compose.
- Entrega manifests Kubernetes/OpenShift sem versionar segredos.
- Versiona JSON Schema do evento e documenta operação e decisões de entrevista.

## Rastreabilidade ADR

Decisao local sem ADR novo: pipelines e manifests materializam as decisões existentes sem alterar a arquitetura do produto.

## Verificação

- `JAVA_HOME=/opt/homebrew/opt/openjdk@21 ./mvnw verify`: aprovado com 9 testes, incluindo Testcontainers.
- `PATH=/opt/homebrew/opt/node@24/bin:$PATH npm --prefix frontend ci && npm --prefix frontend test -- --watch=false && npm --prefix frontend run build`: aprovado com 1 teste e bundle de produção.
- `docker compose config --quiet`: aprovado.
- `ruby -e 'require "yaml"; Dir["deploy/**/*.yml"].each { |f| YAML.load_stream(File.read(f)) }'`: aprovado.
- `kubectl kustomize deploy/base` e `kubectl kustomize deploy/openshift`: aprovados.
- `./scripts/traceability-gate.sh --staged`: aprovado.
- `git diff --cached --check`: aprovado.

## Alternativas e trade-offs

Os manifests referenciam serviços gerenciados de Kafka/PostgreSQL e um Secret provisionado fora do Git; isso evita credenciais de demonstração no cluster e mantém o laboratório focado.

## Próximo passo

Executar a revisão final de arquitetura, contratos e evidências antes da entrega.
