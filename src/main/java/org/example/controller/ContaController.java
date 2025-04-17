package org.example.controller;

import org.example.model.Conta;
import org.example.service.ContaService;

import java.util.List;
import java.util.Scanner;

public class ContaController implements Controller {
    private Scanner scanner;
    private ContaService contaService;

    public ContaController(ContaService contaService, Scanner scanner) {
        this.scanner = scanner;
        this.contaService = contaService;
    }

    public String executar(String comando) throws Exception {

        if (comando.equals("contas")) {
            return """
                    -------------------------------
                    | cadastrar                   |
                    | desativar {numero da conta} |
                    | extrato {numero da conta}   |
                    | pesquisar                   |
                    -------------------------------""";
        }

        String[] partes = comando.split(" ");
        var acao = partes[1];

        switch (acao) {
            case "cadastrar":
                return cadastrarConta();

            case "desativar":
                return "não implementado";

            case "extrato":
                return "não implementado";

            case "pesquisar":
                return pesquisarConta(partes);

            default:
                return "operação inválida";
        }
    }

    private String pesquisarConta(String[] partes) throws Exception {

        if (partes.length == 2) {
            return """
                    ----------------------------------
                    | cpf-titular {cpf do cliente}   |
                    | nome-titular {nome do cliente} |
                    | numero {número da conta}       |
                    ----------------------------------""";
        }

        switch (partes[2]) {
            case "cpf-titular":
                return "não implementado";

            case "nome-titular":
                if (partes.length > 3) {
                    return pesquisarContaPorTitular(partes[3]);
                } else {
                    return "Informe o nome do titular da conta que deseja pesquisar. " +
                            "Ex: contas pesquisar nome-titular Kevelly.\n";
                }

            case "numero":
                if (partes.length > 3) {
                    return pesquisarContaPorNumero(partes[3]);
                } else {
                    return "Informe o número do cartão que deseja pesquisar. Ex: contas pesquisar numero 5.\n";
                }

            default:
                return "operação inválida";
        }
    }

    public String cadastrarConta() {
        System.out.println("Informe seu CPF: ");
        String cpf = scanner.nextLine();

        System.out.println("Informe seu saldo: ");
        String saldoStr = scanner.nextLine();

        try {
            return "Conta cadastrada com sucesso!\n" +
                    contaService.cadastrarConta(cpf, saldoStr).toString(); // valueOf() -> converte para String
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String pesquisarContaPorNumero(String numeroConta) throws Exception {
        try {
            return "Conta encontrada: \n" + contaService.buscarContaPorNumero(numeroConta).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String pesquisarContaPorTitular(String nomeCompleto) throws Exception {
        List<Conta> contas;
        try {
            contas = contaService.buscarContasPorTitular(nomeCompleto);
        } catch (Exception e) {
            return e.getMessage();
        }
        StringBuilder resultado = new StringBuilder("Contas encontradas: \n");
        for (Conta conta : contas) {
            resultado.append(conta.toString()).append("\n");
        }

        return resultado.toString();
    }
}
