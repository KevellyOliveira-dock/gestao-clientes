package org.example.service;

import org.example.model.Cliente;
import org.example.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {
    @InjectMocks
    private ClienteServiceImpl clientesServiceImpl;

    @Mock
    private ClienteRepository clienteRepository;

    private static final String NOME_CLIENTE = "Kevelly";
    private static final String CPF_CLIENTE = "12345678900";
    private static final String ENDERECO_CLIENTE = "Rua dos testes 56";
    private static final boolean IS_ATIVO_CLIENTE = true;

    @Test
    public void quandoComandoEhClienteCadastrarECpfNaoFoiCadastradoEntaoCadastreComSucesso() throws Exception {
        Cliente cliente = clientesServiceImpl.cadastrarCliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        assertEquals(NOME_CLIENTE, cliente.getNomeCompleto());
        assertEquals(CPF_CLIENTE, cliente.getCpf());
        assertEquals(ENDERECO_CLIENTE, cliente.getEndereco());
    }

    @Test
    public void quandoComandoEhClienteCadastrarVerifiqueSeCadastrouCpfAntesEntaoRetorneMensagem() {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);

        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);
        // () -> : lambda expression
        Exception exception = assertThrows(Exception.class, () ->
                clientesServiceImpl.cadastrarCliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE)
        );
        assertEquals("CPF já cadastrado", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            //nulo
            " , 12345678900, Rua testes 12",
            "Kevelly, , Rua testes 12",
            "Kevelly, 12345678900, ",
            ", , ",

            //vazio
            "'', 12345678900, Rua testes 12",
            "Kevelly, '', Rua testes 12",
            "Kevelly, 12345678900, ''",
            "'', '', ''",
    })
    void quandoComandoEhClienteCadastrarENomeForNuloOuVazioEntaoRetorneMensagemDeErro(
            String nomeCompleto,
            String cpf,
            String endereco
    ) {
        Exception exception = assertThrows(Exception.class, () ->
                clientesServiceImpl.cadastrarCliente(nomeCompleto, cpf, endereco)
        );
        assertEquals("Preencha todos os campos", exception.getMessage());
    }

    @Test
    public void quandoComandoEhClienteAtualizarVerifiqueSeOCpfJaFoiCadastradoEntaoAtualizeComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        Cliente novoCliente = clientesServiceImpl.atualizarCliente(
                "Joana", CPF_CLIENTE, "Rua teste da silva"
        );

        assertEquals("Joana", novoCliente.getNomeCompleto());
        assertEquals(CPF_CLIENTE, novoCliente.getCpf());
        assertEquals("Rua teste da silva", novoCliente.getEndereco());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    //Simula a entrada vazia, esperando que o nome continue NOME_CLIENTE
    @CsvSource({" , Kevelly"})
    public void quandoComandoEhClienteAtualizarVerfiqueSeNomeCompletoEhVazioEntaoRetorneSeuValorAnterior
            (String novoNome, String nomeEsperado) throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        //Simula o input vazio
        if (novoNome == null) {
            novoNome = cliente.getNomeCompleto();
        }

        clientesServiceImpl.atualizarCliente(novoNome, CPF_CLIENTE, ENDERECO_CLIENTE);
        Cliente clienteAtualizado = clientesServiceImpl.buscarClientePorCPF(CPF_CLIENTE);

        assertEquals(nomeEsperado, clienteAtualizado.getNomeCompleto());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    @CsvSource({" , Rua dos testes 56"})
    public void quandoComandoEhAtualizarClienteVerfiqueSeEnderecoEhVazioEntaoRetorneSeuValorAnterior
            (String novoEndereco, String enderecoEsperado) throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        if (novoEndereco == null) {
            novoEndereco = cliente.getEndereco();
        }

        clientesServiceImpl.atualizarCliente("Ana", CPF_CLIENTE, novoEndereco);
        Cliente clienteAtualizado = clientesServiceImpl.buscarClientePorCPF(CPF_CLIENTE);

        assertEquals(enderecoEsperado, clienteAtualizado.getEndereco());
    }

    @Test
    public void quandoComandoEhClientePesquisarCpfENaoEncontrarUmClienteEntaoRetorneErro() {
        when(clienteRepository.buscarPorCPF("0123456789")).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            clientesServiceImpl.buscarClientePorCPF("0123456789");

        });
        assertEquals("Cliente não encontrado. Cadastre-se e tente novamente.\n", exception.getMessage());
    }

    @Test
    public void quandoComandoEhClienteBuscarPorCpfForValidoEntaoRetorneCliente() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);

        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        Cliente resultado = clientesServiceImpl.buscarClientePorCPF(CPF_CLIENTE);

        assertEquals(NOME_CLIENTE, resultado.getNomeCompleto());
        assertEquals(CPF_CLIENTE, resultado.getCpf());
        assertEquals(ENDERECO_CLIENTE, resultado.getEndereco());
    }

    @Test
    public void quandoComandoEhClientePesquisarNomeEntaoListeTodosOsClientesComEsseNome() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE, IS_ATIVO_CLIENTE);
        Cliente cliente2 = new Cliente(
                "Joana Silva", "0000000", "rua Unitarios 123", true
        );
        Cliente cliente3 = new Cliente(
                "Carol silveira", "9999999", "rua Unitarios 123", IS_ATIVO_CLIENTE
        );

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        clientes.add(cliente2);
        clientes.add(cliente3);

        var busca = "sil";
        when(clienteRepository.buscarValores(busca)).thenReturn(clientes);
        List<Cliente> resultado = clientesServiceImpl.pesquisarClientePorNome(busca);

        // Testando usando a função get da lista (modo imperativo)
        assertEquals(2, resultado.size());
        assertTrue(resultado.get(0).getNomeCompleto().toLowerCase().contains(busca));
        assertTrue(resultado.get(1).getNomeCompleto().toLowerCase().contains(busca));

        // Testando usando streams (modo funcional)
        assertTrue(resultado.stream()  // Stream transforma a lista em um fluxo de dados
                .allMatch( // Verifica se todos os elementos da lista atendem à condição.
                        // Para cada cliente na lista, compara o nome do cliente
                        // e ignora as diferenças entre maiusculas e minusculas
                        c -> c.getNomeCompleto().toLowerCase().contains(busca)));
    }

    @Test
    public void quandoComandoEhClientePesquisarNomeENaoEncontrarClientesEntaoRetorneErro() throws Exception {
        Exception exception = assertThrows(Exception.class, () ->
                clientesServiceImpl.pesquisarClientePorNome(NOME_CLIENTE)
        );
        assertEquals("Cliente não encontrado. Cadastre-se e tente novamente.\n", exception.getMessage());
    }
}
