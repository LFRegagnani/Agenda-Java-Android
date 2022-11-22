package lfr.agenda.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import lfr.agenda.R;
import lfr.agenda.database.AgendaDatabase;
import lfr.agenda.database.dao.RoomClienteDAO;
import lfr.agenda.model.Cliente;

public class FormularioClienteActivity extends AppCompatActivity implements ConstantesActivityes {
//Atributos

    private static final String TITLE_APPBAR_NOVO_CLIENTE = "Novo Cliente";
    private static final String TITLE_APPBAR_EDITA_CLIENTE = "Edita Cliente";
    private EditText campoNome;
    private EditText campoSobrenome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private Cliente cliente;
    private RoomClienteDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//aqui a tela é iniciada
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cliente);//vincula esta activity ao XML dela
        AgendaDatabase database = AgendaDatabase.getInstance(this);
        this.dao = database.getRoomClienteDAO();

        inicializaCamposPreenchiveis();
        //vincula os campos preenchiveis feitos no XML aos atributos e à variaveis
        // que serão ultilizadas para criar um novo Cliente ou carregar os dados de um.

        carregaCliente();
        //verifica se existe dados de um cliente serializados préexistentes a
        // serem inicializados, enviados pela activity anterior. caso sim, ele usa a chave
        // de ConstantesActivityes para des-Serelializar esses dados e preenche os campos.
        //caso não, ele mostra os campos vazios para a criação de um novo Cliente.

    }

    //vincula o XML do botão de salvar a essa Activity e o inicializa.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_cliente_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //estabelece o funcionamento do botão salvar inicializado logo a cima.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.botaoSalvarCanto) {
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    //metodo ultilizado no Oncreate para verificar se o cliente é novo ou se será editado.
    private void carregaCliente() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_CLIENTE)) {
            setTitle(TITLE_APPBAR_EDITA_CLIENTE);
            cliente = (Cliente) dados.getSerializableExtra(CHAVE_CLIENTE);
            preencheCamposDoClienteExistente();
        } else {
            setTitle(TITLE_APPBAR_NOVO_CLIENTE);
            cliente = new Cliente();

        }
    }

    ////metodo ultilizado indiretamente no Oncreate, responsável por preencher os dados serializados.
    private void preencheCamposDoClienteExistente() {
        campoNome.setText(cliente.getNome());
        campoSobrenome.setText(cliente.getSobrenome());
        campoTelefone.setText(cliente.getTelefone());
        campoEmail.setText(cliente.getEmail());
    }

    //Salva o cliente novo ou Substitui um cliente editado pelo usuario
// A ação a ser executada vai depender do ID do Cliente que esse objeto for aplicado
// Se o ID ainda for 0 signiffica que o cliente é novo e ele será salvo e um numero de ID será gerado
// Se o ID for > que 0 significa que é cliente editado e o metodo edita() vai salvar a edição em cima
    private void finalizaFormulario() {
        preencheCliente();

        if (cliente.temIdValido()) {
            dao.edita(cliente);
        } else {
            dao.salva(cliente);
        }
        finish();
    }

    //pega a View do campo preenchivel do XML e vincula a um atributo
    private void inicializaCamposPreenchiveis() {
        campoNome = findViewById(R.id.campoNome);
        campoSobrenome = findViewById(R.id.campoSobrenome);
        campoTelefone = findViewById(R.id.campoTelefone);
        campoEmail = findViewById(R.id.campoEmail);

    }

    //Só é executado após o cliente clicar no botão Salvar. Pega tudo que está digitado nos
    //campos preenchiveis do metodo de cima, e seta como atributos de inicialização de um Cliente.
    private void preencheCliente() {
        String nome = campoNome.getText().toString();
        String sobrenome = campoSobrenome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();
        cliente.setNome(nome);
        cliente.setSobrenome(sobrenome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
    }
}