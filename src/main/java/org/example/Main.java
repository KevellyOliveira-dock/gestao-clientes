package org.example;

import java.util.Scanner;
import org.example.controller.CartaoController;
import org.example.controller.ClienteController;
import org.example.controller.ContaController;
import org.example.controller.FaturaController;
import org.example.controller.FrontController;
import org.example.service.ClienteService;
import org.example.service.ClienteServiceImpl;
import org.example.service.ContaService;
import org.example.service.ContaServiceImpl;
import org.example.service.CartaoService;
import org.example.service.CartaoServiceImpl;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Seja bem-vindo! -----------");

        //inicializa a ClienteService | dependencia criada fora da controller
        ClienteService clienteService = new ClienteServiceImpl();
        ContaService contaService = new ContaServiceImpl(clienteService);
        CartaoService cartaoService = new CartaoServiceImpl(clienteService, contaService);

        var cartoesController = new CartaoController(cartaoService, scanner);
        //Injeção de dependência -> passar a dependencia (ClienteService) ao invés de criar dentro do ClientesController
        var clientesController = new ClienteController(clienteService, scanner); //injeção de dependencia
        var contasController = new ContaController(contaService, scanner, cartaoService);
        var faturasController = new FaturaController();

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