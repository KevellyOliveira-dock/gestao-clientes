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
        if (indiceMaisRecente >= input.length()) {
            return -1;
        }

        var bytes = String.valueOf(this.input.charAt(indiceMaisRecente)).getBytes(StandardCharsets.UTF_8);
        indiceMaisRecente++;
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getInt();
    }
}
