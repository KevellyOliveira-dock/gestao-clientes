package org.example.repository;

import org.example.model.Fatura;

import java.util.List;

public interface FaturaRepository {
    Fatura cadastrar(Fatura fatura);

    List<Fatura> buscarPorNumeroCartao(String numeroCartao);

    int buscarTamanho();
}