package org.example.service;

import org.example.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceImplTest {
    private ClienteServiceImpl clienteServiceImpl;

    @BeforeEach
    public void setup() {
        clienteServiceImpl = new ClienteServiceImpl();
    }

    @Test
    public void quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso() throws Exception {
        //cria um usuario para teste
        var resultado = clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");

        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("5689778");

        assertEquals(cliente, resultado);
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("5689778", cliente.getCpf());
        assertEquals("Rua teste", cliente.getEndereco());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoBuscarClientePorCpfForVazioOuNuloEntaoNaoRealizarCadastro(String cpf) throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            clienteServiceImpl.buscarClientePorCPF(cpf);

            clienteServiceImpl.cadastrarCliente(
                    "Kevelly",
                    cpf,
                    "Rua teste");
        });
        assertEquals("CPF não pode ser nulo ou vazio", exception.getMessage());
    }

    // O que diferencia do teste a cima é o @ParameterizedTest, mantido aqui para fins educativos
    @Test
    public void quandoBuscarClientePorCpfForVaziaEntaoNaoCadastra() throws Exception {
        quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();

        Exception exception = assertThrows(Exception.class, () ->
                clienteServiceImpl.cadastrarCliente(
                        "Kevelly",
                        "",
                        "Rua teste"));

        assertEquals("CPF não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    public void quandoBuscarClientePorCpfEntaoVerifiqueSeCadastrouChaveAntes() throws Exception {
        clienteServiceImpl.cadastrarCliente(
                "Joao",
                "5689778",
                "Rua teste");

        // () -> : lambda expression
        Exception exception = assertThrows(Exception.class, () ->
                clienteServiceImpl.cadastrarCliente(
                        "Kevelly",
                        "5689778",
                        "Rua teste"));

        // assertTrue(exception.getMessage().contains("CPF já cadastrado"));
        assertEquals("CPF já cadastrado", exception.getMessage());
    }

    //TESTE ATUALIZAR
    @Test
    public void quandoAtualizarClienteVerifiqueSeOCpfJaFoiAtualizadoEntaoAtualizeComSucesso() throws Exception {
        quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();

        var resultado = clienteServiceImpl.atualizarCliente("Joana", "5689778", "Rua teste da silva");

        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("5689778");

        assertEquals(cliente, resultado);
        assertNotNull(cliente);
        assertEquals("Joana", cliente.getNomeCompleto());
        assertEquals("5689778", cliente.getCpf());
        assertEquals("Rua teste da silva", cliente.getEndereco());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    //Simula a entrada vazia, esperando que o nome continue "Kevelly"
    @CsvSource({" , Kevelly"})
    public void quandoAtualizarClienteVerfiqueSeNomeCompletoEhVazioEntaoRetorneSeuValorAnterior
            (String novoNome, String nomeEsperado) throws Exception
    {
        clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("5689778");

        //Simula o input vazio
        if (novoNome == null) {
            novoNome = cliente.getNomeCompleto();
        }

        clienteServiceImpl.atualizarCliente(novoNome, "5689778", "Rua teste");

        //Valida se o nome continua o mesmo
        Cliente clienteAtualizado = clienteServiceImpl.buscarClientePorCPF("5689778");
        assertEquals(nomeEsperado, clienteAtualizado.getNomeCompleto());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    @CsvSource({" , rua Unitarios 123"})
    public void quandoAtualizarClienteVerfiqueSeEnderecoEhVazioEntaoRetorneSeuValorAnterior
            (String novoEndereco, String enderecoEsperado) throws Exception
    {
        clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "rua Unitarios 123");
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("5689778");

        if (novoEndereco == null) {
            novoEndereco = cliente.getEndereco();
        }

        clienteServiceImpl.atualizarCliente("Ana", "5689778", novoEndereco);

        Cliente clienteAtualizado = clienteServiceImpl.buscarClientePorCPF("5689778");
        assertEquals(enderecoEsperado, clienteAtualizado.getEndereco());
    }

    //PESQUISAR CLIENTE
    @Test
    public void quandoClientePesquisarNomeEntaoListeTodosOsClientesComEsseNome() throws Exception {
        clienteServiceImpl.cadastrarCliente("Kevelly", "1111111", "rua Unitarios 123");
        clienteServiceImpl.cadastrarCliente("Joana Silva", "0000000", "rua Unitarios 123");
        clienteServiceImpl.cadastrarCliente("Carol silveira", "9999999", "rua Unitarios 123");

        var busca = "sil";

        List<Cliente> resultado = clienteServiceImpl.pesquisarClientePorNome(busca);

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
        quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();

        Cliente resultado = clienteServiceImpl.buscarClientePorCPF("5689778");

        assertEquals("Kevelly", resultado.getNomeCompleto());
        assertEquals("5689778", resultado.getCpf());
        assertEquals("Rua teste", resultado.getEndereco());
    }

    @Test
    public void quandoBuscarClientePorCpfECpfNaoExistirEntaoRetorneNulo() {
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("");
        assertNull(cliente);
    }
}
