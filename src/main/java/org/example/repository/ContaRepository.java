package org.example.repository;

import org.example.model.Conta;

import java.util.Collection;
import java.util.List;

public interface ContaRepository {
    Conta cadastrar(Conta conta);

    // Colletion -> armazena um grupo de objetos. Mais genérico e flexível
    List<Conta> buscarValores(String cpf);

    Conta buscarPorNumero(String numeroConta);

    int buscarTamanho();
}
