**Universidade Federal da Paraíba - UFPB** \
**Centro de Ciências Exatas e Educação - CCAE** \
**Departamento de Ciências Exatas - DCX**

**Professor:** [Rodrigo Rebouças de Almeida](http://rodrigor.com)

# Projeto de Referência: Sistema de Gestão de Projetos

Este é um projeto de referência para os alunos das disciplinas de Análise e 
Projeto de Sistemas e Projeto de Sistemas Orientados a Objetos dos cursos de 
Sistemas de Informação e Lic. em Ciência da Computação da UFPB, campus IV em 
Rio Tinto.

Este projeto foi desenvolvido para servir como base para a implementação do 
projeto da disciplina. Ele é incompleto e possui vários pontos de melhoria 
propositais, que serão discutidos em sala de aula. Deste modo, não considere 
cegamente este projeto como uma referência para boas práticas de programação 
e um bom design. Repito, eu coloquei propositalmente alguns problemas de 
design que serão discutidos em sala de aula.

Trata-se de um projeto de controle de Projetos, onde, por enquanto é 
possível cadastrar projetos e participantes.

## Principais funcionalidades do sistema

### Gerenciamento de Projetos
- **Listar Projetos**: Exibe uma lista de todos os projetos cadastrados.
- **Adicionar Projeto**: Permite a criação de um novo projeto, incluindo informações como nome, descrição, datas de início e encerramento, e coordenador.
- **Remover Projeto**: Permite a remoção de um projeto existente.
- **Atualizar Projeto**: Permite a atualização das informações de um projeto existente.

### Gerenciamento de Participantes
- **Listar Participantes**: Exibe uma lista de todos os participantes cadastrados.
- **Adicionar Participante**: Permite a criação de um novo participante, incluindo informações como nome, sobrenome, email, telefone e categoria.
- **Remover Participante**: Permite a remoção de um participante existente.

## Arquitetura da Solução

### Camada de Controle
- **ProjetoController**: Controla as operações relacionadas aos projetos, como listar, adicionar, remover e atualizar projetos.
- **ParticipanteController**: Controla as operações relacionadas aos participantes, como listar, adicionar e remover participantes.

### Camada de Serviço
- **ProjetoService**: Contém a lógica de negócios para gerenciar projetos, incluindo a conversão entre objetos `Projeto` e documentos do MongoDB.
- **ParticipanteService**: Contém a lógica de negócios para gerenciar participantes.

### Camada de Modelo
- **Projeto**: Representa a entidade de um projeto, incluindo atributos como nome, descrição, datas de início e encerramento, e coordenador.
- **Participante**: Representa a entidade de um participante, incluindo atributos como nome, sobrenome, email, telefone e categoria.

### Persistência de Dados
- **MongoDB**: Utilizado para armazenar os dados dos projetos e participantes. A conexão e operações com o banco de dados são gerenciadas pelo `MongoDBConnector`.

### Templates
- **Thymeleaf**: Utilizado para renderizar as páginas HTML, como a lista de projetos e o formulário de cadastro de participantes.

### Configuração
- **application.properties**: Arquivo de configuração onde são definidas as propriedades do banco de dados, como a string de conexão do MongoDB.

## Configuração do Projeto

1. Copie o arquivo `application.properties.exemplo` para `application.properties`:
   ```bash
   cp src/main/resources/application.properties.exemplo src/main/resources/application.properties
    ```
2. Edite o arquivo `application.properties` e insira a string de conexão do 
   seu banco de dados MongoDB: `mongodb.connectionString=<sua_string_de_conexão>`
