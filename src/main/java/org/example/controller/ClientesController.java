package org.example.controller;

import org.example.model.Cliente;
import org.example.service.ClienteService;

import java.util.List;
import java.util.Scanner;

public class ClientesController implements Controller {
    private Scanner scanner;

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

        try {
            clienteService.cadastrarCliente(nomeCompleto, cpf, endereco);
            return "Cliente cadastrado com sucesso";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        try {
            clienteService.atualizarCliente(nomeCompleto, cpf, endereco);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Cliente atualizado com sucesso";
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
                return pesquisarClientesPorCPF(partes[3]);

            case "nome":
                return pesquisarClientesPorNome(partes[3]);

            default:
                return "operação inválida";
        }
    }

    public String pesquisarClientesPorNome(String nome) {
        List<Cliente> clientes = clienteService.pesquisarClientePorNome(nome);
        // String clientes2 = clientes.toString();

        if (clientes.isEmpty()) {
            return "Nenhum cliente com esse nome foi encontrado.";
        }

        StringBuilder resultado = new StringBuilder("Clientes encontrados: \n");
        for (Cliente cliente : clientes) {
            resultado.append(cliente.toString()).append("\n");
        }

        return resultado.toString();
    }

    public String pesquisarClientesPorCPF(String cpf) {
        Cliente cliente = clienteService.pesquisarClientePorCPF(cpf);

        if (cliente == null) {
            return "Nenhum cliente com esse CPF foi encontrado.";
        }

        String resultado = "Cliente encontrado: \n";
        resultado += cliente + "\n";

        return resultado;
    }
}
