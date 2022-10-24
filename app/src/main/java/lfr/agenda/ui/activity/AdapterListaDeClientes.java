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

//O adapter serve para conectar uma lista (Dao.clientes) ao elemento grafico que vai ultiliza-lo
//dentro de uma ListView(listaDeClientes que é a referencia de lista_de_clientes_ordenada),
//que por sua vez está dentro de uma activity(activity_lista_de_clientes).

//O novo adapter precisa implementar BaseAdapter pra funcionar.
public class AdapterListaDeClientes extends BaseAdapter {

    //Atributos
    private final List<Cliente> clientes = new ArrayList();
    private final android.content.Context Context;

    //construtor
    public AdapterListaDeClientes(android.content.Context context) {
        Context = context;
    }

//Metodos sobreescritos do BaseAdapter que são essenciais para criar o Adapter personalizado.

    @Override
    public int getCount() {
        return clientes.size();
    }//mede a Lista que será colocada no ListView

    @Override
    public Cliente getItem(int posicao) {
        return clientes.get(posicao);
    }//Pega a posição do Cliente especifico

    @Override
    public long getItemId(int posicao) {
        return clientes.get(posicao).getId();
    }//pega o atributo "ID" de dentro do Cliente especifico

    @Override
    public View getView(int posicaoNaArray, View view, ViewGroup viewGroup) {//Implementa o adapter
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
        return LayoutInflater.from(Context).inflate(R.layout.item_cliente, viewGroup, false);
    }


    public void atualiza(List<Cliente> clientes) {//Faz a lista atualizar após uma ação do usuario
        this.clientes.clear();
        this.clientes.addAll(clientes);
        notifyDataSetChanged();

    }

    public void remove(Cliente clienteEscolhido) {//metodo que exclui o cliente da lista
        clientes.remove(clienteEscolhido);
        notifyDataSetChanged();
    }
}
