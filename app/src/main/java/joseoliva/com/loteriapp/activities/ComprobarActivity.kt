package joseoliva.com.loteriapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.activities.adapter.DecimosAdapter
import joseoliva.com.loteriapp.activities.adapter.PremiosAdapter
import joseoliva.com.loteriapp.activities.models.DecimoJugado
import joseoliva.com.loteriapp.activities.models.DecimoPremiado
import joseoliva.com.loteriapp.activities.viewmodel.ViewModelDecimo
import joseoliva.com.loteriapp.databinding.ActivityComprobarBinding

class ComprobarActivity : AppCompatActivity() {

    lateinit var binding: ActivityComprobarBinding
    lateinit var adapter: PremiosAdapter
    private var linearLayoutManager = LinearLayoutManager(this)
    lateinit var viewModel: ViewModelDecimo

    lateinit var listapremiados: List<DecimoPremiado>
    var listaTusPremios = mutableListOf<DecimoPremiado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprobarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializamos el viewmodel con un provider y le pasamos nuestra clase de ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDecimo::class.java)

        //aqui obtengo la lista con los decimos que juego, los que estan en la main activity.
        viewModel.listadecimos.observe(this) { list ->
            list?.let {
                    for (d in list){
                        for (p in listapremiados){
                            if (d.numero == p.numero){
                                listaTusPremios.add(p)
                            }
                        }
                    }
                initRecyclerView()
            }
        }

        //lista de pruebas provisional
        listapremiados = listOf(
            DecimoPremiado(12345,400000),
            DecimoPremiado(54678,125000),
            DecimoPremiado(56789,50000),
            DecimoPremiado(32456,50000),
        )


        //initRecyclerView() //lo llamamos cuando comprobamos si tenemos algun numero premiado

        binding.btnvolver.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            listaTusPremios.clear()
        }

    }

    private fun initRecyclerView() {

        adapter = PremiosAdapter(listaTusPremios.toList())
        binding.rvdecimos.layoutManager = linearLayoutManager
        binding.rvdecimos.adapter = adapter

    }
}