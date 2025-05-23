package com.example.albumeter

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.albumeter.Modelo.Usuario
import com.example.albumeter.databinding.FragmentMenuLogin1Binding


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuLoginFragment1 : Fragment() {

    private var _binding: FragmentMenuLogin1Binding? = null
    private val binding get() = _binding!!

    lateinit var loginUsuario: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuLogin1Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //titulo del fragmento
        (activity as AppCompatActivity).supportActionBar?.title = "Albumeter"
        //traerme login del usuario
        val mainActivity = activity as? MainActivity
        if (mainActivity != null) {
            loginUsuario = mainActivity.loginusuario
            val editor: SharedPreferences.Editor = loginUsuario.edit()
        } else {
            Log.e("MenuLoginFragment1", "MainActivity es null")
        }

        var usuario = (activity as MainActivity).miViewModel.usuario
        usuario = Usuario("", "", "")

        binding.botonLogIn.setOnClickListener {
            if (comprobarPassword()) {
                findNavController().navigate(R.id.action_menuLoginFragment1_to_menuPrincipalFragment3)
                Toast.makeText(
                    requireContext(),
                    "Login correcto",
                    Toast.LENGTH_SHORT
                ).show()


            } else {
                Toast.makeText(
                    requireContext(),
                    "Login Incorrecto",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.botonRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_menuLoginFragment1_to_menuRegistroFragment2)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun comprobarPassword(): Boolean {


        val datos = (activity as MainActivity).loginusuario
        Log.d("shared preferences", datos.toString())
        val nombreGuardado = datos.getString("Nombre", "No registrado")
        val passwordGuardada = datos.getString("password", "Sin contraseña")

        val usuario = Usuario(
            nombre = binding.ETNombre.text.toString(),
            password = binding.ETContraseA.text.toString(),
            correo = ""
        )

        val usuarioLocal: Usuario? = nombreGuardado?.let {
            if (passwordGuardada != null) {
                Usuario(
                    nombre = it,
                    password = passwordGuardada,
                    correo = ""
                )
            } else null
        }

        if (usuario.nombre.trim().lowercase() == usuarioLocal?.nombre?.trim()?.lowercase() &&
            usuario.password.trim().lowercase() == usuarioLocal?.password?.trim()?.lowercase()


        ) {
            // Usuario y contraseña coinciden, permitir el login
            Toast.makeText(
                requireContext(),
                "Log In correcto",
                Toast.LENGTH_SHORT
            ).show()
            return true
        } else {
            // Datos incorrectos
            Toast.makeText(
                requireContext(),
                "Log In Incorrecto",
                Toast.LENGTH_SHORT
            ).show()
            binding.ETContraseA.setText("")
            return false
        }

        /* (activity as MainActivity).miViewModel.usuario =
             Usuario(
                 nombre = binding.ETNombre.text.toString(),
                 password = binding.ETContraseA.text.toString(),
                 correo = ""
             )

         val nombre = binding.ETNombre.text.toString().trim()
         val password = binding.ETContraseA.text.toString().trim()

         if (nombre.isEmpty() || password.isEmpty()) {
             return false
         }

         return true*/

    }


}