package org.example;

import java.util.Scanner;

import org.example.controller.*;
import org.example.service.ClientesService;
import org.example.service.ClientesServiceImpl;
import org.example.service.ContasService;
import org.example.service.ContasServiceImpl;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Seja bem-vindo! -----------");

        //inicializa a ClienteService | dependencia criada fora da controller
        ClientesService clientesService = new ClientesServiceImpl();
        ContasService contaService = new ContasServiceImpl(clientesService);

        var cartoesController = new CartoesController();
        //Injeção de dependência -> passar a dependencia (ClienteService) ao invés de criar dentro do ClientesController
        var clientesController = new ClientesController(clientesService, scanner); //injeção de dependencia
        var contasController = new ContasController(contaService, scanner);
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

            String resultado;
            try {
                resultado = frontController.executar(comando);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            System.out.println(resultado);
        }
    }
}
