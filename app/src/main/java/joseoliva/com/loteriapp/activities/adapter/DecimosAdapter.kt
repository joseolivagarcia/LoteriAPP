package joseoliva.com.loteriapp.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.activities.models.DecimoJugado

class DecimosAdapter(
    val onClickDelete: (DecimoJugado) -> Unit
): RecyclerView.Adapter<DecimosViewHolder>() {

    //creo una var de lista donde guardare todos los decimos jugados
    private var allDecimosJugados = ArrayList<DecimoJugado>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecimosViewHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return DecimosViewHolder(layoutinflater.inflate(R.layout.item_tusdecimos,parent,false))
    }

    override fun onBindViewHolder(holder: DecimosViewHolder, position: Int) {
        val item = allDecimosJugados[position]
        holder.render(item,onClickDelete)
    }

    override fun getItemCount(): Int = allDecimosJugados.size

    fun updateList(listadecimos: List<DecimoJugado>) {
        allDecimosJugados.clear()
        allDecimosJugados.addAll(listadecimos)
        notifyDataSetChanged()
    }
}