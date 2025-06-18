package org.example.repository;

import org.example.model.Conta;

import java.util.Collection;
import java.util.List;

public interface ContaRepository {
    Conta cadastrar(Conta conta);

    List<Conta> buscarPorCPF(String cpf);

    List<Conta> buscarPorNomeCompleto(String nomeCompleto);

    Conta buscarPorNumero(String numeroConta);

    int buscarTamanho();
}
