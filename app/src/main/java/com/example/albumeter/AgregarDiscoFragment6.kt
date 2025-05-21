package com.example.albumeter

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.albumeter.BBDD.Album
import com.example.albumeter.BBDD.Estado
import com.example.albumeter.BBDD.Repositorio
import com.example.albumeter.databinding.FragmentAgregarDisco6Binding


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class AgregarDiscoFragment6 : Fragment() {

    private var _binding: FragmentAgregarDisco6Binding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAgregarDisco6Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Para hacer el menu desplegable de estado
        val estados = Estado.values().map { it.name } // Convierte el enum a una lista de Strings
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, estados)

        binding.AutocompleteEstado.setAdapter(adapter)

        // Mostrar el menú desplegable al hacer clic en el campo
        binding.AutocompleteEstado.setOnClickListener {
            binding.AutocompleteEstado.showDropDown()
        }


        binding.botonInsertarDisco.setOnClickListener {
            //recojer el resultado del menu desplegable
            val estadoSeleccionado = try {
                enumValueOf<Estado>(binding.AutocompleteEstado.text.toString())
            } catch (e: IllegalArgumentException) {
                Estado.ALBUM_ANADIDO // Valor por defecto si la entrada no es válida
            }

            val nuevoDisco = Album(
                titulo = binding.EditTextTituloDisco.toString(),
                banda = binding.EditTextNombreBanda.toString(),
                ano = binding.EditTextAno.toString().toIntOrNull() ?: 0,
                estilo = binding.EditTextEstilo.toString(),
                pais = binding.EditTextPais.toString(),
                estado = estadoSeleccionado,

            )

            (activity as MainActivity).miViewModel.agregarDisco(nuevoDisco)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}