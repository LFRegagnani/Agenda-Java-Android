package lfr.agenda.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//recebe a interface Serializable para que o Adapter possa jogar esse
// objeto para outra Activity.

@SuppressWarnings("RedundantIfStatement")
@Entity
public class Cliente implements Serializable {
    //Atributos
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;

    //Construtores
    @Ignore
    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente() {

    }
//metodos

    @NonNull
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}
