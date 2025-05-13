package org.example.controller;

import org.example.model.Cartao;
import org.example.service.CartaoService;

import java.util.Scanner;

public class CartaoController implements Controller {
    private final Scanner scanner;
    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService, Scanner scanner) {
        this.scanner = scanner;
        this.cartaoService = cartaoService;
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

    public String cadastrarCartao() {
        System.out.println("Informe seu CPF: ");
        String cpf = scanner.nextLine();

        System.out.println("Informe sua conta: ");
        String conta = scanner.nextLine();

        try {
            return "Cartão criado com sucesso!\n" + cartaoService.cadastrarCartao(cpf, conta).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String bloquearCartao(String numeroCartao) {
        try {
            Cartao cartaoExistente = cartaoService.buscarCartaoPorNumero(numeroCartao);
            String resposta;

            while (true) {
                System.out.println("Confirma o bloqueio do cartão " + cartaoExistente.getNumeroCartao() +
                        ", com vencimento em " + cartaoExistente.getDtVencimento() +
                        ", de titularidade de " + cartaoExistente.getCliente().getNomeCompleto() +
                        ", portador do CPF " + cartaoExistente.getCliente().getCpf() +
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
            String resposta;

            while (true) {
                System.out.println("Confirma o desbloqueio do cartão " + cartaoExistente.getNumeroCartao() +
                        ", com vencimento em " + cartaoExistente.getDtVencimento() +
                        ", de titularidade de " + cartaoExistente.getCliente().getNomeCompleto() +
                        ", portador do CPF " + cartaoExistente.getCliente().getCpf() +
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
