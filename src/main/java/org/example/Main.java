package org.example;

import java.util.Scanner;
import org.example.controller.FrontController;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------- Seja bem-vindo! -----------");

        while (true) {
            System.out.println("Insira um comando ou aperte Enter para exibir os comandos possíveis.\n" +
                    "Aperte Ctrl + c para sair");

            String comando = scanner.nextLine();

            var frontController = new FrontController(comando);
        }
    }
}
