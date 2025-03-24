package org.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
//@Data -> getter e setter
@RequiredArgsConstructor //gera o construtor
public class Cliente {
    private String nomeCompleto;
    private String cpf;
    private String endereco;
}
