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

    @Test
    public void quandoClientesCadastrarVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso() throws Exception {
        Cliente cliente = clientesServiceImpl.cadastrarCliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        assertEquals(NOME_CLIENTE, cliente.getNomeCompleto());
        assertEquals(CPF_CLIENTE, cliente.getCpf());
        assertEquals(ENDERECO_CLIENTE, cliente.getEndereco());
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
    void quandoClientesCadastrarENomeForNuloOuVazioEntaoRetorneMensagemDeErro(String nomeCompleto,
                                                                              String cpf,
                                                                              String endereco) {
        Exception exception = assertThrows(Exception.class, () ->
                clientesServiceImpl.cadastrarCliente(nomeCompleto, cpf, endereco)
        );
        assertEquals("Preencha todos os campos", exception.getMessage());
    }

    @Test
    public void quandoClientesCadastrarEntaoVerifiqueSeCadastrouChaveAntes() {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);

        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);
        // () -> : lambda expression
        Exception exception = assertThrows(Exception.class, () ->
                clientesServiceImpl.cadastrarCliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE)
        );
        assertEquals("CPF já cadastrado", exception.getMessage());
    }

    // Verificação do pesquisar CPF
    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoBuscarClientePorCpfForVazioOuNuloEntaoNaoRealizarCadastro(String cpf) {
        Exception exception = assertThrows(Exception.class, () -> {
            clientesServiceImpl.buscarClientePorCPF(cpf);
            clientesServiceImpl.cadastrarCliente(NOME_CLIENTE, cpf, ENDERECO_CLIENTE);
        });
        assertEquals("O CPF informado não foi encontrado. Tente novamente", exception.getMessage());
    }

    //TESTE ATUALIZAR
    @Test
    public void quandoClienteAtualizarVerifiqueSeOCpfJaFoiCadastradoEntaoAtualizeComSucesso() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        Cliente novoCliente = clientesServiceImpl.atualizarCliente("Joana", CPF_CLIENTE, "Rua teste da silva");

        assertEquals("Joana", novoCliente.getNomeCompleto());
        assertEquals(CPF_CLIENTE, novoCliente.getCpf());
        assertEquals("Rua teste da silva", novoCliente.getEndereco());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    //Simula a entrada vazia, esperando que o nome continue NOME_CLIENTE
    @CsvSource({" , Kevelly"})
    public void quandoClienteAtualizarVerfiqueSeNomeCompletoEhVazioEntaoRetorneSeuValorAnterior
            (String novoNome, String nomeEsperado) throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        //Simula o input vazio
        if (novoNome == null) {
            novoNome = cliente.getNomeCompleto();
        }

        clientesServiceImpl.atualizarCliente(novoNome, CPF_CLIENTE, ENDERECO_CLIENTE);

        //Valida se o nome continua o mesmo
        Cliente clienteAtualizado = clientesServiceImpl.buscarClientePorCPF(CPF_CLIENTE);
        assertEquals(nomeEsperado, clienteAtualizado.getNomeCompleto());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    @CsvSource({" , Rua dos testes 56"})
    public void quandoAtualizarClienteVerfiqueSeEnderecoEhVazioEntaoRetorneSeuValorAnterior
            (String novoEndereco, String enderecoEsperado) throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        if (novoEndereco == null) {
            novoEndereco = cliente.getEndereco();
        }

        clientesServiceImpl.atualizarCliente("Ana", CPF_CLIENTE, novoEndereco);

        Cliente clienteAtualizado = clientesServiceImpl.buscarClientePorCPF(CPF_CLIENTE);
        assertEquals(enderecoEsperado, clienteAtualizado.getEndereco());
    }

    //PESQUISAR CLIENTE
    @Test
    public void quandoClientePesquisarNomeEntaoListeTodosOsClientesComEsseNome() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        Cliente cliente2 = new Cliente("Joana Silva", "0000000", "rua Unitarios 123");
        Cliente cliente3 = new Cliente("Carol silveira", "9999999", "rua Unitarios 123");

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
    public void quandoClientePesquisarCpfEntaoExibaOClienteComEsseCpf() throws Exception {
        Cliente cliente = new Cliente(NOME_CLIENTE, CPF_CLIENTE, ENDERECO_CLIENTE);
        when(clienteRepository.buscarPorCPF(CPF_CLIENTE)).thenReturn(cliente);

        Cliente resultado = clientesServiceImpl.buscarClientePorCPF(CPF_CLIENTE);

        assertEquals(NOME_CLIENTE, resultado.getNomeCompleto());
        assertEquals(CPF_CLIENTE, resultado.getCpf());
        assertEquals(ENDERECO_CLIENTE, resultado.getEndereco());
    }
}
