package org.example.model;

public class Cliente {
    private String nomeCompleto;
    private String cpf;
    private String endereco;

    public Cliente(String nomeCompleto, String cpf, String endereco) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

}
