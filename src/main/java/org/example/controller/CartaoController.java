package org.example.controller;

import org.example.service.CartaoService;

import java.util.Scanner;

public class CartaoController implements Controller {
    private Scanner scanner;
    private CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService, Scanner scanner) {
        this.scanner = scanner;
        this.cartaoService = cartaoService;
    }

    public String executar(String comando) throws Exception {
        if (comando.equals("cartoes")) {
            return """
                    -------------------------------
                    | Bloquear {número do cartao} |
                    | Cadastrar                   |
                    -------------------------------""";
        }

        String[] partes = comando.split(" ");
        var acao = partes[1];

        switch (acao) {
            case "bloquear":
                return "não implementado";

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
            return cartaoService.cadastrarCartao(cpf, conta) + "Cartão criado com sucesso";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
