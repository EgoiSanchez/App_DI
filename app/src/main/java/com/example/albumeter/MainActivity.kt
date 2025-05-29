package com.example.albumeter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.albumeter.BBDD.BBDD
import com.example.albumeter.BBDD.Repositorio
import com.example.albumeter.Modelo.AlbumViewModelFactory
import com.example.albumeter.Modelo.VM
import com.example.albumeter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var loginusuario: SharedPreferences
    lateinit var miDataBase: BBDD
    private lateinit var miRepositorio: Repositorio
    lateinit var miViewModel: VM
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //almaceno datos en las preferences
        loginusuario = this.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Faltaba usar el binding para la toolbar
        setSupportActionBar(binding.toolbar)

        //instancio la base de datos
        miDataBase = BBDD.getDatabase(this)
        Log.d("BBDD", "Base de datos abierta: ${miDataBase.isOpen}")

        //instancio el repo y el VM
        miRepositorio = Repositorio(miDataBase.miDAO())
        miViewModel = ViewModelProvider(this, AlbumViewModelFactory(miRepositorio))[VM::class.java]

        enableEdgeToEdge()


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //Facilita la navegacion hacia atras del sistema
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //para inflar el menu de arriba
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // manajear opciones menu de arriba
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolBarMenuPrincipal -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.menuPrincipalFragment3)
                true
            }

            R.id.toolBarLogOut -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.menuLoginFragment1)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}