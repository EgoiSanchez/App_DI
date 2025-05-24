package com.example.albumeter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albumeter.databinding.FragmentEstadisticas8Binding
import com.example.albumeter.recyclerView.Adaptador


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EstadisticasFragment8 : Fragment() {

    private var _binding: FragmentEstadisticas8Binding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEstadisticas8Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val opcionesOrden = listOf("Mayor a menor", "Menor a mayor")
        val spinnerOrden = view.findViewById<Spinner>(R.id.spinnerOrdenNotas)


        // Spiner para seleccionar orden referencia Copilot y https://stackoverflow.com/questions/38762010/how-to-use-spinner-in-recyclerview
        //adaptador spinner
        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesOrden)
        adapterSpinner.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        spinnerOrden.adapter = adapterSpinner

        //listener para detectar cambios en el spinner
        spinnerOrden.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            //on item selected se ejecuta segun se selecciona
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int, //posicion de las opciones
                id: Long
            ) {
                //se guarda segun las opciones que tengamos en el array de arriba
                val ordenSeleccionado = opcionesOrden[position]

                //ordena los albumes segun la nota
                (activity as MainActivity).miViewModel.listaAlbumes.observe(viewLifecycleOwner) { albumes ->
                    val albumesOrdenados = when (ordenSeleccionado) {
                        "Mayor a menor" -> albumes.sortedByDescending { it.nota }.take(10)
                        "Menor a mayor" -> albumes.sortedBy { it.nota }.take(10)
                        else -> albumes
                    }

                    binding.newMisDiscosTop10.layoutManager = LinearLayoutManager(requireContext())
                    binding.newMisDiscosTop10.adapter = Adaptador(albumesOrdenados)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.botonAtrasEstadisticas.setOnClickListener{
            findNavController().navigate(R.id.action_estadisticasFragment8_to_menuEstadisticasFragment5)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}