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
                return "não implementado";

            default:
                return "operação inválida";
        }
    }
}
