package org.example.controller;

import org.example.model.Cartao;
import org.example.model.Conta;
import org.example.service.CartaoService;
import org.example.service.ContaService;

import java.util.List;
import java.util.Scanner;

public class ContaController implements Controller {
    private Scanner scanner;
    private ContaService contaService;
    private CartaoService cartaoService;

    public ContaController(ContaService contaService, Scanner scanner, CartaoService cartaoService) {
        this.scanner = scanner;
        this.contaService = contaService;
        this.cartaoService = cartaoService;
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
                if (partes.length == 3) {
                    return desativarConta(partes[2]);
                } else {
                    return "Para desativar a conta é necessário informar o número. Ex: contas desativar 0.\n";
                }

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
                if (partes.length > 3) {
                    return pesquisarContaPorCPF(partes[3]);
                } else {
                    return "Informe o CPF do titular da conta que deseja pesquisar. " +
                            "Ex: contas pesquisar cpf-titular 12345678900.\n";
                }

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

    public String pesquisarContaPorNumero(String numeroConta) {
        try {
            return "Conta encontrada: \n" + contaService.buscarContaPorNumero(numeroConta).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String pesquisarContaPorTitular(String nomeCompleto) {
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

    public String pesquisarContaPorCPF(String cpf) {
        List<Conta> contas;
        try {
            contas = contaService.buscarContasPorCPF(cpf);
        } catch (Exception e) {
            return e.getMessage();
        }
        StringBuilder resultado = new StringBuilder("Contas encontradas: \n");
        for (Conta conta : contas) {
            resultado.append(conta.toString()).append("\n");
        }

        return resultado.toString();
    }

    public String desativarConta(String numeroConta) {
        try {
            Conta contaExistente = contaService.buscarContaPorNumero(numeroConta);
            String resposta;

            while (true) {
                System.out.println("Confirma a desativação da conta " + contaExistente.getNumeroConta() +
                        ", de titularidade de " + contaExistente.getTitular().getNomeCompleto() +
                        ", CPF " + contaExistente.getTitular().getCpf() +
                        "? Ao confirmar todos os cartões vinculados a essa conta serão bloqueados. \n" +
                        " Digite \"S\" para sim ou \"N\" para não: ");
                resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    contaExistente.setAtivo(false);
                    List<Cartao> lista = cartaoService.buscarCartoesPorCPF(contaExistente.getTitular().getCpf());
                    lista.forEach(cartao -> cartao.setBloqueado(true));
                    return "Sua conta foi desativada com sucesso!\n";
                } else if (resposta.equals("N")) {
                    return "Operação cancelada\n";
                }

                System.out.println("\nDigite somente \"S\" para sim ou \"N\" para não.");
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
