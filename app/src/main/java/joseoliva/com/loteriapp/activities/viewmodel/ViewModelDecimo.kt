package joseoliva.com.loteriapp.activities.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import joseoliva.com.loteriapp.activities.bbdd.DecimosDB
import joseoliva.com.loteriapp.activities.models.DecimoJugado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
El viewModel es la clase que se ocupa de proveer de datos a la UI
Es una especie de intermediaria entre el repositorio y la UI.
 */
class ViewModelDecimo(application: Application): AndroidViewModel(application) {
    /*
   creamos una var donde guardaremos todas las burguers pedidas (una lista)
   y una var para referenciar nuestro repositorio
   Daran error hasta que las inicialicemos
    */
    val listadecimos: LiveData<List<DecimoJugado>>
    val repositorio: RepositorioDecimos

    init {
        /* el dao lo obtengo desde la clase DecimosDB llamando a la fun getDatabase
           y pasandola el contexto que estoy cogiendo de esta misma clase
           y llamando a la fun getDecimosDao de la misma clase DecimosDB
         */
        val dao = DecimosDB.getDatabase(application).getDecimosDao()
        //inicializo el repo pasandole el dao que acabo de obtener
        repositorio = RepositorioDecimos(dao)
        //y obtengo todas las notas en la var que creé arriba
        listadecimos = repositorio.listaDecimosJugados
    }
    /*
     Me creo las funciones para insertat,borrar o editar decimos segun necesite. LLamare a las funciones que
     hay en el repositorio.
     Lo hago con viewModelScope para no hacerlo en el hilo ppal y no bloquear la app
     */
    fun insertardecimo(decimo: DecimoJugado) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.insertarDecimo(decimo)
    }
    fun borrardecimo(decimo: DecimoJugado) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.borrarDecimo(decimo)
    }
}