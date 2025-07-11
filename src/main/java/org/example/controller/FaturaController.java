package org.example.controller;

import org.example.model.Cartao;
import org.example.model.Fatura;
import org.example.service.CartaoService;
import org.example.service.FaturaService;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FaturaController implements Controller {
    private final Scanner scanner;
    private final FaturaService faturaService;
    private final CartaoService cartaoService;

    public FaturaController(FaturaService faturaService, Scanner scanner, CartaoService cartaoService) {
        this.faturaService = faturaService;
        this.scanner = scanner;
        this.cartaoService = cartaoService;
    }

    public String executar(String comando) {
        if (comando.equals("faturas")) {
            return """
                    -----------------------------
                    | fechar {número do cartão} |
                    | pagar {número do cartão}  |
                    -----------------------------""";
        }

        String[] partes = comando.split(" ");
        var acao = partes[1];

        switch (acao) {
            case "fechar":
                if (partes.length == 3) {
                    return fecharFatura(partes[2]);
                } else {
                    return "Para fechar a fatura é necessário informar o número do cartão. Ex: faturas fechar 0123.\n";
                }

            case "pagar":
                if (partes.length == 3) {
                    return pagarFatura(partes[2]);
                } else {
                    return "Para pagar a fatura é necessário informar o número do cartão. Ex: faturas pagar 0123.\n";
                }


            default:
                return "operação inválida";
        }
    }

    public String fecharFatura(String numeroCartao) {
        try {
            Cartao cartao = cartaoService.buscarCartaoPorNumero(numeroCartao);
            String dataVencimentoCartao = cartao.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            while (true) {
                System.out.println("Confirma o fechamento da fatura do cartão " + cartao.getNumeroCartao() +
                        ", valido até " + dataVencimentoCartao +
                        ", de titularidade de " + cartao.getConta().getTitular().getNomeCompleto() +
                        ", portador do CPF " + cartao.getConta().getTitular().getCpf() + "?\n" +
                        "\nDigite \"S\" para sim ou \"N\" para não: ");
                String resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    Fatura fatura = faturaService.fecharFatura(cartao);
                    String dataVencimentoFatura = fatura.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    return "Sua fatura foi fechada com sucesso! Pague até dia " + dataVencimentoFatura + ".\n";
                } else if (resposta.equals("N")) {
                    return "Operação cancelada\n";
                }

                System.out.println("\nDigite somente \"S\" para sim ou \"N\" para não.");
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String pagarFatura(String numeroCartao) {
        try {
            Cartao cartao = cartaoService.buscarCartaoPorNumero(numeroCartao);
            String vencimentoCartao = cartao.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            while (true) {
                System.out.println("Confirma o pagamento da fatura do cartão " + cartao.getNumeroCartao() +
                        ", valido até " + vencimentoCartao +
                        ", de titularidade de " + cartao.getConta().getTitular().getNomeCompleto() +
                        ", portador do CPF " + cartao.getConta().getTitular().getCpf() + "?\n" +
                        "\nDigite \"S\" para sim ou \"N\" para não: ");
                String resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    faturaService.pagarFatura(cartao);

                    return "Sua fatura foi paga com sucesso!\n";
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