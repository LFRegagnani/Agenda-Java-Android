package lfr.agenda.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
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
    private AdapterListaDeClientes adapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE_APPBAR);
        setContentView(R.layout.activity_lista_de_clientes);

        inicializaFabNovoCliente();
        inicializaListaDeClientes();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_clientes_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int idDoMenu = item.getItemId();
        if (idDoMenu == R.id.menu_remover) {
            confirmarRemoçãoOuNãoDoCliente(item);
        }
        return super.onContextItemSelected(item);
    }

    private void confirmarRemoçãoOuNãoDoCliente(@NonNull MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Remover cliente")
                .setMessage("Tem CERTEZA que quer remover o cliente?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdapterView.AdapterContextMenuInfo menuInfo =
                                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Cliente clienteEscolhido = adapter.getItem(menuInfo.position);
                        removeClienteDaLista(clienteEscolhido);
                    }
                }).
                setNegativeButton("Não", null)
                .show();
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

        adapter.atualiza(dao.todos());

    }

    private void inicializaListaDeClientes() {
        ListView listaDeClientes = findViewById(R.id.activity_main_lista_de_clientes);
        configuraAdapter(listaDeClientes);
        configuraCliqueNosItensDaListaDeNomes(listaDeClientes);
        registerForContextMenu(listaDeClientes);

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
        adapter = new AdapterListaDeClientes(this);
        listaDeClientes.setAdapter(adapter);
    }

}
