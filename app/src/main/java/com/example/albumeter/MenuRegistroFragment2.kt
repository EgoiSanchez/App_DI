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
import androidx.navigation.fragment.findNavController
import com.example.albumeter.Modelo.Usuario
import com.example.albumeter.databinding.FragmentMenuRegistro2Binding


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuRegistroFragment2 : Fragment() {

    private var _binding: FragmentMenuRegistro2Binding? = null
    private val binding get() = _binding!!

    lateinit var loginUsuario: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuRegistro2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mainActivity = activity as? MainActivity
        if (mainActivity != null) {

            binding.botonRegistro.setOnClickListener {


                if (binding.ETContraseA.text.toString() == binding.ETContraseARepetida.text.toString()) {

                    loginUsuario = mainActivity.loginusuario
                    val editor: Editor = loginUsuario.edit()

                    (activity as MainActivity).miViewModel.usuario =
                        Usuario(
                            nombre = binding.ETNombre.text.toString(),
                            password = binding.ETContraseA.text.toString(),
                            correo = ""
                        )


                    //compruebo si usuario esta ya guardado (sintexis sugerida por android Studio)
                    if ((activity as MainActivity).miViewModel.usuario?.let { it1 ->
                            comprobarDatos(
                                it1
                            )
                        } == true) {
                        editor.putString("Nombre", binding.ETNombre.text.toString())
                        editor.putString("password", binding.ETContraseA.text.toString())
                        editor.apply()

                        findNavController().navigate(R.id.action_menuRegistroFragment2_to_menuLoginFragment1)
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "La contraseña no coincide",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.ETContraseA.setText("")
                    binding.ETContraseARepetida.setText("")
                }

            }

            binding.botonAtrasMenuRegistro.setOnClickListener {
                findNavController().navigate(R.id.action_menuRegistroFragment2_to_menuLoginFragment1)
            }


        }
    }

    fun comprobarDatos(usuario: Usuario): Boolean {
        val sharedPreferences = (activity as MainActivity).loginusuario
        val nombreGuardado = sharedPreferences.getString("Nombre", null)

        return if (nombreGuardado != null) {
            if (nombreGuardado.lowercase() == usuario.nombre.lowercase()) {
                Toast.makeText(requireContext(), "Nombre ya en uso", Toast.LENGTH_SHORT).show()
                false
            } else {
                true
            }
        } else {
            Toast.makeText(requireContext(), "Registro correcto", Toast.LENGTH_SHORT).show()
            true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}