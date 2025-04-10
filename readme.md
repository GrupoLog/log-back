# 📦 Sistema de Banco de Dados para Logística

## 📖 Descrição do Projeto

Este projeto visa modelar e implementar um banco de dados para uma empresa de logística que realiza transporte de mercadorias e passageiros. O sistema gerencia motoristas, clientes, serviços, pagamentos e veículos de forma estruturada, garantindo eficiência e segurança nas operações.

## 🏗 Estrutura do Projeto

O desenvolvimento do projeto está dividido em dois módulos principais:

### 🏛 Módulo 1
- Modelagem do banco de dados:
  - Diagrama Conceitual
  - Diagrama Lógico
- Scripts SQL:
  - Criação das tabelas
  - Inserção de dados iniciais
- Desenvolvimento de um CRUD funcional com interface simples
- Relatório versão 1.0: Documentação das etapas do processo

### 🚀 Módulo 2
- Interface versão 2.0 com melhorias na usabilidade
- Dashboard para visualização de dados
- Scripts avançados de consultas SQL
- Relatório final detalhando a conclusão do projeto

## 📌 Tecnologias Utilizadas

- **MySQL** - Banco de dados relacional
- **SQL** - Linguagem para manipulação dos dados
- **BR Modelo** - Modelagem do banco de dados
- **DBeaver** - Ferramentas de gerenciamento

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e executar o projeto em sua máquina:

### 1. Clone o Repositório
Clone o repositório para sua máquina local usando o comando abaixo:
```bash
git clone https://github.com/GrupoLog/log-back.git
```

### 2. Acesse o Diretório do Projeto
Entre no diretório do projeto:
```bash
cd log-back
```

### 3. Configure o Banco de Dados
Certifique-se de que o MySQL está instalado e configurado corretamente. Em seguida:
- Crie um banco de dados chamado `GrupoLog_DB`.
- Execute os scripts SQL localizados na pasta `scripts/` para criar as tabelas e inserir os dados iniciais:
  ```sql
  source scripts/criar_tabelas.sql;
  source scripts/inserir_dados.sql;
  ```

### 4. Configure o Arquivo `application.properties`
No diretório `src/main/resources`, edite o arquivo `application.properties` com as credenciais do seu banco de dados:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/GrupoLog_DB
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 5. Compile o Projeto
Compile o projeto usando o Maven:
```bash
mvn clean install
```

### 6. Execute o Projeto
Inicie a aplicação com o comando:
```bash
mvn spring-boot:run
```

### 7. Acesse a Aplicação
Após iniciar o servidor, acesse a aplicação no navegador:
```
http://localhost:8080
```

### 8. Por fim, Finalize o Processo
Após testar, finalize o servidor com `Ctrl + C` no terminal.


## 👥 Integrantes do Grupo

- **Arthur Capistrano** - [asc@cesar.school](mailto:asc@cesar.school)
- **Érico Chen** - [ec2@cesar.school](mailto:ec2@cesar.school)
- **João Antônio** - [jamlp@cesar.school](mailto:jamlp@cesar.school)
- **Pablo Muniz** - [pcm7@cesar.school](mailto:pcm7@cesar.school)

## 📄 Licença

Este projeto está licenciado sob a **MIT License**.
