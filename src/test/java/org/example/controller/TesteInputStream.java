package org.example.controller;

import java.io.IOException;
import java.io.InputStream;

public class TesteInputStream extends InputStream {
    private String input;
    private int indiceMaisRecente = 0;

    public void setInputs(String input) {
        this.input = input;
        this.indiceMaisRecente = 0;
    }

    @Override
    public int read() throws IOException {
        //ao chegar no final da String deve retornar -1, para indicar o fim
        if (indiceMaisRecente >= input.length()) {
            return -1;
        }

        //Pega o caractere atual da String e add mais um ao indice
        return input.charAt(indiceMaisRecente++);
    }
}
