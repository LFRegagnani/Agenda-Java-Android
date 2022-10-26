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

import lfr.agenda.dao.ClienteDAO;
import lfr.agenda.model.Cliente;
import lfr.agenda.R;

@SuppressWarnings("Convert2Lambda")
public class ListaDeClientesActivity extends AppCompatActivity implements ConstantesActivityes {
    //Atributos
    public static final String TITLE_APPBAR = "Agenda da Kátia";
    private final ClienteDAO dao = new ClienteDAO();
    private AdapterListaDeClientes adapter;

    //Metodo de ciclo de vida:  metodo que inicializa a primeira tela do aplicativo
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE_APPBAR);//Seta o titulo do app usando uma variavel que é um atributo.
        setContentView(R.layout.activity_lista_de_clientes);//vincula esta activity ao XML dela

        inicializaFabNovoCliente();//Seta e configura o botão bolinha de adicionar Novo Cliente

        inicializaListaDeClientes();
        //metodo mais importante do app:Inicializa e Configura
        //tudo que está relacionado ao Adapter criado, à lista de cliente cadastrados,
        //configura a inicialização de outra Activity pelo Intent e também a serialização
        //de clientes já existentes a partir do clique de um Item da Lista.
        //Tambem configura as condições para a exclusão de um cliente ao fazer longClick em
        //um item da lista

    }

    //metodo que cria e vincula um um contextMenu em XML que será usado no metodo a baixo.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_clientes_menu, menu);
    }

    //vincula o Item de menu "Remover" criado no XML à cima, ao long click em um item
    // da lista de Clientes.Verifica se foi clicado, se for true, inicia o metodo de confirmação.
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int idDoMenu = item.getItemId();
        if (idDoMenu == R.id.menu_remover) {
            confirmarRemocaoOuNaoDoCliente(item);
        }
        return super.onContextItemSelected(item);
    }

    //Metodo de confirmação de exclusão do cliente após selecionar o menu "Remover".
    //Aqui um Dialog é criado e se for confirmada a exclusão com clique na opção "sim"
    //O cliente clicado é enviado ao adapter e ao DAO e ambos farão a exclusão do cliente.
    private void confirmarRemocaoOuNaoDoCliente(@NonNull MenuItem item) {
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

    //Metodo que configura o clique no botão redondo, atribuindo sua View no XML a uma variavel.
    private void inicializaFabNovoCliente() {
        FloatingActionButton botaoFloat = findViewById(R.id.activity_main_fab_novo_aluno);
        botaoFloat.setOnClickListener((View) -> abreFormularioInsereNovoCliente());
    }

    //metodo chamado após clicar no botão à cima. Ele usa um Intent para passar dessa Activity para
    //a tela do formulário de cadastrar um novo cliente.
    private void abreFormularioInsereNovoCliente() {
        startActivity(new Intent(this, FormularioClienteActivity.class));
    }

    //Metodo de ciclo de vida: Onde o app se estabiliza após a criação.
    @Override
    protected void onResume() {
        super.onResume();
        atualizaListaDeClientes();

    }

    //Usado para atualizar o adapter, ou seja, o visual da lista
    // após qualquer mudança no banco de dados principal que é o ClienteDAO.
    private void atualizaListaDeClientes() {

        adapter.atualiza(dao.todos());
    }

    //Metodo que pega a ListView que está no XML desta activity
    // e guarda na variavel listaDeClientes que é usada na configuração do adapter
    //Depois chama o adapter configurado e atribui ele coloca na ListView que exibe os clientes.
    //Este metodo também configura os dois tipos de clique nos itens da lista:
    // Clique normal: pega a posição do item clicado na lista e passa para para um Intent
    // Serializar os dados e abrir a pagina de edição que está na outra Activity.
    //Clique Longo:registra o menu "Remover" dos metodos anteriores
    private void inicializaListaDeClientes() {
        ListView listaDeClientes = findViewById(R.id.lista_de_clientes_ordenada);
        configuraAdapter(listaDeClientes);
        configuraCliqueNosItensDaListaDeNomes(listaDeClientes);
        registerForContextMenu(listaDeClientes);

    }

    //Remove um objeto cliente tanto do bando de dados DAO quanto da lista visual do adapter.
    //internamente chama o metodo de atualizar a lista visual para impedir erros.
    private void removeClienteDaLista(Cliente clienteEscolhido) {
        dao.remove(clienteEscolhido);
        adapter.remove(clienteEscolhido);

    }

    //Salva a posição na lista do cliente clicado em uma variavel e
    // à envia para o processo de serialização que possibilita a edição de um cliente já cadastrado.
    private void configuraCliqueNosItensDaListaDeNomes(ListView listaDeClientes) {
        listaDeClientes.setOnItemClickListener((adapterView, view, posicaoNaArray, id) -> {
            Cliente clienteEscolhido = (Cliente) adapterView.getItemAtPosition(posicaoNaArray);
            abreFormularioDeEditarCliente(clienteEscolhido);
        });
    }
    //pega o cliente clicado, serializa seus dados e o envia para a Activity responsável por receber.
    //Também inicia a activity de formulário, trocando de tela (essa já abre com
    //os dados do cliente clicado preenchidos na tela, graças ao codigo que está na Activity receptora.

    private void abreFormularioDeEditarCliente(Cliente clienteEscolhido) {
        Intent vaiParaFormulario = new Intent(ListaDeClientesActivity.this, FormularioClienteActivity.class);
        vaiParaFormulario.putExtra(CHAVE_CLIENTE, clienteEscolhido);
        startActivity(vaiParaFormulario);
    }

    //Recebe a referencia da ListView em que a lista de clientes vai ser exibida e configura o adapter
    private void configuraAdapter(ListView listaDeClientes) {
        adapter = new AdapterListaDeClientes(this);
        listaDeClientes.setAdapter(adapter);
    }
}
