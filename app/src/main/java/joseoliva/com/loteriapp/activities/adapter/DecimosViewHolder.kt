package joseoliva.com.loteriapp.activities.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.activities.models.DecimoJugado

class DecimosViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //obtengo las vistas que voy a tener que rellenar en cada item
    val numero = view.findViewById<TextView>(R.id.tvdecimo)
    val participacion = view.findViewById<TextView>(R.id.tvparticipacion)
    val btnborrar = view.findViewById<ImageView>(R.id.btndelete)

    fun render(decimoModel: DecimoJugado, onClickDelete:(DecimoJugado) -> Unit){
        //relleno las vistas con los datos que obtengo de cada item que recorro
        numero.text = decimoModel.numero.toString()
        participacion.text = decimoModel.participacion.toString()

        //doy funcionalidad al boton de borrar
        btnborrar.setOnClickListener {
            onClickDelete(decimoModel)
        }
    }
}