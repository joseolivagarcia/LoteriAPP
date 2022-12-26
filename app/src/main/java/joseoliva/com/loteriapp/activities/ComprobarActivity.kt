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
    var listaJugados = arrayListOf<Int>()
    lateinit var listapremiados: List<DecimoPremiado>
    var listaTusPremios = mutableListOf<DecimoPremiado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprobarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lista de pruebas provisional
        listapremiados = listOf(
            DecimoPremiado(12345,400000),
            DecimoPremiado(54678,125000),
            DecimoPremiado(56789,50000),
            DecimoPremiado(32456,50000),
        )

        //recibo la lista de los numeros que juego y que comparare con los de firebase
        listaJugados = intent.getSerializableExtra("listanumeros") as ArrayList<Int>

        for (num in listapremiados){
            for (minum in listaJugados){
                if(num.numero == minum){
                    listaTusPremios.add(num)
                }
            }

            initRecyclerView()
        }

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