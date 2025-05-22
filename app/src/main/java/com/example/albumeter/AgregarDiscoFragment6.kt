package com.example.albumeter

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
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
        // Convierte el enum a una lista de strings
        val estados = Estado.values().map { it.name }
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, estados)
        binding.AutocompleteEstado.setAdapter(adapter)

        // Mostrar el menú desplegable al hacer click en el campo
        binding.AutocompleteEstado.setOnClickListener {
            binding.AutocompleteEstado.showDropDown()

        }

        //para que al traer el disco desde detalles y  rellene los campos
        var posicion = 0
        // si no traigo argumentos(id) le asigno el -1
        val idAlbum = arguments?.getInt("id") ?: -1

        if (idAlbum <= 0) {
            Log.d("Depuración", "Navegaste desde el menú principal. Formulario vacío.")
            binding.botonBorrarDisco.visibility = View.INVISIBLE
            binding.botonModificarDisco.visibility = View.INVISIBLE

        } else {
            (activity as MainActivity).miViewModel.listaAlbumes.observe(viewLifecycleOwner) { albumes ->
                val album = albumes?.find { it.id == idAlbum } // Busca por ID correcto
                if (album != null) {
                    // Rellena los campos con album.titulo, album.banda, etc.
                    binding.EditTextTituloDisco.setText(album.titulo)
                    binding.EditTextNombreBanda.setText(album.banda)
                    album.ano?.let { binding.EditTextAno.setText(it.toString()) }
                    binding.EditTextEstilo.setText(album.estilo)
                    binding.EditTextPais.setText(album.pais)
                    binding.EditTextDescripcion.setText(album.descripcion)
                    binding.EditTextPortada.setText(album.portada)
                    album.nota?.let { binding.EditTextNota.setText(it.toString()) }
                    //convertir la lista de tags en una cadena separadas por comas
                    binding.EditTextTags.setText(album.tags.joinToString(", "))
                    binding.AutocompleteEstado.setText(album.estado.name)

                    //oculto el boton de insertar disco
                    binding.botonInsertarDisco.visibility = View.INVISIBLE
                } else {
                    findNavController().popBackStack()
                }
            }


        }
        //Insertar disco
        binding.botonInsertarDisco.setOnClickListener {
            //recojer el resultado del menu desplegable
            val estadoSeleccionado = try {
                Estado.valueOf(binding.AutocompleteEstado.text.toString())
            } catch (e: IllegalArgumentException) {
                Estado.ALBUM_ANADIDO // Valor por defecto si la entrada no es válida
            }

            val nuevoDisco = Album(
                titulo = binding.EditTextTituloDisco.text.toString(),
                banda = binding.EditTextNombreBanda.text.toString(),
                ano = binding.EditTextAno.text.toString().toIntOrNull() ?: 0,
                estilo = binding.EditTextEstilo.text.toString(),
                pais = binding.EditTextPais.text.toString(),
                estado = estadoSeleccionado,
                descripcion = binding.EditTextDescripcion.text.toString(),
                tags = listOf(binding.EditTextTags.text.toString()),
                nota = binding.EditTextNota.text.toString().toDoubleOrNull() ?: 0.0,
                portada = binding.EditTextPortada.text.toString()
            )

            (activity as MainActivity).miViewModel.agregarDisco(nuevoDisco)
            Toast.makeText(context, "Disco añadido", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack() // Regresar a la pantalla anterior
        }

        binding.botonAtrasAgregarDisco.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}