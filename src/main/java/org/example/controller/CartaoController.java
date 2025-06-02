package org.example.controller;

import org.example.model.Cartao;
import org.example.model.Conta;
import org.example.service.CartaoService;
import org.example.service.ContaService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CartaoController implements Controller {
    private final Scanner scanner;
    private final CartaoService cartaoService;
    private final ContaService contaService;

    public CartaoController(CartaoService cartaoService, Scanner scanner, ContaService contaService) {
        this.scanner = scanner;
        this.cartaoService = cartaoService;
        this.contaService = contaService;
    }

    public String executar(String comando) throws Exception {
        if (comando.equals("cartoes")) {
            return """
                    ----------------------------------
                    | Bloquear {número do cartao}    |
                    | Desbloquear {número do cartao} |
                    | Cadastrar                      |
                    ----------------------------------""";
        }

        String[] partes = comando.split(" ");
        var acao = partes[1];

        switch (acao) {
            case "bloquear":
                if (partes.length == 3) {
                    return bloquearCartao(partes[2]);
                } else {
                    return "Para bloquear o cartão é necessário informar o número. Ex: cartoes bloquear 0123.\n";
                }

            case "desbloquear":
                if (partes.length == 3) {
                    return desbloquearCartao(partes[2]);
                } else {
                    return "Para desbloquear o cartão é necessário informar o número. Ex: cartoes desbloquear 0123.\n";
                }

            case "cadastrar":
                return cadastrarCartao();

            default:
                return "operação inválida";
        }
    }

    public String cadastrarCartao() throws Exception {
        System.out.println("Informe sua conta: ");
        String numeroConta = scanner.nextLine();
        Conta conta = contaService.buscarContaPorNumero(numeroConta);

        try {
            Cartao cartaoCriado = cartaoService.cadastrarCartao(conta);
            String dataVencimentoCartao = cartaoCriado.getDataVencimento().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            return "Cartão criado com sucesso!\n" +
                    "O cliente " + cartaoCriado.getConta().getTitular().getNomeCompleto() +
                    ", de conta número " + cartaoCriado.getConta().getNumeroConta() +
                    ", acionou um novo cartão: " +
                    "\nData de vencimento: " + dataVencimentoCartao + "." +
                    "\nNúmero do cartão: " + cartaoCriado.getNumeroCartao() + "." +
                    "\nCVV: " + cartaoCriado.getCvv() + ".\n";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String bloquearCartao(String numeroCartao) {
        try {
            Cartao cartaoExistente = cartaoService.buscarCartaoPorNumero(numeroCartao);
            String dataVencimento = cartaoExistente.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String resposta;

            while (true) {
                System.out.println("Confirma o bloqueio do cartão " + cartaoExistente.getNumeroCartao() +
                        ", com vencimento em " + dataVencimento +
                        ", de titularidade de " + cartaoExistente.getConta().getTitular().getNomeCompleto() +
                        ", portador do CPF " + cartaoExistente.getConta().getTitular().getCpf() +
                        "?\nDigite \"S\" para sim ou \"N\" para não: ");
                resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    cartaoService.bloquearCartao(numeroCartao);
                    return "Seu cartão foi bloqueado com sucesso!\n";
                } else if (resposta.equals("N")) {
                    return "Operação cancelada\n";
                }

                System.out.println("\nDigite somente \"S\" para sim ou \"N\" para não.");
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String desbloquearCartao(String numeroCartao) {
        try {
            Cartao cartaoExistente = cartaoService.buscarCartaoPorNumero(numeroCartao);
            String dataVencimento = cartaoExistente.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String resposta;

            while (true) {
                System.out.println("Confirma o desbloqueio do cartão " + cartaoExistente.getNumeroCartao() +
                        ", com vencimento em " + dataVencimento +
                        ", de titularidade de " + cartaoExistente.getConta().getTitular().getNomeCompleto() +
                        ", portador do CPF " + cartaoExistente.getConta().getTitular().getCpf() +
                        "?\nDigite \"S\" para sim ou \"N\" para não: ");
                resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    cartaoService.desbloquearCartao(numeroCartao);
                    return "Seu cartão foi desbloqueado com sucesso!\n";
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
