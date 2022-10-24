package lfr.agenda.Model;

import java.io.Serializable;

//recebe a interface Serializable para que o Adapter possa jogar esse
// objeto para outra Activity.

public class Cliente implements Serializable {
    //Atributos
    private int id = 0;
    private String nome;
    private String telefone;
    private String email;

    //Construtores
    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente() {

    }
//metodos

    @Override
    public String toString() {//Sobrescreve o metodo padrão para aprimorar a referencia da classe
        return this.nome + "      Tel:  " + this.telefone;
    }

    public boolean temIdValido() {//verifica se a instancia Cliente acessada já está cadastrada
        if (this.id == 0) {
            return false;
        } else {
            return true;
        }
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


}
