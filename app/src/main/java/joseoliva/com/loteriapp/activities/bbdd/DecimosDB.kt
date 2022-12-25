package joseoliva.com.loteriapp.activities.bbdd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import joseoliva.com.loteriapp.activities.models.DecimoJugado

/*
 * Esta clase representa a la base de datos como tal. Será la clase que referenciemos cada
 * vez que queramos acceder a ella para consultar, guardar, borrar, etc cualquier tipo
 * de dato.
 * Debe ser una clase abstracta.
 * Tenemos que crear una anotacion @Database para indicarle en un array todas las tablas que
 * vayamos a utilizar y la version de la bbdd
 */
@Database(
    entities = arrayOf(DecimoJugado::class),
    version = 1
)
//la clase hereda de RoomDatabase
abstract class DecimosDB: RoomDatabase() {

    abstract fun getDecimosDao(): DecimoDao //utimizaremos esta funcion para obtener el dao donde necesitemos

    /*
    Metemos el companion object para prevenir que se abran
    multiples instancias de la bbdd al mismo tiempo
     */
    companion object{
        @Volatile
        private var INSTANCE: DecimosDB? = null
        fun getDatabase(context: Context): DecimosDB{
            //si la instancia no es nula la retorna
            //si es nula, crea la bbdd
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DecimosDB::class.java,
                    "decimos_database"
                ).build()
                INSTANCE = instance
                //devolvemos la instance
                instance
            }
        }
    }
}