package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Seja bem-vindo! -----------");

        while (true) {
            System.out.println("Insira um comando ou aperte Enter para exibir os comandos possíveis.\n" +
                    "Aperte Ctrl + c para sair");
            System.out.print("Digite o serviço gostaria de acessar: ");

            String comando = scanner.nextLine();

            if (!comando.isEmpty()) {
                switch (comando) {
                    case "cartoes":
                        System.out.println(" -------------------------------");
                        System.out.println(" | Bloquear {número do cartao} |");
                        System.out.println(" | Cadastrar                   |");
                        System.out.println(" -------------------------------");

                        String subCartoes = scanner.nextLine();

                        switch (subCartoes) {
                            case "bloquear" -> System.out.println("processo bloquear cartoes");
                            case "cadastrar" -> System.out.println("processo cadastrar cartoes");
                            default -> System.out.println(" operação inválida");
                        }

                        break;

                    case "cartoes bloquear":
                        System.out.println("operação sequencial bloquear cartoes");
                        break;
                    case "cartoes cadastrar":
                        System.out.println("operação sequencial cadastrar cartao");
                        break;

                    case "clientes":
                        System.out.println(" ------------------------------");
                        System.out.println(" | atualizar {cpf do cliente} |");
                        System.out.println(" | cadastrar                  |");
                        System.out.println(" | desativar {cpf do cliente} |");
                        System.out.println(" | pesquisar                  |");
                        System.out.println(" ------------------------------");

                        String subClientes = scanner.nextLine();

                        switch (subClientes) {
                            case "atualizar" -> System.out.println("processo clientes atualizar");
                            case "cadastrar" -> System.out.println("processo clientes cadastrar");
                            case "desativar" -> System.out.println("processo clientes desativar");
                            case "pesquisar" -> {
                                System.out.println(" --------------------------");
                                System.out.println(" | cpf {cpf do cliente}   |");
                                System.out.println(" | nome {nome do cliente} |");
                                System.out.println(" --------------------------");

                                String subClientesPesq = scanner.nextLine();

                                switch (subClientesPesq) {
                                    case "cpf" -> System.out.println("processo clientes pesquisar cpf");
                                    case "nome" -> System.out.println("processo clientes pesquisar nome");
                                    default -> System.out.println(" operação inválida");
                                }
                            }
                            default -> System.out.println(" operação inválida");
                        }
                        break;

                    case "clientes atualizar":
                        System.out.println("operação sequencial clientes atualizar");
                        break;
                    case "clientes cadastrar":
                        System.out.println("operação sequencial clientes cadastra");
                        break;
                    case "clientes desativar":
                        System.out.println("operação sequencial clientes desativar");
                        break;
                    case "clientes pesquisar":
                        System.out.println(" --------------------------");
                        System.out.println(" | cpf {cpf do cliente}   |");
                        System.out.println(" | nome {nome do cliente} |");
                        System.out.println(" --------------------------");

                        String subCartoesPesq = scanner.nextLine();

                        switch (subCartoesPesq) {
                            case "cpf" -> System.out.println("processo clientes pesquisar cpf");
                            case "nome" -> System.out.println("processo clientes pesquisar nome");
                            default -> System.out.println(" operação inválida");
                        }
                        break;


                    case "clientes pesquisar cpf":
                        System.out.println("operacao sequencial cliente pesquisar cpf");
                        break;

                    case "clientes pesquisar nome":
                        System.out.println("operacao sequencial cliente pesquisar nome");
                        break;

                    case "contas":
                        System.out.println(" -------------------------------");
                        System.out.println(" | cadastrar                   |");
                        System.out.println(" | desativar {numero da conta} |");
                        System.out.println(" | extrato {numero da conta}   |");
                        System.out.println(" | pesquisar                   |");
                        System.out.println(" -------------------------------");

                        String subContas = scanner.nextLine();

                        switch (subContas) {
                            case "cadastrar" -> System.out.println("processo contas cadastrar");
                            case "desativar" -> System.out.println("processo contas desativar");
                            case "extrato" -> System.out.println("processo contas extrato");
                            case "pesquisar" -> {
                                System.out.println(" ----------------------------------");
                                System.out.println(" | cpf-titular {cpf do cliente}   |");
                                System.out.println(" | nome-titular {nome do cliente} |");
                                System.out.println(" | numero {número da conta}       |");
                                System.out.println(" ----------------------------------");

                                String subContasPesq = scanner.nextLine();

                                switch (subContasPesq) {
                                    case "cpf" -> System.out.println("processo contas pesquisar cpf-titular");
                                    case "nome" -> System.out.println("processo contas pesquisar nome-titular");
                                    case "numero" -> System.out.println("processo contas pesquisar numero");
                                    default -> System.out.println(" operação inválida");
                                }
                            }
                            default -> System.out.println("operação inválida");
                        }
                        break;

                    case "contas cadastrar":
                        System.out.println("operação sequencial contas cadastrar");
                        break;
                    case "contas desativar":
                        System.out.println("operação sequencial ontas desativar");
                        break;
                    case "contas extrato":
                        System.out.println("operação sequencial contas extrato");
                        break;
                    case "contas pesquisar":
                        System.out.println(" ----------------------------------");
                        System.out.println(" | cpf-titular {cpf do cliente}   |");
                        System.out.println(" | nome-titular {nome do cliente} |");
                        System.out.println(" | numero {número da conta}       |");
                        System.out.println(" ----------------------------------");

                        String ContasPesq = scanner.nextLine();

                        switch (ContasPesq) {
                            case "cpf-titular" -> System.out.println("processo contas pesquisar cpf");
                            case "nome-titular" -> System.out.println("processo contas pesquisar nome");
                            case "numero" -> System.out.println("processo contas pesquisar numero");
                            default -> System.out.println(" operação inválida");
                        }
                        break;

                    case "contas pesquisar cpf-titular":
                        System.out.println("operacao sequencial contas pesquisar cpf-titular");
                        break;

                    case "contas pesquisar nome-titular":
                        System.out.println("operacao sequencial ontas pesquisar nome-titulare");
                        break;

                    case "contas pesquisar numero":
                        System.out.println("operacao sequencial contas pesquisar numero");
                        break;

                    case "faturas":
                        System.out.println(" -----------------------------");
                        System.out.println(" | fechar {número do cartão} |");
                        System.out.println(" -----------------------------");
                        String subFaturas = scanner.nextLine();

                        if (subFaturas.equals("fechar")) {
                            System.out.println("Operação fechar fatura");
                        } else {
                            System.out.println("operação inválida");
                        }
                        break;

                    case "faturas fechar":
                        System.out.println("operação sequencial faturas fechar");
                        break;
                    default:
                        System.out.println("operação inválida");
                }
            } else {
                System.out.println(" ------------");
                System.out.println(" | cartoes  |");
                System.out.println(" | clientes |");
                System.out.println(" | contas   |");
                System.out.println(" | faturas  |");
                System.out.println(" ------------");
            }
        }
    }
}
