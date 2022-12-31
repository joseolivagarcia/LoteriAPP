package joseoliva.com.loteriapp.activities.models

import com.google.firebase.firestore.Exclude

data class DecimoPremiado(
    @get:Exclude var id: String? = null,
    var numero: Int = 0,
    var premio: Float = 0f
)
