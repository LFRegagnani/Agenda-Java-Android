package lfr.agenda;

import android.app.Application;

import lfr.agenda.dao.ClienteDAO;
import lfr.agenda.model.Cliente;

public class AgendaApplication extends Application {

    //Classe criada para Impedir o "aluno exemplo" de ser recriado toda vez que giramos o celular
    //Ela serve para mostrar que alguns processos do app precisam ser protegidos para não serem
    //inicializados mais de uma vez por acidente durante a transição entre os ciclos de vida.

    @Override
    public void onCreate() {
        super.onCreate();
        criaESalvaAlunoTeste();
    }

    private void criaESalvaAlunoTeste() {
        ClienteDAO dao = new ClienteDAO();
        dao.salva(new Cliente("Dunha", "928927299", "dunha@hotmail.com"));
    }
}
