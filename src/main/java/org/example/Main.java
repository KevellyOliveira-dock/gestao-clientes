package org.example;

import java.util.Scanner;
import org.example.controller.FrontController;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Seja bem-vindo! -----------");

        var frontController = new FrontController();

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
