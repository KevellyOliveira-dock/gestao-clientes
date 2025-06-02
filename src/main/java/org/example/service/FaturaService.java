package org.example.service;

import org.example.model.Cartao;
import org.example.model.Fatura;

public interface FaturaService {
    Fatura fecharFatura(Cartao cartao) throws Exception;

    Fatura pagarFatura(Cartao cartao) throws Exception;
}