package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontControllerTest {
    private FrontController controller;

    @BeforeEach
    public void setup() {
        controller = new FrontController();
    }

    @Test
    public void quandoComandoEhCartoesEntaoExibaOpcoesDeCartoes() {
        var resultadoEsperado = """
                -------------------------------
                | Bloquear {número do cartao} |
                | Cadastrar                   |
                -------------------------------""";
        var resultadoReal = controller.executar("cartoes");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesBloquearEntaoBloqueieOsCartoes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("cartoes bloquear");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhCartoesCadastrarEntaoCadastreOsCartoes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("cartoes cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesEntaoExibaOpcoesDeClientes() {
        var resultadoEsperado = """
                ------------------------------
                | atualizar {cpf do cliente} |
                | cadastrar                  |
                | desativar {cpf do cliente} |
                | pesquisar                  |
                ------------------------------""";
        var resultadoReal = controller.executar("clientes");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesAtualizarEntaoAtualizeOsClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes atualizar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesCadastrarEntaoCadastreOsClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesDesativarEntaoDesativeOsClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes desativar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarEntaoExibaOpcoesDeClientesPesquisar() {
        var resultadoEsperado = """
                --------------------------
                | cpf {cpf do cliente}   |
                | nome {nome do cliente} |
                -------------------------""";
        var resultadoReal = controller.executar("clientes pesquisar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarCpfEntaoExibaDetalhesDosClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes pesquisar cpf");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhClientesPesquisarNomeEntaoExibaDetalhesDosClientes() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("clientes pesquisar nome");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasEntaoExibaOpcoesDeContas() {
        var resultadoEsperado = """
                -------------------------------
                | cadastrar                   |
                | desativar {numero da conta} |
                | extrato {numero da conta}   |
                | pesquisar                   |
                -------------------------------""";
        var resultadoReal = controller.executar("contas");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasCadastrarEntaoCadastreAsContas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas cadastrar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasDesativarEntaoDesativeAsContas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas desativar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasExtratoEntaoExibaOExtradoDasContas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas extrato");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarEntaoExibaOpcoesDeContasPesquisar() {
        var resultadoEsperado = """
                ----------------------------------
                | cpf-titular {cpf do cliente}   |
                | nome-titular {nome do cliente} |
                | numero {número da conta}       |
                ----------------------------------""";
        var resultadoReal = controller.executar("contas pesquisar");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarCpfTitularEntaoExibaAsContasEncontradas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar cpf-titular");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNomeTitularEntaoExibaAsContasEncontradas() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar nome-titular");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhContasPesquisarNumeroEntaoExibaDetalhesDaConta() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("contas pesquisar numero");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhFaturasEntaoExibaOpcoesDeFaturas() {
        var resultadoEsperado = """
                -----------------------------
                | fechar {número do cartão} |
                -----------------------------""";
        var resultadoReal = controller.executar("faturas");
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void quandoComandoEhFaturasFecharEntaoFecheAFatura() {
        var resultadoEsperado = "não implementado";
        var resultadoReal = controller.executar("faturas fechar");
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
