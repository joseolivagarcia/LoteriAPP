package joseoliva.com.loteriapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.activities.adapter.DecimosAdapter
import joseoliva.com.loteriapp.activities.models.DecimoJugado
import joseoliva.com.loteriapp.activities.viewmodel.ViewModelDecimo
import joseoliva.com.loteriapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //añadimos viewbinding para las vistas
    lateinit var binding: ActivityMainBinding
    //creamos las var necesarias
    lateinit var viewModel: ViewModelDecimo
    lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inicializamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializo el recycler con los decimos
        recyclerview = binding.rvdecimos
        recyclerview.layoutManager = LinearLayoutManager(this)
        //inicializamos el adapter y le pasamos como parametros el contexto y las interface que necesita
        val adapter = DecimosAdapter(onClickDelete = {decimo -> onItemDelete(decimo)})
        recyclerview.adapter = adapter

        //inicializamos el viewmodel con un provider y le pasamos nuestra clase de ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDecimo::class.java)

        //Llamamos a la lista de decimos del viewModel para observar los cambios que haya en la lista
        //lo puedo "observar" porque es un LiveData y cada vez que la lista cambie algo, se actualiza
        viewModel.listadecimos.observe(this,{list -> list?.let {
            adapter.updateList(it)
        }})

        //creo el AlertDialog para que al pulsar el boton de añadir pueda insertar un numero y la participacion
        binding.fab.setOnClickListener {
            val inflater = layoutInflater //creo una var para guardar un layoutinflater
            val dialoglayout = inflater.inflate(R.layout.dialog_insertar_decimo,null) //en otra var inflo el layout que tenemos para capturar los numeros
            //referencio los campos donde inertamos los datos de numero y participacion
            val numdecimo = dialoglayout.findViewById<TextInputEditText>(R.id.etnumero)
            val participacion = dialoglayout.findViewById<TextInputEditText>(R.id.etparticipacion)
            //creamos el AlertDialog
            val alertdialog = AlertDialog.Builder(this)
            alertdialog.setTitle("Insertar Décimo")
            alertdialog.setView(dialoglayout) //le paso el layout para poder insertar datos
            //añadimos los botones del dialog
            alertdialog.setPositiveButton("Insertar"){
                dialog,_ ->
                dialog.dismiss()
                //guardamos el decimo en la bbdd
                val numero = numdecimo.text.toString().toInt()
                val parti = participacion.text.toString().toDouble()
                val newDecimo = DecimoJugado(numero,parti)
                viewModel.insertardecimo(newDecimo)
            }
            alertdialog.setNegativeButton("Cancelar"){
                dialog,_ ->
                dialog.dismiss()
            }

            alertdialog.show()
        }

        //funcionalidad para el boton de comprobar decimos
        binding.btncomprobar.setOnClickListener {
            val intent = Intent(this, ComprobarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onItemDelete(decimo: DecimoJugado) {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Seguro de eliminar este décimo?")
            .setNegativeButton("NO"){
                    view, _ -> view.dismiss()
            }
            .setPositiveButton("SI"){
                    view,_ -> view.dismiss()
                viewModel.borrardecimo(decimo)
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}