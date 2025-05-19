# HawkEye

**HawkEye** é uma ferramenta de automação e monitoramento de pull requests no GitHub, alimentada por Node.js e IA.

## Funcionalidades

* **Monitoramento de Pull Requests**: consome filas e detecta novos PRs automaticamente.
* **Análise de Arquivos**: identifica arquivos adicionados ou modificados em cada PR.
* **Comentários Automáticos**: gera resumos de commits via IA e publica comentários no GitHub.
* **Extensibilidade**: arquitetura modular para adicionar agentes e serviços personalizados.
* **Orquestração com Docker**: configuração simplificada com Docker Compose.

## Tecnologias

* Node.js v18
* Express
* Axios
* GitHub REST API
* RabbitMQ (fila de tarefas)
* Docker e Docker Compose

## Instalação

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/HawkEye.git
   cd HawkEye
   ```
2. Instale as dependências:

   ```bash
   npm install
   ```
3. Configure as variáveis de ambiente:

   * `GITHUB_TOKEN`: Token de acesso ao GitHub com escopo `repo`.
   * `RABBITMQ_URL`: URL de conexão do RabbitMQ.
4. Inicie os serviços com Docker:

   ```bash
   docker-compose up -d
   ```
5. Ou rode localmente:

   ```bash
   npm start
   ```

## Uso

Após iniciar o servidor, ele consumirá a fila de pull requests e postará comentários automaticamente. Certifique-se de que o RabbitMQ esteja em execução e de que o token do GitHub tenha as permissões corretas.

## Contribuição

1. Faça um fork do projeto.
2. Crie uma branch para sua feature: `git checkout -b feature/nova-funcionalidade`.
3. Faça commit das suas alterações: `git commit -m "Adiciona nova funcionalidade"`.
4. Envie para o GitHub: `git push origin feature/nova-funcionalidade`.
5. Abra um Pull Request e aguarde revisão.

## Licença

Este projeto está licenciado sob a licença MIT.
