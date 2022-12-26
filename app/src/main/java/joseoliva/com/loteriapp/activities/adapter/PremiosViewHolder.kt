package joseoliva.com.loteriapp.activities.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.activities.models.DecimoPremiado

class PremiosViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val numero = view.findViewById<TextView>(R.id.tvdecimopremiado)
    val premio = view.findViewById<TextView>(R.id.tvpremio)

    fun render(decimopremiado: DecimoPremiado) {
        numero.text = decimopremiado.numero.toString()
        premio.text = "Has ganado " + decimopremiado.premio.toString() + "€ al décimo (20€)"
    }

}