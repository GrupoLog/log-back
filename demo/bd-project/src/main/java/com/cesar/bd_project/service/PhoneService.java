package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.ClientDao;
import com.cesar.bd_project.dao.PhoneDao;
import com.cesar.bd_project.model.PhoneModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PhoneService {

    private final PhoneDao phoneDao;
    private final ClientDao clientDao;

    public PhoneService(PhoneDao phoneDao, ClientDao clientDao){
        this.phoneDao = phoneDao;
        this.clientDao = clientDao;
    }

    public List<PhoneModel> listPhones() throws SQLException {
        return phoneDao.list();
    }

//    public PhoneModel findById(String cpf) throws SQLException{
//        return phoneDao.findById(cpf);
//    }

    public PhoneModel insertPhone(PhoneModel phone) throws SQLException {
        // Verifica se o telefone não é nulo
        if(phone.getTelefone() == null || phone.getClientesCpf() == null){
            throw new IllegalArgumentException("telefone e cpf é obrigatório");
        }
        // Verifica se o cpf está no banco
        if(clientDao.findById(phone.getClientesCpf()) == null){
            throw new IllegalArgumentException("CPF não está cadastrado.");
        }
        // Verica se já existe o telefone
        List<PhoneModel> phoneList = phoneDao.findById(phone.getClientesCpf());
        for(PhoneModel savedPhone : phoneList){
            if(savedPhone.getTelefone().equals(phone.getTelefone())){
                throw new IllegalArgumentException("Telefone já cadastrado para esse CPF.");
            }
        }

        return phoneDao.save(phone);
    }

    public void updatePhone(PhoneModel phone) throws SQLException {
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


    public void deletePhone(String cpf) throws SQLException{
        if (phoneDao.findById(cpf) == null) {
            throw new IllegalArgumentException("PTelefone não encontrado!");
        }
        phoneDao.delete(cpf);
    }
}
