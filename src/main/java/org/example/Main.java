package org.example;

import java.util.Scanner;

import org.example.controller.CartaoController;
import org.example.controller.ClienteController;
import org.example.controller.ContaController;
import org.example.controller.FaturaController;
import org.example.controller.FrontController;
import org.example.repository.ClienteRepository;
import org.example.repository.InMemoryClienteRepository;
import org.example.repository.CartaoRepository;
import org.example.repository.InMemoryCartaoRepository;
import org.example.repository.ContaRepository;
import org.example.repository.InMemoryContaRepository;
import org.example.repository.FaturaRepository;
import org.example.repository.InMemoryFaturaRepository;
import org.example.service.ClienteService;
import org.example.service.ClienteServiceImpl;
import org.example.service.ContaService;
import org.example.service.ContaServiceImpl;
import org.example.service.CartaoService;
import org.example.service.CartaoServiceImpl;
import org.example.service.FaturaService;
import org.example.service.FaturaServiceImpl;
import org.example.service.ClienteDesativacaoService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Seja bem-vindo! -----------");

        ClienteRepository clienteRepository = new InMemoryClienteRepository();
        CartaoRepository cartaoRepository = new InMemoryCartaoRepository();
        ContaRepository contaRepository = new InMemoryContaRepository();
        FaturaRepository faturaRepository = new InMemoryFaturaRepository();

        //inicializa a ClienteService | dependencia criada fora da controller
        ClienteService clienteService = new ClienteServiceImpl(clienteRepository);
        CartaoService cartaoService = new CartaoServiceImpl(cartaoRepository);
        FaturaService faturaService = new FaturaServiceImpl(faturaRepository);
        ContaService contaService = new ContaServiceImpl(contaRepository, cartaoService, clienteService, faturaService);

        ClienteDesativacaoService clienteDesativacaoService = new ClienteDesativacaoService(clienteService, contaService);

        var cartoesController = new CartaoController(cartaoService, scanner, contaService);
        //Injeção de dependência -> passar a dependencia (ClienteService) ao invés de criar dentro do ClientesController
        var clientesController = new ClienteController(clienteService, scanner, clienteDesativacaoService); //injeção de dependencia
        var contasController = new ContaController(contaService, scanner);
        var faturasController = new FaturaController(faturaService, scanner, cartaoService);

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