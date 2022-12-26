package joseoliva.com.loteriapp.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.activities.models.DecimoPremiado

class PremiosAdapter(private var listadecimospremiados: List<DecimoPremiado>): RecyclerView.Adapter<PremiosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremiosViewHolder {

        val layoutinflater = LayoutInflater.from(parent.context)
        return PremiosViewHolder(layoutinflater.inflate(R.layout.item_decimo_premiadd,parent,false))
    }

    override fun onBindViewHolder(holder: PremiosViewHolder, position: Int) {
        val item = listadecimospremiados[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = listadecimospremiados.size


}