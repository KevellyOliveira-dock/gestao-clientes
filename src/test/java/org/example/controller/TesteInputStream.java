package org.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
        char caractere =  input.charAt(indiceMaisRecente++);

        //char sendo convertido diretamente em int (type casting explícito - maior para menor)
        return (int) caractere;

        //Não daria certo porque o inputStrem retorna valores de 0 a 255, transformar em bytes gera valores invalidos
        //var bytes = String.valueOf(this.input.charAt(indiceMaisRecente)).getBytes(StandardCharsets.UTF_8);
        //indiceMaisRecente++;
        //ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        //buffer.put(bytes);
        //buffer.rewind();
        //return buffer.getInt();
    }
}
