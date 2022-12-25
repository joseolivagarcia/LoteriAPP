package joseoliva.com.loteriapp.activities.bbdd

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import joseoliva.com.loteriapp.activities.models.DecimoJugado

/*
 * Esta interface la usamos para crear aqui todas las funciones que vayamos a necesitar
 * de la bbdd, es decir, las consultas, las ordenes para borrar o actualizar, la orden
 * para insertar, etc...
 */
@Dao
interface DecimoDao {
    //crearemos las funciones como suspend(menos la que tiene un Livedata) para que no se ejecuten en el hilo ppal de la app
    //la funcion que obtiene todos los decimos sera LiveData porque necesitamos que "escuche" para poder
    //actualizar los cambios que se produzcan en tiempo real
    @Query("Select * From decimosjugados")
    fun getAllDecimos(): LiveData<List<DecimoJugado>>

    @Insert
    suspend fun insertDecimo(decimo: DecimoJugado)
    @Delete
    suspend fun deleteDecimo(decimo: DecimoJugado)
}