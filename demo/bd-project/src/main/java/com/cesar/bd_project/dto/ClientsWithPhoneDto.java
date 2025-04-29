package com.cesar.bd_project.dto;

import com.cesar.bd_project.model.ClientModel;
import com.cesar.bd_project.model.PhoneModel;
import lombok.Data;

import java.util.List;

@Data
public class ClientsWithPhoneDto {
    private ClientModel client;
    private List<String> phonesList;

    public ClientsWithPhoneDto(ClientModel client, List<String> phonesList) {
        this.client = client;
        this.phonesList = phonesList;
    }
}
