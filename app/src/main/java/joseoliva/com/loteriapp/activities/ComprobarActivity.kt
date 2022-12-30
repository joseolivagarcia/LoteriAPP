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
import com.google.firebase.firestore.FirebaseFirestore
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

        configRealtimeFirestore() //llamo al metodo que inicia Firestore

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
                                p.premio = (p.premio * d.participacion / 20).toFloat() //esto es lo que te toca segun lo que juegues
                                listaTusPremios.add(p)
                            }else if (d.numero == p.numero +1 || d.numero == p.numero -1 && p.premio == 400000f){
                                p.premio = (2000 * d.participacion / 20).toFloat()
                                p.numero = d.numero //para que aparezca tu numero jugado y no el anterior o posterior (aunque es realmente el que te toca)
                                listaTusPremios.add(p)
                            }
                        }
                    }
                initRecyclerView()
            }
        }

        //lista de pruebas provisional esta lista la tendre que obtener de firebase
        listapremiados = listOf(
            DecimoPremiado(23223,400000f),
            DecimoPremiado(54678,125000f),
            DecimoPremiado(56789,50000f),
            DecimoPremiado(32456,50000f),
        )


        //initRecyclerView() //lo llamamos cuando comprobamos si tenemos algun numero premiado

        binding.btnvolver.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            listaTusPremios.clear()
        }

    }

    private fun configRealtimeFirestore() {
        val db = FirebaseFirestore.getInstance() //con esto accedo a la bd de Firestore
        /*
        * Ahora necesito acceder a la coleccion que me interesa y recorrer con un bucle todos los objetos
        * que contenga. Cada objeto lo añadire a la lista de listapremiados para compararlos con
        * los objetos que tengo en la listadecimos.
         */
        db.collection("Premiados").get().addOnSuccessListener { snapshots ->
            for (document in snapshots){
                Log.d("firebase",document.get("numero").toString())
            }
        }
    }

    private fun initRecyclerView() {

        adapter = PremiosAdapter(listaTusPremios.toList())
        binding.rvdecimos.layoutManager = linearLayoutManager
        binding.rvdecimos.adapter = adapter

    }
}