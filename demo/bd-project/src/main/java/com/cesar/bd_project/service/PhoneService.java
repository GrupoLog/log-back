package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.PhoneDAO;
import com.cesar.bd_project.model.PhoneModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class PhoneService {

    private final PhoneDAO phoneDao;

    public PhoneService(PhoneDAO phoneDao){
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

        // Verica se já existe no banco de dados
        if (phone.getTelefone().equals(phoneDao.findById(phone.getClientesCpf()).getTelefone())) {
            throw new IllegalArgumentException("Telefone já cadastrado!");
        }

        return phoneDao.save(phone);
    }

    public void updatePhone(PhoneModel phone) throws SQLException {
        if(phone.getClientesCpf() == null){
            throw new IllegalArgumentException("O campo id_produto é obrigatório!");
        }

        PhoneModel existingPhone = phoneDao.findById(phone.getClientesCpf());
        if(existingPhone == null){
            System.err.println("Produto não encontrado!");
        }

        phoneDao.update(phone);
    }

    public void deletePhone(String cpf) throws SQLException{
        if (phoneDao.findById(cpf) == null) {
            throw new IllegalArgumentException("Produto não encontrado!");
        }
        phoneDao.delete(cpf);
    }
}
