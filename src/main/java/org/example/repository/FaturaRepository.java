package org.example.repository;

import org.example.model.Cartao;
import org.example.model.Fatura;

import java.util.List;

public interface FaturaRepository {
    Fatura cadastrar(Fatura fatura);

    List<Fatura> buscarFaturaPorNumeroCartao(Cartao cartao);

    int buscarTamanho();
}