package com.cesar.bd_project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties props = new Properties();


    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo application.properties não encontrado");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar configurações do banco", e);
        }
    }

    public static String getUrl() {
        return props.getProperty("spring.datasource.url");
    }

    public static String getUsername() {
        return props.getProperty("spring.datasource.username");
    }

    public static String getPassword() {
        return props.getProperty("spring.datasource.password");
    }

}
