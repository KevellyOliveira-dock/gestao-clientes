package org.example.validator;

import org.example.exception.ClienteDesativdadoException;
import org.example.model.Cliente;

public class ClienteValidator {

    public static void validarAtivo(Cliente cliente) {
        if (!cliente.isAtivo()) {
            throw new ClienteDesativdadoException("Esse cliente está desativado. Suas permissões foram revogadas.\n");
        }
    }
}
