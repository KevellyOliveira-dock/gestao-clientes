package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;

import java.util.List;

public interface CartaoService {
    Cartao cadastrarCartao(Conta conta) throws Exception;

    Cartao buscarCartaoPorNumero(String numeroCartao) throws Exception;

    Cartao bloquearCartao(String numeroCartao) throws Exception;

    Cartao desbloquearCartao(String numeroCartao) throws Exception;

    List<Cartao> buscarCartaoPorCPF(Cliente titular) throws Exception;
}
