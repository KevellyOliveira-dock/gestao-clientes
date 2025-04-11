package org.example.service;

import org.example.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientesServiceImplTest {
    private ClientesServiceImpl clientesServiceImpl;

    @BeforeEach
    public void setup() {
        clientesServiceImpl = new ClientesServiceImpl();
    }

    @Test
    public void quandoClientesCadastrarVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso() throws Exception {
        var resultado = clientesServiceImpl.cadastrarCliente(
                "Kevelly",
                "5689778",
                "Rua teste");

        Cliente cliente = clientesServiceImpl.buscarClientePorCPF("5689778");

        assertEquals(cliente, resultado);
        assertEquals("Kevelly", cliente.getNomeCompleto());
        assertEquals("5689778", cliente.getCpf());
        assertEquals("Rua teste", cliente.getEndereco());
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
    void quandoClientesCadastrarENomeForNuloOuVazioEntaoRetorneMensagemDeErro(
            String nomeCompleto, String cpf, String endereco) throws Exception {
        Exception exception = assertThrows(Exception.class, () ->
                clientesServiceImpl.cadastrarCliente(nomeCompleto, cpf, endereco));

        assertEquals("Preencha todos os campos", exception.getMessage());
    }

    @Test
    public void quandoClientesCadastrarEntaoVerifiqueSeCadastrouChaveAntes() throws Exception {
        clientesServiceImpl.cadastrarCliente(
                "Joao",
                "5689778",
                "Rua teste");

        // () -> : lambda expression
        Exception exception = assertThrows(Exception.class, () ->
                clientesServiceImpl.cadastrarCliente(
                        "Kevelly",
                        "5689778",
                        "Rua teste"));

        assertEquals("CPF já cadastrado", exception.getMessage());
    }

    // Verificação do pesquisar CPF
    @ParameterizedTest
    @NullAndEmptySource //quando a função for executada passa null e depois vazia
    public void quandoBuscarClientePorCpfForVazioOuNuloEntaoNaoRealizarCadastro(String cpf) throws Exception {

        Exception exception = assertThrows(Exception.class, () -> {
            clientesServiceImpl.buscarClientePorCPF(cpf);

            clientesServiceImpl.cadastrarCliente(
                    "Kevelly",
                    cpf,
                    "Rua teste");
        });
        assertEquals("O CPF informado não foi encontrado. Tente novamente", exception.getMessage());
    }


    //TESTE ATUALIZAR
    @Test
    public void quandoClienteAtualizarVerifiqueSeOCpfJaFoiCadastradoEntaoAtualizeComSucesso() throws Exception {
        quandoClientesCadastrarVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();

        var resultado = clientesServiceImpl.atualizarCliente(
                "Joana",
                "5689778",
                "Rua teste da silva");

        Cliente cliente = clientesServiceImpl.buscarClientePorCPF("5689778");

        assertEquals(cliente, resultado);
        assertEquals("Joana", cliente.getNomeCompleto());
        assertEquals("5689778", cliente.getCpf());
        assertEquals("Rua teste da silva", cliente.getEndereco());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    //Simula a entrada vazia, esperando que o nome continue "Kevelly"
    @CsvSource({" , Kevelly"})
    public void quandoClienteAtualizarVerfiqueSeNomeCompletoEhVazioEntaoRetorneSeuValorAnterior
            (String novoNome, String nomeEsperado) throws Exception {
        clientesServiceImpl.cadastrarCliente("Kevelly", "5689778", "Rua teste");
        Cliente cliente = clientesServiceImpl.buscarClientePorCPF("5689778");

        //Simula o input vazio
        if (novoNome == null) {
            novoNome = cliente.getNomeCompleto();
        }

        clientesServiceImpl.atualizarCliente(novoNome, "5689778", "Rua teste");

        //Valida se o nome continua o mesmo
        Cliente clienteAtualizado = clientesServiceImpl.buscarClientePorCPF("5689778");
        assertEquals(nomeEsperado, clienteAtualizado.getNomeCompleto());
    }

    @ParameterizedTest
    //permite executar o mesmo teste várias vezes com valores diferentes
    @CsvSource({" , rua Unitarios 123"})
    public void quandoAtualizarClienteVerfiqueSeEnderecoEhVazioEntaoRetorneSeuValorAnterior
            (String novoEndereco, String enderecoEsperado) throws Exception {
        clientesServiceImpl.cadastrarCliente("Kevelly", "5689778", "rua Unitarios 123");
        Cliente cliente = clientesServiceImpl.buscarClientePorCPF("5689778");

        if (novoEndereco == null) {
            novoEndereco = cliente.getEndereco();
        }

        clientesServiceImpl.atualizarCliente("Ana", "5689778", novoEndereco);

        Cliente clienteAtualizado = clientesServiceImpl.buscarClientePorCPF("5689778");
        assertEquals(enderecoEsperado, clienteAtualizado.getEndereco());
    }

    //PESQUISAR CLIENTE
    @Test
    public void quandoClientePesquisarNomeEntaoListeTodosOsClientesComEsseNome() throws Exception {
        clientesServiceImpl.cadastrarCliente("Kevelly", "1111111", "rua Unitarios 123");
        clientesServiceImpl.cadastrarCliente("Joana Silva", "0000000", "rua Unitarios 123");
        clientesServiceImpl.cadastrarCliente("Carol silveira", "9999999", "rua Unitarios 123");

        var busca = "sil";

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
        quandoClientesCadastrarVerifiqueSeOCpfJaFoiCadastradoEntaoCadastreComSucesso();

        Cliente resultado = clientesServiceImpl.buscarClientePorCPF("5689778");

        assertEquals("Kevelly", resultado.getNomeCompleto());
        assertEquals("5689778", resultado.getCpf());
        assertEquals("Rua teste", resultado.getEndereco());
    }
}
