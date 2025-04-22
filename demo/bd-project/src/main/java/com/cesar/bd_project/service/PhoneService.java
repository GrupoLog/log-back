package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.PhoneDao;
import com.cesar.bd_project.model.PhoneModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PhoneService {

    private final PhoneDao phoneDao;

    public PhoneService(PhoneDao phoneDao){
        this.phoneDao = phoneDao;
    }

    public List<PhoneModel> listPhones() throws SQLException {
        return phoneDao.list();
    }

    public PhoneModel findById(String cpf) throws SQLException{
        return phoneDao.findById(cpf);
    }

    public PhoneModel insertPhone(PhoneModel phone) throws SQLException {
        // Verifica se o telefone não é nulo
        if(phone.getTelefone() == null){
            throw new IllegalArgumentException("telefone é obrigatório");
        }
        // Falta validar o caso de o cpf não estiver no banco

        // Verica se já existe no banco de dados
        if (phone.getTelefone().equals(phoneDao.findById(phone.getClientesCpf()).getTelefone())) {
            throw new IllegalArgumentException("Telefone já cadastrado!");
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
