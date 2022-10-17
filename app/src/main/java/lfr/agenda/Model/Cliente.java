package lfr.agenda.Model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int id = 0;
    private String nome;
    private String telefone;
    private String email;

    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente() {

    }

    @Override
    public String toString() {
        return this.nome + "      Tel:  " + this.telefone;
    }

    //GETTs E SETTs
    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public void setTelefone(String t) {
        this.telefone = t;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setId(int contadorDeIds) {
        this.id = contadorDeIds;
    }

    public int getId() {
        return this.id;
    }

    public boolean temIdValido() {
        if (this.id == 0) {
            return false;
        } else {
            return true;
        }
    }
}
