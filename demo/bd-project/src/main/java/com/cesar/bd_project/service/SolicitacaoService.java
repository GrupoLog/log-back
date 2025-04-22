package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.SolicitacaoDao;
import com.cesar.bd_project.model.SolicitacaoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoDao solicitacaoDao;

    public SolicitacaoModel criarSolicitacao(SolicitacaoModel solicitacao) {
        // Lógica de validação ou transformação antes de salvar
        return solicitacaoDao.salvar(solicitacao);
    }

    public List<SolicitacaoModel> listarSolicitacoes() {
        return solicitacaoDao.listarTodos();
    }

    public SolicitacaoModel buscarSolicitacaoPorId(int id) {
        return solicitacaoDao.buscarPorId(id);
    }

    public boolean atualizarSolicitacao(int id, SolicitacaoModel solicitacaoAtualizada) {
        return solicitacaoDao.atualizar(id, solicitacaoAtualizada);
    }

    public boolean deletarSolicitacao(int id) {
        return solicitacaoDao.deletar(id);
    }
}