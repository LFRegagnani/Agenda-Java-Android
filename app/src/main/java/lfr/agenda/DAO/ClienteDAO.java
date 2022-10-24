package lfr.agenda.DAO;

import java.util.ArrayList;
import java.util.List;

import lfr.agenda.Model.Cliente;

public class ClienteDAO {
    //Atributos
    private final static List<Cliente> clientes = new ArrayList<>();
    private static int contadorDeIds = 1;

    //Construtor
    public void salva(Cliente cliente) {
        cliente.setId(ClienteDAO.contadorDeIds);
        clientes.add(cliente);
        ClienteDAO.contadorDeIds++;

    }

    //Metodos
    public List<Cliente> todos() { //retorna a lista de clientes mas impossibilita a modificação.
        return new ArrayList<>(clientes);

    }

    public void edita(Cliente cliente) {//substitui cliente clicado pelo novo que foi editado
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

    public void remove(Cliente cliente) {//remove cliente

        if (cliente != null) {
            clientes.remove(cliente);
        }
    }
}

