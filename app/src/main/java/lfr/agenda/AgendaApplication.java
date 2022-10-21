package lfr.agenda;

import android.app.Application;

import lfr.agenda.DAO.ClienteDAO;
import lfr.agenda.Model.Cliente;

public class AgendaApplication extends Application {

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
