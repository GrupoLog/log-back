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

    public PhoneModel findById(String cpf) {
        return phoneDao.findById(cpf);
    }

    public void insertPhone(PhoneModel phone) {
        // Verifica se o telefone não é nulo
        if(phone.getTelefone() == null || phone.getClientesCpf() == null){
            throw new IllegalArgumentException("telefone e cpf é obrigatório");
        }
        // Verifica se o cpf está no banco
        if(clientDao.findById(phone.getClientesCpf()) == null){
            throw new IllegalArgumentException("CPF não está cadastrado.");
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
        if(phone.getTelefone() == null){
            throw new IllegalArgumentException("O campo telefone é obrigatório!");
        }
        // Falta validar caso o telefone não exista
        // Falta validar caso o cpf não exista
//        PhoneModel existingPhone = phoneDao.findById(phone.getClientesCpf());
//        if(existingPhone == null){
//            System.err.println("Telefone não encontrado!");
//        }

        phoneDao.update(phone);
    }

    public void deletePhone(String phone) {
        if (phoneDao.findById(phone) == null) {
            throw new IllegalArgumentException("Telefone não encontrado!");
        }
        phoneDao.delete(phone);

    }
}
