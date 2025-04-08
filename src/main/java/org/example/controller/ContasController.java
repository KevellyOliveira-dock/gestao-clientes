package org.example.controller;

import org.example.service.ContaService;

import java.util.Scanner;

public class ContasController implements Controller {
    private Scanner scanner;
    private ContaService contaService;

    public ContasController(ContaService contaService, Scanner scanner) {
        this.scanner = scanner;
        this.contaService = contaService;
    }

    public String executar(String comando) {

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

    private String pesquisarConta(String[] partes) {

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
                return "não implementado";

            case "numero":
                return "não implementado";

            default:
                return "operação inválida";
        }
    }

    public String cadastrarConta() {
        System.out.println("Informe seu CPF: ");
        String cpf = scanner.nextLine();

        System.out.println("Informe o número do seu cartão: ");
        String numeroCartao = scanner.nextLine();

        System.out.println("Informe seu saldo: ");
        double saldo = scanner.nextDouble();

        return contaService.cadastrarConta(numeroCartao, cpf, saldo);
    }
}
