package org.example.repository;

import org.example.model.Fatura;

import java.util.Collection;

public interface FaturaRepository {
    Fatura cadastrar(Fatura fatura);

    Collection<Fatura> buscarPorNumeroCartao(String numeroCartao);

    int buscarTamanho();

}