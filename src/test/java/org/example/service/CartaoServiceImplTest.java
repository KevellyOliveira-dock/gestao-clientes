package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {

    private CartaoServiceImpl cartaoServiceImpl;

    @Mock
    private ClienteService clienteService;

    @Mock
    private  ContaService contaService;

    @BeforeEach
    public void setup() {
        cartaoServiceImpl = new CartaoServiceImpl(clienteService, contaService);
    }
}
