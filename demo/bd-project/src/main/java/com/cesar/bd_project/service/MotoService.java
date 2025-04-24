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

    public void deleteMoto(String chassi) throws SQLException {
        motoDao.delete(chassi);
    }
}
