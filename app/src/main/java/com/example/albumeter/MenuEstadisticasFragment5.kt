package com.example.albumeter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.albumeter.databinding.FragmentMenuEstadisticas5Binding



/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuEstadisticasFragment5 : Fragment() {

    private var _binding: FragmentMenuEstadisticas5Binding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuEstadisticas5Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Estadisticas"

        //botones
        binding.botonEstadisticas.setOnClickListener{
            findNavController().navigate(R.id.action_menuEstadisticasFragment5_to_estadisticasFragment8)
        }

        binding.botonGraficos.setOnClickListener{
            findNavController().navigate(R.id.action_menuEstadisticasFragment5_to_estadisticasGraficosFragment9)
        }
        binding.botonEstanterias.setOnClickListener{
            findNavController().navigate(R.id.action_menuEstadisticasFragment5_to_estanteriasFragment10)
        }

        binding.botonAtrasMenuEstadisticas5.setOnClickListener{
            findNavController().navigate(R.id.action_menuEstadisticasFragment5_to_menuPrincipalFragment3)
        }



    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}