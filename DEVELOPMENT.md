# Processo de desenvolvimento

- Evoluir o laboratório em fatias verticais pequenas e reversíveis diretamente na `main`.
- Incluir exatamente um Journal em cada commit substantivo não-merge.
- Declarar em todo Journal se o incremento cria, aplica ou dispensa um ADR.
- Criar ADR apenas para decisões arquiteturais duráveis e difíceis de reverter.
- Manter o SDD ativo e o README coerentes com o comportamento entregue.
- Testar pelo contrato público antes de implementar o comportamento.
- Executar o gate de rastreabilidade, testes focados e verificações proporcionais antes do commit.
- Publicar commits incrementalmente sem apagar o histórico de aprendizado.
