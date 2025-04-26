package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ClientDao;
import com.cesar.bd_project.dao.PhoneDao;
import com.cesar.bd_project.model.PhoneModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {

    private final PhoneDao phoneDao;
    private final ClientDao clientDao;

    public PhoneService(PhoneDao phoneDao, ClientDao clientDao) {
        this.phoneDao = phoneDao;
        this.clientDao = clientDao;
    }

    // Não faz sentido ter
    public List<PhoneModel> listPhones() {
        try {
            List<PhoneModel> phoneList = phoneDao.list();
            if (phoneList.isEmpty()) {
                throw new IllegalStateException("Nenhum telefone encontrado.");
            }

            return phoneList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar telefones: " + e.getMessage(), e);
        }
    }

    // Não faz sentido ter, tem que ter outro metodo que retorne uma lista de telefones
    public PhoneModel findById(String cpf) {
        return phoneDao.findById(cpf);
    }

    public List<PhoneModel> findByCpf(String cpf) {
        return phoneDao.findByCpf(cpf);
    }

    public void insertPhone(PhoneModel phone) {
        // Verifica se o cpf está no banco
        if(clientDao.findById(phone.getClientesCpf()) == null){
            throw new IllegalArgumentException("Cliente não está cadastrado.");
        }
        // Verica se já existe o telefone
        List<PhoneModel> phoneList = phoneDao.findByCpf(phone.getClientesCpf());
        for(PhoneModel savedPhone : phoneList){
            if(savedPhone.getTelefone().equals(phone.getTelefone())){
                throw new IllegalArgumentException("Telefone já cadastrado para esse CPF.");
            }
        }
        phoneDao.save(phone);
    }


    public void updatePhone(PhoneModel phone) {
        // Verifica se o cliente existe
        if (clientDao.findById(phone.getClientesCpf()) == null) {
            throw new IllegalArgumentException("Cliente não está cadastrado.");
        }

        // Verifica se o telefone antigo existe
        PhoneModel existingPhone = phoneDao.findById(phone.getTelefone());
        if (existingPhone == null) {
            throw new IllegalArgumentException("Telefone antigo não encontrado.");
        }

        // Verifica se o telefone novo já está cadastrado, mas para outro cliente
        PhoneModel phoneWithSameNumber = phoneDao.findById(phone.getTelefone());
        if (phoneWithSameNumber != null && !phoneWithSameNumber.getClientesCpf().equals(phone.getClientesCpf())) {
            throw new IllegalArgumentException("Telefone já está cadastrado para outro cliente.");
        }
        phoneDao.update(phone);
    }

    public void deletePhone(String phone) {
        if (phoneDao.findById(phone) == null) {
            throw new IllegalArgumentException("Telefone não encontrado!");
        }
        phoneDao.delete(phone);

    }
}
