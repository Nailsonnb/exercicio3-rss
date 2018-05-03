# Tarefa #3 - RSS 

A ideia deste exercício é aplicar os conceitos de criação e consumo de conteúdo a partir de um `ContentProvider` e agendamento de tarefas via `JobScheduler`. 

A partir da resolução da [Tarefa #2](https://github.com/if1001/exercicio2-rss), siga os passos na ordem sugerida e marque mais abaixo, na sua resposta, quais os passos completados. 
Para entregar o exercício, responda o [formulário de entrega](https://docs.google.com/forms/d/e/1FAIpQLScs03Oovqgz9LWDWuuwS0oGJuqB0y1TIV8kx9-CV8a8cAVGaQ/viewform) até 02/05/2018, às 23h59.

  18. A partir da `SharedPreferences` definida para estabelecer uma periodicidade para o carregamento de notícias, agende uma tarefa periódica por meio de `JobScheduler` para download das notícias do feed. A tarefa só deve ser executada se houver conectividade;
  19. Implemente um `ContentProvider` na classe `RssProvider` (disponibilizada no repositório), para realizar a manipulação do banco de dados, implementando todos os métodos de acesso e manipulação do banco;
  20. Defina uma permissão de acesso a este `RssProvider`, nomeada `br.ufpe.cin.if1001.rss.leitura`;
  21. Crie uma aplicação à parte, que acessa os dados de `RssProvider` e exibe em uma `ListView`. Esta nova aplicação *não deve ter permissão* de acessar a Internet.

---

# Orientações

  - Comente o código que você desenvolver, explicando o que cada parte faz.
  - Entregue o exercício *mesmo que não tenha completado todos os itens* listados. Marque abaixo apenas o que completou.

----

# Status

| Passo | Completou? |
| ------ | ------ |
| 18 | **sim** |
| 19 | **sim** |
| 20 | **sim** |
| 21 | **não** |
