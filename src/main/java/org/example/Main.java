package org.example;

import java.util.Scanner;

import org.example.controller.*;
import org.example.service.ClienteService;
import org.example.service.ClienteServiceImpl;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Seja bem-vindo! -----------");

        //inicializa a ClienteService | dependencia criada fora da controller
        ClienteService clienteService = new ClienteServiceImpl();

        var cartoesController = new CartoesController();
        //Injeção de dependência -> passar a dependencia (ClienteService) ao invés de criar dentro do ClientesController
        var clientesController = new ClientesController(clienteService, scanner); //injeção de dependencia
        var contasController = new ContasController();
        var faturasController = new FaturasController();

        var frontController = new FrontController(
                cartoesController,
                clientesController,
                contasController,
                faturasController
        );

        while (true) {
            System.out.println("""
                Insira um comando ou aperte Enter para exibir os comandos possíveis
                "Aperte Ctrl + c para sair""");

            String comando = scanner.nextLine();

            String resultado = frontController.executar(comando);

            System.out.println(resultado);
        }
    }
}
