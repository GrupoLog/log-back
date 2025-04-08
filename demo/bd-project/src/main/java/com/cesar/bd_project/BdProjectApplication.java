package com.cesar.bd_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BdProjectApplication implements CommandLineRunner{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BdProjectApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        String sql = "INSERT INTO Clientes (CPF, nome, sobrenome, rua, bairro, numero, cidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update(sql, 
            "12345678901", 
            "João",        
            "Silva",       
            "Rua das Flores", 
            "Centro",      
            123,           
            "Recife"       
        );

        if (result > 0) {
            System.out.println("Um novo cliente foi inserido.");
        }
    }
}
