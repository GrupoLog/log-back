📦 Sistema de Banco de Dados para Logística
📖 Descrição do Projeto
Este projeto visa modelar e implementar um banco de dados para uma empresa de logística que realiza transporte de mercadorias e passageiros. O sistema gerencia motoristas, clientes, serviços, pagamentos e veículos de forma estruturada, garantindo eficiência e segurança nas operações.

🏢 Mini-Mundo
- A empresa de logística oferece dois tipos de serviço:
- Transporte de mercadorias (realizado exclusivamente por motos, com limite de carga de 50 kg);
- Transporte de passageiros (realizado por vans, com limite de passageiros definido pela capacidade do veículo).

🚗 Frota
- Veículos são motos e vans, pertencendo à empresa ou a terceiros.
- Cada veículo tem placa, capacidade e proprietário cadastrado.
- Apenas um motorista conduz um veículo por vez.

👨‍✈️ Motoristas
- Podem ser fixos (apenas da empresa) ou terceirizados (podem ter veículo próprio).
- Possuem registro com nome, CPF, CNH, categoria de habilitação e contato.

🏠 Clientes
- Devem fornecer CPF, nome, sobrenome, endereço e telefone.
- Podem solicitar múltiplos serviços simultaneamente.

💰 Pagamento
- Realizado antecipadamente por Pix, cartão de crédito ou dinheiro.
- O transporte de passageiros custa R$ 100,00 por pessoa.
- A entrega de mercadorias custa R$ 30,00 por envio.
- O status do pagamento pode ser Em andamento, Cancelado ou Aprovado.
- Em caso de cancelamento, o cliente tem direito a reembolso.

📍 Pontos de Encontro e Coleta
- O transporte de passageiros parte de três pontos fixos.
- As entregas são deixadas em pontos de coleta fixos e devem incluir informações como destinatário, endereço de entrega e descrição do produto.

📊 Registro de Viagens
Cada viagem é registrada com:

- Data e horário
- Rota
- Motorista e veículo utilizados
- Tipo de serviço
- Clientes transportados ou detalhes do produto entregue

📌 Tecnologias Utilizadas
- MySQL - Banco de dados relacional
- SQL - Linguagem para manipulação dos dados
- Diagrama ER - Modelagem do banco de dados
- DBeaver - Ferramentas de gerenciamento

🚀 Como Executar o Projeto
Clone o repositório:


👥 Integrantes do Grupo
Nome 1 - asc@cesar.school

Nome 2 - ec2@cesar.school

Nome 3 - jamlp@cesar.school

Nome 4 - pcm7@cesar.school

📄 Licença
Este projeto está licenciado sob a MIT License.
