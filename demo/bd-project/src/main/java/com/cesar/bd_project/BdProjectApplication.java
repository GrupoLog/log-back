package com.cesar.bd_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.cesar.bd_project.client.ClientDAO;
import com.cesar.bd_project.client.ClientModel;

@SpringBootApplication
public class BdProjectApplication implements CommandLineRunner{

	@Autowired
    private ClientDAO clientDAO;

	public static void main(String[] args) {
		SpringApplication.run(BdProjectApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        String cpf = "12345678901";

        // Verifica se o cliente já existe no banco
        ClientModel existingClient = clientDAO.buscarPorCpf(cpf);

        if (existingClient == null) {
            // Cliente não existe, insere no banco
            ClientModel newClient = new ClientModel(
                cpf,
                "João",
                "Silva",
                "Rua das Flores",
                "Centro",
                123,
                "Recife"
            );

            clientDAO.salvar(newClient);
            System.out.println("Um novo cliente foi inserido.");
        } else {
            System.out.println("Cliente com o CPF " + cpf + " já existe no banco.");
        }
    }
}
