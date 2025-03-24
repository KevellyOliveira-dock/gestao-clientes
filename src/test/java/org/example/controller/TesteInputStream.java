package org.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TesteInputStream extends InputStream {
    private List<String> inputs;
    private int indiceMaisRecente = 0;

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
        this.indiceMaisRecente = 0;
    }

    @Override
    public int read() throws IOException {
        var bytes = this.inputs.get(indiceMaisRecente).getBytes(StandardCharsets.UTF_8);
        indiceMaisRecente++;
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getInt();
    }
}
