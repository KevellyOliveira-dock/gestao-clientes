package org.example.service;

import org.example.model.Fatura;

import java.util.Collection;


public interface FaturaService {
    Fatura fecharFatura(String numeroCartao) throws Exception;

//    List<Fatura> buscarPorNumeroCartao(String numeroCartao);

    Fatura pagarFatura(String numeroCartao) throws Exception;
}