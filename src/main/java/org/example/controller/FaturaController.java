package org.example.controller;

import org.example.service.FaturaService;

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
                return "não implementado";

            case "pagar":
                return "não implementado";

            default:
                return "operação inválida";
        }
    }
}
