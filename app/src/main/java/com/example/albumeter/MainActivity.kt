package com.example.albumeter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.albumeter.BBDD.BBDD
import com.example.albumeter.BBDD.Repositorio
import com.example.albumeter.Modelo.AlbumViewModelFactory
import com.example.albumeter.Modelo.VM


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var loginusuario: SharedPreferences

    lateinit var miDataBase: BBDD

    private lateinit var miRepositorio: Repositorio
    lateinit var miViewModel: VM



    override fun onCreate(savedInstanceState: Bundle?) {
        loginusuario = this.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)

        miDataBase = BBDD.getDatabase(this)
        Log.d("BBDD", "Base de datos abierta: ${miDataBase.isOpen}")


        miRepositorio = Repositorio(miDataBase.miDAO())
        miViewModel = ViewModelProvider(this, AlbumViewModelFactory(miRepositorio))[VM::class.java]

        val db = BBDD.getDatabase(this)
        Log.d("Depuración", "Estado de la BD en MainActivity: ${db.isOpen}")


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)




        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navController.navController.navigateUp() || super.onSupportNavigateUp()
    }

}
