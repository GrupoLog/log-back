package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.RequestDao;
import com.cesar.bd_project.dto.RequestDto;
import com.cesar.bd_project.mapper.ClassMapper;
import com.cesar.bd_project.model.RequestModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    private final RequestDao requestDao;

    public RequestService(RequestDao requestDao){
        this.requestDao = requestDao;
    }

    public List<RequestDto> listRequests() {
        try {
            List<RequestModel> requestList = requestDao.list();
            List<RequestDto> requestListDto = new ArrayList<>();
            for (RequestModel request : requestList) {
                requestListDto.add(ClassMapper.toRequestDto(request));
            }
            if (requestListDto.isEmpty()) {
                throw new IllegalStateException("Nenhuma solicitacao encontrada.");
            }
            return requestListDto;
        }catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar solicitacoes: " + e.getMessage(), e);
        }
    }

//    public RequestModel criarSolicitacao(RequestModel solicitacao) {
//        // Lógica de validação ou transformação antes de salvar
//        return requestDao.salvar(solicitacao);

//    }

//    public RequestModel buscarSolicitacaoPorId(int id) {
//        return requestDao.buscarPorId(id);
//    }
//
//    public boolean atualizarSolicitacao(int id, RequestModel solicitacaoAtualizada) {
//        return requestDao.atualizar(id, solicitacaoAtualizada);
//    }
//
//    public boolean deletarSolicitacao(int id) {
//        return requestDao.deletar(id);
//    }
}