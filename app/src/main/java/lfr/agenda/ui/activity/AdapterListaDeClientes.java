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

    private final List<Cliente> clientes = new ArrayList();
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
    public View getView(int posicaoNaArray, View view, ViewGroup viewGroup) {
        View viewCriada = criaViewParaOAdapter(viewGroup);
        VinculaCamposDeDadosDoAdapterAoCliente(posicaoNaArray, viewCriada);
        return viewCriada;
    }

    private void VinculaCamposDeDadosDoAdapterAoCliente(int posicaoNaArray, View viewCriada) {
        TextView nomeDoCliente = viewCriada.findViewById(R.id.item_nome_do_cliente);
        TextView telefoneDoCliente = viewCriada.findViewById(R.id.item_telefone_do_cliente);
        Cliente clienteDevolvido = clientes.get(posicaoNaArray);
        nomeDoCliente.setText(clienteDevolvido.getNome());
        telefoneDoCliente.setText(clienteDevolvido.getTelefone());
    }

    private View criaViewParaOAdapter(ViewGroup viewGroup) {
        return LayoutInflater.from(Context).
                inflate(R.layout.item_cliente, viewGroup, false);
    }


    public void atualiza(List<Cliente> clientes){
        this.clientes.clear();
        this.clientes.addAll(clientes);
        notifyDataSetChanged();

    }

    public void remove(Cliente clienteEscolhido) {
        clientes.remove(clienteEscolhido);
        notifyDataSetChanged();
    }
}
