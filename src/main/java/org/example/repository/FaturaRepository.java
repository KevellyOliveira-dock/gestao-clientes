package org.example.repository;

import org.example.model.Fatura;

import java.util.List;

public interface FaturaRepository {
    Fatura cadastrar(Fatura fatura);

    List<Fatura> buscarFaturaPorNumeroCartao(String numeroCartao);

    int buscarTamanho();
}