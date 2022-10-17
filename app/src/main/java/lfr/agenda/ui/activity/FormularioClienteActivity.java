package lfr.agenda.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import lfr.agenda.DAO.ClienteDAO;
import lfr.agenda.Model.Cliente;
import lfr.agenda.R;

public class FormularioClienteActivity extends AppCompatActivity implements ConstantesActivityes {

    private static final String TITLE_APPBAR_NOVO_CLIENTE = "Novo Cliente";
    private static final String TITLE_APPBAR_EDITA_CLIENTE = "Edita Cliente";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private Cliente cliente;
    final ClienteDAO dao = new ClienteDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cliente);


        inicializaCamposPreenchiveis();
        inicializaBotaoSalvar();
        carregaCliente();
    }

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

    private void preencheCamposDoClienteExistente() {
        campoNome.setText(cliente.getNome());
        campoTelefone.setText(cliente.getTelefone());
        campoEmail.setText(cliente.getEmail());
    }

    private void inicializaBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener((View) -> {

            finalizaFormulario();
        });
    }

    private void finalizaFormulario() {
        preencheCliente();

        if (cliente.temIdValido()) {
            dao.edita(cliente);
        } else {
            dao.salva(cliente);
        }
        finish();
    }


    private void inicializaCamposPreenchiveis() {
        campoNome = findViewById(R.id.campoNome);
        campoTelefone = findViewById(R.id.campoTelefone);
        campoEmail = findViewById(R.id.campoEmail);
    }


    private void preencheCliente() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
    }
}