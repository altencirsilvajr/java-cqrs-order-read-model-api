# Visão do CQRS Order Read Model API

Este repositório é um laboratório vertical Java 21 para demonstrar, de maneira executável e defendível em entrevistas Senior, como um pedido atravessa write model, transactional outbox, Kafka e read model sem esconder a consistência eventual.

O fluxo deliberadamente pequeno é: criar um pedido, persistir `OrderPlaced` na mesma transação, publicar a outbox no Kafka, projetar um resumo idempotente em outro PostgreSQL e consultar o resultado com atraso de projeção explícito.

O avaliador deve conseguir observar as fronteiras, provocar a janela de inconsistência, repetir mensagens sem duplicar a projeção e relacionar cada decisão ao histórico Git.

Não fazem parte do produto pagamentos, estoque, cancelamento, autenticação, sagas ou um e-commerce completo. A interface Angular é um painel mínimo de aprendizado, não uma aplicação comercial.
