package org.example.service;

import org.example.model.Fatura;

public interface FaturaService {
    Fatura fecharFatura(String numeroCartao) throws Exception;

    Fatura buscarFatura(String numeroCartao) throws  Exception;
}
