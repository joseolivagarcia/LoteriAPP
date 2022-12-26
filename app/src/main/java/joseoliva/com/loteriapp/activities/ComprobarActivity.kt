package joseoliva.com.loteriapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.activities.adapter.DecimosAdapter
import joseoliva.com.loteriapp.activities.models.DecimoJugado
import joseoliva.com.loteriapp.activities.viewmodel.ViewModelDecimo
import joseoliva.com.loteriapp.databinding.ActivityComprobarBinding

class ComprobarActivity : AppCompatActivity() {

    lateinit var binding: ActivityComprobarBinding
    lateinit var adapter: DecimosAdapter
    lateinit var recyclerView: RecyclerView
    var listaJugados = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprobarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recibo la lista de los numeros que juego
        listaJugados = intent.getSerializableExtra("listanumeros") as ArrayList<Int>


    }
}