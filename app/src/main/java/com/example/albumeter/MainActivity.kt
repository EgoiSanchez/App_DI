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
import com.example.albumeter.databinding.ActivityMainBinding // Asegúrate de tener el binding generado

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var loginusuario: SharedPreferences
    lateinit var miDataBase: BBDD
    private lateinit var miRepositorio: Repositorio
    lateinit var miViewModel: VM
    private lateinit var binding: ActivityMainBinding // Faltaba inicializar el binding

    override fun onCreate(savedInstanceState: Bundle?) {
        loginusuario = this.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)

        // Faltaba esta línea para inicializar el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Faltaba usar el binding para la toolbar
        setSupportActionBar(binding.toolbar) // Cambiado de findViewById a binding

        miDataBase = BBDD.getDatabase(this)
        Log.d("BBDD", "Base de datos abierta: ${miDataBase.isOpen}")

        miRepositorio = Repositorio(miDataBase.miDAO())
        miViewModel = ViewModelProvider(this, AlbumViewModelFactory(miRepositorio))[VM::class.java]

        enableEdgeToEdge()

        // Faltaba esta configuración del NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration) // Faltaba pasar appBarConfiguration

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Faltaba implementar correctamente la navegación hacia arriba
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Faltaba manejar el segundo ítem del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolBarMenuPrincipal -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.menuPrincipalFragment3)
                true
            }
            R.id.toolBarLogOut -> { // Faltaba este caso
                findNavController(R.id.nav_host_fragment).navigate(R.id.menuLoginFragment1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}