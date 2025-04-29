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

    public String executar(String comando) {
        switch (comando) {
            case "cartoes":
                return """
                        -------------------------------
                        | Bloquear {número do cartao} |
                        | Cadastrar                   |
                        -------------------------------""";

            case "cartoes bloquear":
                return "não implementado";

            case "cartoes cadastrar":
                return "não implementado";

            default:
                return "operação inválida";
        }
    }
}
