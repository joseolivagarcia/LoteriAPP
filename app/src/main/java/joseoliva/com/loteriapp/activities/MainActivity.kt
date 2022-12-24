package joseoliva.com.loteriapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import joseoliva.com.loteriapp.R
import joseoliva.com.loteriapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //añadimos viewbinding para las vistas
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inicializamos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}