package org.example.controller;

import org.example.model.Cliente;
import org.example.service.ClienteService;

import java.util.Scanner;

public class ClientesController implements Controller {
    Scanner scanner;

    //atributo que será injetado no construtor
    private ClienteService clienteService;

    //Injeção de Dependencia -> dependencia é passada para a controller via construtor
    public ClientesController(ClienteService clienteService, Scanner scanner) {
        this.scanner = scanner;
        this.clienteService = clienteService;
    }

    public String executar(String comando) {
        switch (comando) {
            case "clientes":
                return """
                        ------------------------------
                        | atualizar {cpf do cliente} |
                        | cadastrar                  |
                        | desativar {cpf do cliente} |
                        | pesquisar                  |
                        ------------------------------""";

            case "clientes atualizar":
                return "não implementado";

            case "clientes cadastrar":
                return cadastrarCliente();

            case "clientes desativar":
                return "não implementado";

            case "clientes pesquisar":
                return """
                        --------------------------
                        | cpf {cpf do cliente}   |
                        | nome {nome do cliente} |
                        -------------------------""";

            case "clientes pesquisar cpf":
                return "não implementado";

            case "clientes pesquisar nome":
                return "não implementado";

            default:
                return "operação inválida";
        }
    }

    public String cadastrarCliente() {
        System.out.println("Informe seu nome completo: ");
        String nomeCompleto = scanner.nextLine();

        System.out.println("Informe seu CPF: ");
        String cpf = scanner.nextLine();

        System.out.println("Informe seu endereço: ");
        String endereco = scanner.nextLine();

        return clienteService.cadastrarCliente(nomeCompleto, cpf, endereco);
    }
}
