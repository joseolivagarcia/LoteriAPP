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

    var listapremiados = mutableListOf<DecimoPremiado>()
    var listaTusPremios = mutableListOf<DecimoPremiado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprobarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configRealtimeFirestore() //llamo al metodo que inicia Firestore

        binding.btnvolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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
            for (document in snapshots) {
                val decpremiado = document.toObject(DecimoPremiado::class.java)
                decpremiado.id = document.id
                listapremiados.add(decpremiado)
                Log.d("firebase", document.get("numero").toString())

            }
            Log.d("listapremiados", listapremiados.size.toString())
            /*
            * Una vez que tengo la lista que obtengo de firebase llamo al metodo que va a comprobar los numeros premiados (los de firebase)
            * con los numeros que tengo en la base de datos de room (lon numeros que juego)*/
            comprobarPremios()
        }
    }

    private fun comprobarPremios() {
        //inicializamos el viewmodel con un provider y le pasamos nuestra clase de ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDecimo::class.java)

        //aqui obtengo la lista con los decimos que juego, los que estan en la main activity. y los comparo con los premiados (los de firebase)
        viewModel.listadecimos.observe(this) { list ->
            list?.let {
                //aqui voy metiendo todas las combinaciones posibles de premio
                for (jugado in list) {
                    for (premiado in listapremiados) {
                        if (jugado.numero == premiado.numero) {
                            premiado.premio =
                                (premiado.premio * jugado.participacion / 20).toFloat() //esto es lo que te toca segun lo que juegues
                            listaTusPremios.add(premiado)
                        } else if (jugado.numero == premiado.numero + 1 || jugado.numero == premiado.numero - 1 && premiado.premio == 400000f) {
                            premiado.premio = (2000 * jugado.participacion / 20).toFloat()
                            premiado.numero =
                                jugado.numero //para que aparezca tu numero jugado y no el anterior o posterior (aunque es realmente el que te toca)
                            listaTusPremios.add(premiado)
                        }
                    }
                }
                Log.d("listatuspremios", listaTusPremios.size.toString())
                if(listaTusPremios.isEmpty()){
                    binding.tvtitulocomprobar.text = "Ningún Décimo Premiado"
                }else{
                    binding.tvtitulocomprobar.text = "Décimos Premiados"
                }
                initRecyclerView()
            }
        }
    }

    private fun initRecyclerView() {
        adapter = PremiosAdapter(listaTusPremios.toList())
        binding.rvdecimos.layoutManager = linearLayoutManager
        binding.rvdecimos.adapter = adapter

    }
}