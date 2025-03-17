package org.example.controller;

public class FrontController {
    public FrontController(String comando) {
        if (!comando.isEmpty()) {
            switch (comando) {
                case "cartoes":
                    System.out.println("    -------------------------------");
                    System.out.println("    | Bloquear {número do cartao} |");
                    System.out.println("    | Cadastrar                   |");
                    System.out.println("    -------------------------------");
                    break;

                case "cartoes bloquear":
                    System.out.println("não implementado");
                    break;

                case "cartoes cadastrar":
                    System.out.println("não implementado");
                    break;

                case "clientes":
                    System.out.println("    ------------------------------");
                    System.out.println("    | atualizar {cpf do cliente} |");
                    System.out.println("    | cadastrar                  |");
                    System.out.println("    | desativar {cpf do cliente} |");
                    System.out.println("    | pesquisar                  |");
                    System.out.println("    ------------------------------");
                    break;

                case "clientes atualizar":
                    System.out.println("não implementado");
                    break;

                case "clientes cadastrar":
                    System.out.println("não implementado");
                    break;

                case "clientes desativar":
                    System.out.println("não implementado");
                    break;

                case "clientes pesquisar":
                    System.out.println("    --------------------------");
                    System.out.println("    | cpf {cpf do cliente}   |");
                    System.out.println("    | nome {nome do cliente} |");
                    System.out.println("    --------------------------");
                    break;

                case "clientes pesquisar cpf":
                    System.out.println("não implementado");
                    break;

                case "clientes pesquisar nome":
                    System.out.println("não implementado");
                    break;

                case "contas":
                    System.out.println("    -------------------------------");
                    System.out.println("    | cadastrar                   |");
                    System.out.println("    | desativar {numero da conta} |");
                    System.out.println("    | extrato {numero da conta}   |");
                    System.out.println("    | pesquisar                   |");
                    System.out.println("    -------------------------------");
                    break;

                case "contas cadastrar":
                    System.out.println("não implementado");
                    break;

                case "contas desativar":
                    System.out.println("não implementado");
                    break;

                case "contas extrato":
                    System.out.println("não implementado");
                    break;

                case "contas pesquisar":
                    System.out.println("    ----------------------------------");
                    System.out.println("    | cpf-titular {cpf do cliente}   |");
                    System.out.println("    | nome-titular {nome do cliente} |");
                    System.out.println("    | numero {número da conta}       |");
                    System.out.println("    ----------------------------------");
                    break;

                case "contas pesquisar cpf-titular":
                    System.out.println("não implementado");
                    break;

                case "contas pesquisar nome-titular":
                    System.out.println("não implementado");
                    break;

                case "contas pesquisar numero":
                    System.out.println("não implementado");
                    break;

                case "faturas":
                    System.out.println("    -----------------------------");
                    System.out.println("    | fechar {número do cartão} |");
                    System.out.println("    -----------------------------");
                    break;

                case "faturas fechar":
                    System.out.println("não implementado");
                    break;

                default:
                    System.out.println("operação inválida");
            }
        } else {
            System.out.println("    ------------");
            System.out.println("    | cartoes  |");
            System.out.println("    | clientes |");
            System.out.println("    | contas   |");
            System.out.println("    | faturas  |");
            System.out.println("    ------------");
        }
    }
}
