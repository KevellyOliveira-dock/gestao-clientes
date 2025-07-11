package org.example.controller;

import org.example.model.Cliente;
import org.example.service.ClienteDesativacaoService;
import org.example.service.ClienteService;

import java.util.List;
import java.util.Scanner;

public class ClienteController implements Controller {
    private Scanner scanner;

    //atributo que será injetado no construtor
    private final ClienteService clienteService;

    private final ClienteDesativacaoService clienteDesativacaoService;

    //Injeção de Dependencia -> dependencia é passada para a controller via construtor
    public ClienteController(ClienteService clienteService,
                             Scanner scanner,
                             ClienteDesativacaoService clienteDesativacaoService) {
        this.scanner = scanner;
        this.clienteService = clienteService;
        this.clienteDesativacaoService = clienteDesativacaoService;
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
                if (partes.length == 3) {
                    return desativarCliente(partes[2]);
                } else {
                    return "Para desativar é necessário informar o CPF. Ex: clientes desativar 12345678901.\n";
                }


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
            return "Cliente cadastrado com sucesso\n";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String atualizarCliente(String cpf) throws Exception {
        Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);

        if (clienteExistente == null) {
            return "CPF não cadastrado. Cadastre-se e tente novamente.\n";
        }

        System.out.println("Nome: [" + clienteExistente.getNomeCompleto() + "]");
        String nomeCompleto = scanner.nextLine();

        System.out.println("Endereço: [" + clienteExistente.getEndereco() + "]");
        String endereco = scanner.nextLine();

        try {
            clienteService.atualizarCliente(nomeCompleto, cpf, endereco);
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

    public String pesquisarClientesPorNome(String nome) throws Exception {
        List<Cliente> clientes = clienteService.pesquisarClientePorNome(nome);

        StringBuilder resultado = new StringBuilder("Clientes encontrados: \n");
        for (Cliente cliente : clientes) {
            resultado.append(cliente.toString()).append("\n");
        }

        return resultado.toString();
    }

    public String pesquisarClientesPorCPF(String cpf) {
        try {
            Cliente cliente = clienteService.buscarClientePorCPF(cpf);
            return "Cliente encontrado: \n" + cliente.toString();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String desativarCliente(String cpf) {
        try {
            Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);
            String resposta;

            while (true) {
                System.out.println("Confirma a desativação da cliente " + clienteExistente.getNomeCompleto() +
                        ", portador do CPF " + clienteExistente.getCpf() +
                        "?\n" + "Digite \"S\" para sim ou \"N\" para não: ");
                resposta = scanner.nextLine().toUpperCase();

                if (resposta.equals("S")) {
                    clienteDesativacaoService.desativarCliente(cpf);
                    return "Cliente desativado com sucesso.\n";
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
