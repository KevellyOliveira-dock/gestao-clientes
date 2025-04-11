package org.example.controller;

import org.example.model.Cliente;
import org.example.service.ClientesService;

import java.util.List;
import java.util.Scanner;

public class ClientesController implements Controller {
    private Scanner scanner;

    //atributo que será injetado no construtor
    private ClientesService clientesService;

    //Injeção de Dependencia -> dependencia é passada para a controller via construtor
    public ClientesController(ClientesService clientesService, Scanner scanner) {
        this.scanner = scanner;
        this.clientesService = clientesService;
    }

    public String executar(String comando) throws Exception {
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
                    return "Para atualizar é necessário informar o CPF. Ex: clientes atualizar 12345678901.\n";
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
            clientesService.cadastrarCliente(nomeCompleto, cpf, endereco);
            return "Cliente cadastrado com sucesso\n";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String atualizarCliente(String cpf) throws Exception {
        Cliente clienteExistente = clientesService.buscarClientePorCPF(cpf);

        if (clienteExistente == null) {
            return "CPF não cadastrado. Cadastre-se e tente novamente.\n";
        }

        System.out.println("Nome: [" + clienteExistente.getNomeCompleto() + "]");
        String nomeCompleto = scanner.nextLine();

        System.out.println("Endereço: [" + clienteExistente.getEndereco() + "]");
        String endereco = scanner.nextLine();

        try {
            clientesService.atualizarCliente(nomeCompleto, cpf, endereco);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Cliente atualizado com sucesso\n";
    }

    private String pesquisarCliente(String[] partes) throws Exception {
        if (partes.length == 2) {
            return """
                    --------------------------
                    | cpf {cpf do cliente}   |
                    | nome {nome do cliente} |
                    -------------------------""";
        }

        switch (partes[2]) {
            case "cpf":
                if (partes.length > 3) {
                    return pesquisarClientesPorCPF(partes[3]);
                } else {
                    return "Informe o CPF que deseja pesquisar. Ex: clientes pesquisar cpf 12345678900.\n";
                }

            case "nome":
                if (partes.length > 3) {
                    return pesquisarClientesPorNome(partes[3]);
                } else {
                    return "Informe o nome que deseja pesquisar. Ex: clientes pesquisar nome Joao Silva.\n";
                }

            default:
                return "operação inválida";
        }
    }

    public String pesquisarClientesPorNome(String nome) {
        List<Cliente> clientes = clientesService.pesquisarClientePorNome(nome);
        //String clientes2 = clientes.toString();

        if (clientes.isEmpty()) {
            return "Cliente não encontrado. Cadastre-se e tente novamente.\n";
        }

        StringBuilder resultado = new StringBuilder("Clientes encontrados: \n");
        for (Cliente cliente : clientes) {
            resultado.append(cliente.toString()).append("\n");
        }

        return resultado.toString();
    }

    public String pesquisarClientesPorCPF(String cpf) throws Exception {
        Cliente cliente = clientesService.buscarClientePorCPF(cpf);

        if (cliente == null) {
            return "Cliente não encontrado. Cadastre-se e tente novamente.\n";
        }

        return cliente.toString();
    }
}
