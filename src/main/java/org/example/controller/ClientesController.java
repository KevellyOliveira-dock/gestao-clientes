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
        if (comando.equals("clientes")) {
            return """
                    ------------------------------
                    | atualizar {cpf do cliente} |
                    | cadastrar                  |
                    | desativar {cpf do cliente} |
                    | pesquisar                  |
                    ------------------------------""";
        }

        String[] partes = comando.split(" ");
        var acao = partes[1];

        switch (acao) {
            case "atualizar":
                if (partes.length == 3) {
                    return atualizarCliente(partes[2]);
                } else {
                    return "Para atualizar é necessário informar o CPF. Ex: clientes atualizar 12345678901";
                }

            case "cadastrar":
                return cadastrarCliente();

            case "desativar":
                return "não implementado";

            case "pesquisar":
                return pesquisarCliente(partes);

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

    public String atualizarCliente(String cpf) {
        Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);

        if (clienteExistente == null) {
            return "CPF não cadastrado";
        }

        System.out.println("Nome: [" + clienteExistente.getNomeCompleto() + "]");
        String nomeCompleto = scanner.nextLine();

        System.out.println("Endereço: [" + clienteExistente.getEndereco() + "]");
        String endereco = scanner.nextLine();

        return clienteService.atualizarCliente(nomeCompleto, cpf, endereco);
    }

    private String pesquisarCliente(String[] partes) {
        if (partes.length == 2) {
            return """
                    --------------------------
                    | cpf {cpf do cliente}   |
                    | nome {nome do cliente} |
                    -------------------------""";
        }

        switch (partes[2]) {
            case "cpf":
                return "não implementado";

            case "nome":
                return "não implementado";

            default:
                return "operação inválida";
        }
    }
}
