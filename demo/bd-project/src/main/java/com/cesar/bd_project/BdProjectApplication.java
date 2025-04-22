package com.cesar.bd_project;

import com.cesar.bd_project.dao.ClientDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.cesar.bd_project.model.ClientModel;

@SpringBootApplication
public class BdProjectApplication implements CommandLineRunner{

	@Autowired
    private ClientDao clientDao;

	public static void main(String[] args) {
		SpringApplication.run(BdProjectApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        String cpf = "12345678901";

        // Verifica se o cliente já existe no banco
        ClientModel existingClient = clientDao.findById(cpf);

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

            clientDao.save(newClient);
            System.out.println("Um novo cliente foi inserido.");
        } else {
            System.out.println("Cliente com o CPF " + cpf + " já existe no banco.");
        }
    }
}
