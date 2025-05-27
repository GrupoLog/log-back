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
Certifique-se de que o MySQL está instalado e configurado corretamente:
- Conexão criada com um usuário e senha
  
Em seguida:
- Crie um banco de dados chamado `GrupoLog`.

Este passo é essencial para o build da aplicação, pois Spring não cria banco de dados, apenas cria as tabelas.

### 4. Configure o arquivo `application.properties`
No diretório `src/main/resources`, edite o arquivo `application.properties` com as credenciais do seu banco de dados:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/GrupoLog
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
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

### 8. Finalize o Processo
Após testar, finalize o servidor com `Ctrl + C` no terminal.


### 9. Por fim, alterar o arquivo `application.properties`
No diretório `src/main/resources`, edite o arquivo `application.properties` com as credenciais do seu banco de dados:
```
spring.sql.init.mode=never
```
Este passo é essencial, caso deseje rodar a aplicação novamente. Dessa forma, ao inicializar a aplicação novamente, o Spring não vai executar os scripts sql (criando as mesmas tabelas e dados) e vai buildar normalmente.

## 🧩 Componentes Extras
### 🔁 Trigger: Log de Novos Clientes
```
DROP TRIGGER IF EXISTS trg_log_novo_cliente;

CREATE TRIGGER trg_log_novo_cliente
AFTER INSERT ON Clientes
FOR EACH ROW
INSERT INTO Log_Clientes (cpf, nome)
VALUES (NEW.cpf, NEW.nome);
```

### 📊 Procedure: Contar Viagens por Tipo de Veículo
```
DROP PROCEDURE IF EXISTS sp_contar_viagens_por_tipo;

CREATE PROCEDURE sp_contar_viagens_por_tipo(IN ano INT)
SELECT 'van' AS tipo, COUNT(*) AS total
FROM Viagem vi
JOIN Van van ON vi.veiculo_chassi = van.veiculo_chassi
WHERE YEAR(vi.data_viagem) = ano
UNION ALL
SELECT 'moto' AS tipo, COUNT(*) AS total
FROM Viagem vi
JOIN Moto moto ON vi.veiculo_chassi = moto.veiculo_chassi
WHERE YEAR(vi.data_viagem) = ano;
```

## 👥 Integrantes do Grupo

- **Arthur Capistrano** - [asc@cesar.school](mailto:asc@cesar.school)
- **Érico Chen** - [ec2@cesar.school](mailto:ec2@cesar.school)
- **João Antônio** - [jamlp@cesar.school](mailto:jamlp@cesar.school)
- **Pablo Muniz** - [pcm7@cesar.school](mailto:pcm7@cesar.school)

## 📄 Licença

Este projeto está licenciado sob a **MIT License**.
