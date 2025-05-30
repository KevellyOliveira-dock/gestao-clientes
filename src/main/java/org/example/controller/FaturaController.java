package org.example.controller;

import org.example.model.Fatura;
import org.example.service.FaturaService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FaturaController implements Controller {
    private final Scanner scanner;
    private final FaturaService faturaService;

    public FaturaController(FaturaService faturaService, Scanner scanner) {
        this.faturaService = faturaService;
        this.scanner = scanner;
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
            while (true) {
                System.out.println("Confirma o fechamento da fatura do cartão " + numeroCartao +
                        "?\nDigite \"S\" para sim ou \"N\" para não: ");
                String resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    Fatura fatura = faturaService.fecharFatura(numeroCartao);
                    String dataVencimentoFatura = fatura.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String dataVencimentoCartao = fatura.getCartao().getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    return "Sua fatura foi fechada com sucesso! Pague até dia " + dataVencimentoFatura + ".\n" +
                            "Cartão de número " + fatura.getCartao().getNumeroCartao() +
                            ", valido até " + dataVencimentoCartao +
                            ", Titularidade de " + fatura.getCartao().getCliente().getNomeCompleto() +
                            ", portador do CPF " + fatura.getCartao().getCliente().getCpf() + ".\n";
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
            while (true) {
                System.out.println("Confirma o pagamento da fatura do cartão " + numeroCartao +
                        "?\nDigite \"S\" para sim ou \"N\" para não: ");
                String resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    Fatura fatura = faturaService.pagarFatura(numeroCartao);

                    String vencimentoFatura = fatura.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String vencimentoCartao = fatura.getCartao().getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    return "Sua fatura foi paga com sucesso! Pague até dia " + vencimentoFatura + ".\n" +
                            "Cartão de número " + fatura.getCartao().getNumeroCartao() +
                            ", valido até " + vencimentoCartao +
                            ", Titularidade de " + fatura.getCartao().getCliente().getNomeCompleto() +
                            ", portador do CPF " + fatura.getCartao().getCliente().getCpf() + ".\n";
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