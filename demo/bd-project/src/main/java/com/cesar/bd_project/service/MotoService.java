package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.MotoDao;
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

    public List<MotoModel> listMotos() throws SQLException {
        return motoDao.list();
    }

    public MotoModel findById(String chassi) throws SQLException {
        if(chassi == null || chassi.isEmpty()){
            throw new IllegalArgumentException("Chassi não pode ser nulo ou vazio");
        }
        return motoDao.findById(chassi);
    }

    public void deleteMoto(String chassi){
        motoDao.delete(chassi);
    }
}
