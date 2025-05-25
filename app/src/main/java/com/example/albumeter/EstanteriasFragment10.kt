package com.example.albumeter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumeter.BBDD.Estado
import com.example.albumeter.databinding.FragmentEstanterias10Binding
import com.example.albumeter.recyclerView.Adaptador


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EstanteriasFragment10 : Fragment() {

    private var _binding: FragmentEstanterias10Binding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEstanterias10Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Estado Discos"



        // Convertimos el mapa en una lista de Strings para el Spinner
        val opcionesEstado = listOf("Album Añadido", "Escuchado", "Pendiente de calificacion", "Calificado")
        val estadoMap = mapOf(
            "Album Añadido" to Estado.ALBUM_ANADIDO,
            "Escuchado" to Estado.ESCUCHADO,
            "Pendiente de calificacion" to Estado.PENDIENTE_CALIFICACION,
            "Calificado" to Estado.CALIFICADO
        )

        val spinnerOrden = view.findViewById<Spinner>(R.id.spinnerEstadoDiscos)

        // Adaptador correcto con lista de Strings
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesEstado)
        adapterSpinner.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        spinnerOrden.adapter = adapterSpinner

        spinnerOrden.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val opcionSeleccionado = estadoMap[opcionesEstado[position]] // Convierte la selección en un Estado válido

                (activity as MainActivity).miViewModel.listaAlbumes.observe(viewLifecycleOwner) { albumes ->
                    val albumesFiltrados = albumes.filter { it.estado == opcionSeleccionado }

                    binding.newMisDiscosTop10.layoutManager = LinearLayoutManager(requireContext())
                    binding.newMisDiscosTop10.adapter = Adaptador(albumesFiltrados)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.botonAtrasEstanterias.setOnClickListener {
            findNavController().navigate(R.id.action_estanteriasFragment10_to_menuEstadisticasFragment5)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}