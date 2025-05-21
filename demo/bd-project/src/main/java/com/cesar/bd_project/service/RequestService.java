package com.cesar.bd_project.service;

import com.cesar.bd_project.dao.RequestDao;
import com.cesar.bd_project.dto.RequestDto;
import com.cesar.bd_project.dto.RevenueByPaymentKind;
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

    public List<RevenueByPaymentKind> getRevenueByPaymentKind() {
        try {
            // Chama o método do DAO para calcular a receita por forma de pagamento
            List<RevenueByPaymentKind> revenueList = requestDao.calcularReceitaPorFormaPagamento();

            // Verifica se a lista está vazia e lança uma exceção, se necessário
            if (revenueList.isEmpty()) {
                throw new IllegalStateException("Nenhuma receita encontrada.");
            }

            return revenueList;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao calcular receita por forma de pagamento: " + e.getMessage(), e);
        }
    }

    public Double getReceitaTotalPorAno(int ano) {
        return requestDao.calcularReceitaTotalPorAno(ano);
    }

}