package lfr.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lfr.agenda.model.Cliente;

@Dao
public interface RoomClienteDAO {
    @Insert
    void salva(Cliente cliente);

    @Query("SELECT * FROM cliente")
    List<Cliente> todos();

    @Delete
    void remove(Cliente clienteEscolhido);

    @Update
    void edita(Cliente cliente);
}
