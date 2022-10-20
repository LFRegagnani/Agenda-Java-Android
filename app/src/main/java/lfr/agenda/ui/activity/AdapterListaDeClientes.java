package lfr.agenda.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lfr.agenda.Model.Cliente;
import lfr.agenda.R;

public class AdapterListaDeClientes extends BaseAdapter {

    private List<Cliente> clientes = new ArrayList();
    private final android.content.Context Context;

    public AdapterListaDeClientes(android.content.Context context) {
        Context = context;
    }


    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Cliente getItem(int posicao) {
        return clientes.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return clientes.get(posicao).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewCriada = LayoutInflater.from(Context).
                inflate(R.layout.item_cliente, viewGroup,false);
        TextView nomeDoCliente = viewCriada.findViewById(R.id.item_nome_do_cliente);
        TextView telefoneDoCliente = viewCriada.findViewById(R.id.item_telefone_do_cliente);
        Cliente clienteDevolvido = clientes.get(i);
        nomeDoCliente.setText(clienteDevolvido.getNome());
        telefoneDoCliente.setText(clienteDevolvido.getTelefone());
        return viewCriada;
    }

    public void clear() {
        clientes.clear();
    }

    public void addAll(List<Cliente> todos) {
        clientes.addAll(todos);
    }

    public void remove(Cliente clienteEscolhido) {
        clientes.remove(clienteEscolhido);
    }
}
