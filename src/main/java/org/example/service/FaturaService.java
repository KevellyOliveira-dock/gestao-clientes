package org.example.service;

import org.example.model.Fatura;

import java.util.Collection;

public interface FaturaService {
    Fatura fecharFatura(String numeroCartao) throws Exception;

    Collection<Fatura> pagarFatura(String numeroCartao) throws Exception;
}