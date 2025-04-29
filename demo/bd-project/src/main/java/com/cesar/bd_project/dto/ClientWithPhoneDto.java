package com.cesar.bd_project.dto;

import com.cesar.bd_project.model.ClientModel;
import lombok.Data;

import java.util.List;

@Data
public class ClientWithPhoneDto {
    private ClientModel client;
    private List<String> phonesList;

    public ClientWithPhoneDto(ClientModel client, List<String> phonesList) {
        this.client = client;
        this.phonesList = phonesList;
    }
}
