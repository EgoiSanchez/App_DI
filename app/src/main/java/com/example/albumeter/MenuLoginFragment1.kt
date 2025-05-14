package com.example.albumeter

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
        val editor:Editor=loginUsuario.edit()

        var usuario = (activity as MainActivity).miViewModel.usuario
        usuario = Usuario("","","")

        binding.botonLogIn.setOnClickListener{
            if (comprobarPassword()) {
                Toast.makeText(
                    requireContext(),
                    "Login correcto",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



        binding.botonLogIn.setOnClickListener{
            findNavController().navigate(R.id.action_menuLoginFragment1_to_menuPrincipalFragment3)
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun comprobarPassword(): Boolean {

        val nombre = binding.ETNombre.text.toString().trim()
        val password = binding.ETContraseA.text.toString().trim()

        if (nombre.isEmpty() || password.isEmpty()) {
            return false
        }

        return true
    }



 }