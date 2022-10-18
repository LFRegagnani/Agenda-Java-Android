package lfr.agenda.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import lfr.agenda.DAO.ClienteDAO;
import lfr.agenda.Model.Cliente;
import lfr.agenda.R;

public class ListaDeClientesActivity extends AppCompatActivity implements ConstantesActivityes {

    public static final String TITLE_APPBAR = "Agenda da Kátia";

    private final ClienteDAO dao = new ClienteDAO();
    private ArrayAdapter<Cliente> adapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE_APPBAR);
        setContentView(R.layout.activity_lista_de_clientes);

        inicializaFabNovoCliente();
        inicializaListaDeClientes();
        dao.salva(new Cliente("Luiz Fernando", "92892799", "Luiz_bim@hotmail.com"));
        dao.salva(new Cliente("dinha", "928434392799", "dunha@gmail"));


    }

    private void inicializaFabNovoCliente() {
        FloatingActionButton botaoFloat = findViewById(R.id.activity_main_fab_novo_aluno);
        botaoFloat.setOnClickListener((View) -> abreFormularioInsereNovoCliente());
    }

    private void abreFormularioInsereNovoCliente() {
        startActivity(new Intent(this, FormularioClienteActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaListaDeClientes();


    }

    private void atualizaListaDeClientes() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void inicializaListaDeClientes() {
        ListView listaDeClientes = findViewById(R.id.activity_main_lista_de_clientes);
        configuraAdapter(listaDeClientes);
        configuraCliqueNosItensDaListaDeNomes(listaDeClientes);
        configuraCliqueLongoNoItem(listaDeClientes);

    }

    private void configuraCliqueLongoNoItem(ListView listaDeClientes) {
        listaDeClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicaoNaArray, long id) {
                Cliente clienteEscolhido = (Cliente) adapterView.getItemAtPosition(posicaoNaArray);
                removeClienteDaLista(clienteEscolhido);
                return true;
            }
        });
    }

    private void removeClienteDaLista(Cliente clienteEscolhido) {
        dao.remove(clienteEscolhido);
        adapter.remove(clienteEscolhido);
    }

    private void configuraCliqueNosItensDaListaDeNomes(ListView listaDeClientes) {
        listaDeClientes.setOnItemClickListener((adapterView, view, posicaoNaArray, id) -> {
            Cliente clienteEscolhido = (Cliente) adapterView.getItemAtPosition(posicaoNaArray);
            abreFormularioDeEditarCliente(clienteEscolhido);
        });
    }

    private void abreFormularioDeEditarCliente(Cliente clienteEscolhido) {
        Intent vaiParaFormulario = new Intent(ListaDeClientesActivity.this, FormularioClienteActivity.class);
        vaiParaFormulario.putExtra(CHAVE_CLIENTE, clienteEscolhido);
        startActivity(vaiParaFormulario);
    }

    private void configuraAdapter(ListView listaDeClientes) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDeClientes.setAdapter(adapter);
    }

}
