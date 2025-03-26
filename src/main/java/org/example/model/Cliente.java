package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Getter
//@Setter
@Data // -> getter e setter
@AllArgsConstructor // Gera o construtor com todos of fields da classe
public class Cliente {
    private String nomeCompleto;
    private String cpf;
    private String endereco;
}
