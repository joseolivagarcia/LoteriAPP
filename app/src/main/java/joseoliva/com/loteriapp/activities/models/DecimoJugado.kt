package joseoliva.com.loteriapp.activities.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Esta data class representa una entidad de Room, sería como una tabla de la bbdd.
 * Representa al modelo de décimo de loteria que vamos a rellenar con los datos que
 * introduzca el usuario. Así podremos guardar cada décimo en una bbdd local
 */
@Entity(tableName = "decimosjugados")
data class DecimoJugado(
    @ColumnInfo(name = "numero")
    val numero: Int,
    @ColumnInfo(name = "participacion")
    val participacion: Double
)
{
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
