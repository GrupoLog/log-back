package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.MotoDao;
import com.cesar.bd_project.model.ClientModel;
import com.cesar.bd_project.model.MotoModel;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MotoService {

    private final MotoDao motoDao;

    public MotoService(MotoDao motoDao){
        this.motoDao = motoDao;
    }

    public List<MotoModel> listMotos() {
        try {
            List<MotoModel> motoList = motoDao.list();
            if (motoList.isEmpty()) {
                throw new IllegalStateException("Nenhuma moto encontrada.");
            }

            return motoList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar cmotos: " + e.getMessage(), e);
        }
    }



    public MotoModel findById(String chassi) {
        if(chassi == null || chassi.isEmpty()){
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        return motoDao.findById(chassi);
    }

    public void deleteMoto(String chassi){
        // Verifica se o CPF é nulo ou vazio
        if (chassi == null || chassi.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }

        // Verifica se o cliente existe
        MotoModel existingMoto = motoDao.findById(chassi);
        if (existingMoto == null) {
            throw new IllegalArgumentException("Moto não encontrada.");
        }
        motoDao.delete(chassi);
    }
}
