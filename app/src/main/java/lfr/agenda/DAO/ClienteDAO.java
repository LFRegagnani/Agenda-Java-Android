package lfr.agenda.DAO;

import static android.text.TextUtils.indexOf;

import java.util.ArrayList;
import java.util.List;

import lfr.agenda.Model.Cliente;

public class ClienteDAO {

    private final static List<Cliente> clientes = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salva(Cliente cliente) {
        cliente.setId(ClienteDAO.contadorDeIds);
        clientes.add(cliente);
        ClienteDAO.contadorDeIds++;

    }

    public List<Cliente> todos() {
        return new ArrayList<>(clientes);

    }

    public void edita(Cliente cliente) {
        Cliente clienteEncontrado = null;

        for (Cliente c : clientes) {
            if (c.getId() == cliente.getId()) {
                clienteEncontrado = c;
            }
        }
        if (clienteEncontrado != null) {
            int posiçãoDoCliente = clientes.indexOf(clienteEncontrado);
            clientes.set(posiçãoDoCliente, cliente);
        }
    }

    public void remove(Cliente cliente) {

        if(cliente != null) {
            clientes.remove(cliente);
        }
    }
}

