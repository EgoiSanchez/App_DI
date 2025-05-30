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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.albumeter.BBDD.Album
import com.example.albumeter.BBDD.Estado
import com.example.albumeter.BBDD.Repositorio
import com.example.albumeter.databinding.FragmentAgregarDisco6Binding
import kotlin.math.log


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
        (activity as AppCompatActivity).supportActionBar?.title = ""

        //Para hacer el menu desplegable de estado
        // Convierte el enum a una lista de strings
        val estados = Estado.values().map { it.name }
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, estados)
        binding.AutocompleteEstado.setAdapter(adapter)

        //tengo el album disponible para MODIFICAR, cojo el id de los argmentos del fragment (vengo del recycler view), y observo el livedata dlistaalbumes y busco con el id
        val idAlbum = arguments?.getInt("id") ?: -1
        (activity as MainActivity).miViewModel.listaAlbumes.observe(viewLifecycleOwner) { albumes ->
            val album = albumes?.find { it.id == idAlbum }

            // Mostrar el menú desplegable al hacer click en el campo
            binding.AutocompleteEstado.setOnClickListener {
                binding.AutocompleteEstado.showDropDown()
            }


            //le pongo un if <0 xk si es mayor seria un disco ya en la base de datos, al ser -1 es un disco que no existe y por lo tanto para infresar
            if (idAlbum <= 0) {
                binding.botonBorrarDisco.visibility = View.INVISIBLE
                binding.botonModificarDisco.visibility = View.INVISIBLE

            } else {

                // Busca por ID correcto
                if (album != null) {

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
                Estado.ALBUM_ANADIDO
            }

            val nuevoDisco = Album(
                titulo = binding.EditTextTituloDisco.text.toString(),
                banda = binding.EditTextNombreBanda.text.toString(),
                // si no es un Int se le asigna 0
                ano = binding.EditTextAno.text.toString().toIntOrNull() ?: 0,
                estilo = binding.EditTextEstilo.text.toString(),
                pais = binding.EditTextPais.text.toString(),
                estado = estadoSeleccionado,
                descripcion = binding.EditTextDescripcion.text.toString(),
                tags = listOf(binding.EditTextTags.text.toString()),
                // si no es formato double 0.0 se le asigna 0.0
                nota = binding.EditTextNota.text.toString().toDoubleOrNull() ?: 0.0,
                portada = binding.EditTextPortada.text.toString()
            )

            //llamo funciona agregar disco
            (activity as MainActivity).miViewModel.agregarDisco(nuevoDisco)
            Toast.makeText(context, "Disco añadido", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }



        binding.botonAtrasAgregarDisco.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.botonBorrarDisco.setOnClickListener {
            val id = idAlbum

            //pop up de confirmacion de borrar disco
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmacion Borrado Disco")
                .setMessage("¿Estas seguro que quieres borrar el disco?")
                .setPositiveButton("Yeahh") { dialog, which ->
                    (activity as MainActivity).miViewModel.borraDisco(id)
                    Toast.makeText(context, "Disco borrado", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Nooo!!") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        binding.botonModificarDisco.setOnClickListener {

            val albumActualizado = crearAlbumDesdeCampos(idAlbum)

            if (albumActualizado != null) {

                //pop up de de confirmacion de modificar disco
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmacion Modificar Disco")
                    .setMessage("¿Estas seguro que quieres modificar el disco?")
                    .setPositiveButton("Yeahh!!") { dialog, which ->
                        (activity as MainActivity).miViewModel.modificarDisco(albumActualizado)
                        Toast.makeText(context, "Disco modificado", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Noooo!!") { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }

    //funcion por que si cojo el lista albumes LiveData y esta viejo da NULLPOINTEREXCEPTION (solo para modificar)
    fun crearAlbumDesdeCampos(id: Int): Album {
        return Album(
            id = id,
            titulo = binding.EditTextTituloDisco.text.toString(),
            banda = binding.EditTextNombreBanda.text.toString(),
            ano = binding.EditTextAno.text.toString().toIntOrNull() ?: 0,
            estilo = binding.EditTextEstilo.text.toString(),
            pais = binding.EditTextPais.text.toString(),
            estado = Estado.valueOf(binding.AutocompleteEstado.text.toString()),
            descripcion = binding.EditTextDescripcion.text.toString(),
            tags = binding.EditTextTags.text.toString().split(", "),
            nota = binding.EditTextNota.text.toString().toDoubleOrNull() ?: 0.0,
            portada = binding.EditTextPortada.text.toString()
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}