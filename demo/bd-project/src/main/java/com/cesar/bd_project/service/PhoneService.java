package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.PhoneDAO;
import com.cesar.bd_project.model.PhoneModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PhoneService {

    private final PhoneDAO phoneDao;

    public PhoneService(PhoneDAO phoneDao){
        this.phoneDao = phoneDao;
    }

    public List<PhoneModel> listPhones() throws SQLException {
        return phoneDao.list();
    }

    public PhoneModel insertPhone(PhoneModel phone) throws SQLException {
        // Verifica se o id_produto não é nulo
        if(phone.getClientesCpf() == null){
            throw new IllegalArgumentException("id_produto é obrigatório");
        }

        // Verica se já existe no banco de dados
        if (phoneDao.findById(phone.getClientesCpf()) != null) {
            throw new IllegalArgumentException("Produto já cadastrado!");
        }

        return phoneDao.save(phone);
    }

    public PhoneModel findById(String cpf) throws SQLException{
        return phoneDao.findById(cpf);
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
