package joseoliva.com.loteriapp.activities.viewmodel

import androidx.lifecycle.LiveData
import joseoliva.com.loteriapp.activities.bbdd.DecimoDao
import joseoliva.com.loteriapp.activities.models.DecimoJugado

/*
El repositorio es una clase desde la que se decide de donde
obtener los datos. Pueden obtenerse desde una API o desde una
bbdd local como Room.
El repositorio tiene la logica para decidir de donde obtendra los datos.
Esta clase recibe un objeto de tipo DAO
 */
class RepositorioDecimos(val decimodao: DecimoDao) {
    //creo las funciones para operar en la bbdd. Obtendré los resultados llamando a las fun del dao
    //primero obtengo en una lista todos los decimos que hayamos guardado
    val listaDecimosJugados: LiveData<List<DecimoJugado>> = decimodao.getAllDecimos()

    suspend fun insertarDecimo(decimo:DecimoJugado){
        decimodao.insertDecimo(decimo)
    }
    suspend fun borrarDecimo(decimo: DecimoJugado){
        decimodao.deleteDecimo(decimo)
    }
}