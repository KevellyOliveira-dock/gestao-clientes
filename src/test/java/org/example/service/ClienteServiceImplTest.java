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
    public void quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso() {
        //cria um usuario para teste
        var resultado = clienteServiceImpl.cadastrarCliente(
                "Kevelly",
                "5689778",
                "Rua teste");

        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("5689778");

        var resultadoEsperado = "Cliente cadastrado com sucesso";

        assertEquals(resultadoEsperado, resultado);
        assertNotNull(cliente);
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("5689778", cliente.getCpf());
        assertEquals("Rua teste", cliente.getEndereco());
    }

    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoBuscarClientePorCpfForVazioOuNuloEntaoNaoRealizarCadastro(String cpf) {
        var resultado = clienteServiceImpl.cadastrarCliente("Kevelly", cpf, "Rua teste");
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF(cpf);

        assertEquals("CPF não pode ser nulo ou vazio", resultado);
        assertNull(cliente);
    }

    @Test
    public void quandoBuscarClientePorCpfForVaziaEntaoNaoCadastra() {
        quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();

        var resultado = clienteServiceImpl.cadastrarCliente(
                "Joao",
                "5689778",
                "Rua dos testes 123");

        var resultadoEsperado = "CPF já cadastrado";
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void quandoBuscarClientePorCpfEntaoVerifiqueSeCadastrouChaveAntes() {
        clienteServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");

        var resultado = clienteServiceImpl.cadastrarCliente(
                "Kevelly",
                "5689778",
                "Rua teste");

        assertEquals("CPF já cadastrado", resultado);
    }

    @Test
    public void quandoBuscarClientePorCpfECpfNaoExistirEntaoRetorneNulo() {
        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("");
        assertNull(cliente);
    }

    //TESTE ATUALIZAR
    @Test
    public void quandoAtualizarClienteVerifiqueSeOCpfJaFoiAtualizadoEntaoAtualizeComSucesso() {
        quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();

        var resultado = clienteServiceImpl.atualizarCliente(
                "Joana",
                "5689778",
                "Rua teste da silva");

        var resultadoEsperado = "Cliente atualizado com sucesso";
        assertEquals(resultadoEsperado, resultado);

        Cliente cliente = clienteServiceImpl.buscarClientePorCPF("5689778");
        assertNotNull(cliente);
        assertEquals("Joana", cliente.getNomeCompleto());
        assertEquals("5689778", cliente.getCpf());
        assertEquals("Rua teste da silva", cliente.getEndereco());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    //Simula a entrada vazia, esperando que o nome continue "Kevelly"
    @CsvSource({" , Kevelly"})
    public void quandoAtualizarClienteVerfiqueSeNomeCompletoEhVazioEntaoRetorneSeuValorAnterior(
            String novoNome,
            String nomeEsperado
    ) {
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
    public void quandoAtualizarClienteVerfiqueSeEnderecoEhVazioEntaoRetorneSeuValorAnterior(
            String novoEndereco,
            String enderecoEsperado
    ) {
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
    public void quandoClientePesquisarNomeEntaoListeTodosOsClientesComEsseNome() {
        quandoCadastrarClienteVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();
//        clienteServiceImpl.cadastrarCliente("KeveLly", "0000000", "rua Unitarios 123");

        //Retornar um lista
        List<Cliente> resultado = clienteServiceImpl.pesquisarClientePorNome("Kevelly");

        //Retorna o número de elementos dentro da lista
        assertEquals(2, resultado.size());

        //get(index) -> retorna um elemento especifico da lista pelo indice

        //Verifica se a condição é verdadeira
        assertTrue(resultado.stream() // Stream transforma a lista em um fluxo de dados
                .anyMatch( //Verifica se algum elemento da lista atende a condição
                        //para cada cliente(c) na lista, compara o nome do cliente
                        // e ignora as diferenças entre maiusculas e minusculas
                c -> c.getNomeCompleto().equalsIgnoreCase("Kevelly"))
        );
    }
}
