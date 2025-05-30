package com.example.albumeter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import com.example.albumeter.databinding.FragmentMenuPrincipal3Binding
import com.example.albumeter.databinding.FragmentMenuRegistro2Binding


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuPrincipalFragment3 : Fragment() {

    private var _binding: FragmentMenuPrincipal3Binding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuPrincipal3Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Menu Principal"



        //botones
        binding.botonAgregarDisco.setOnClickListener{
            findNavController().navigate(R.id.action_menuPrincipalFragment3_to_agregarDiscoFragment6)
        }

        binding.botonEstadisticas.setOnClickListener{
            findNavController().navigate(R.id.action_menuPrincipalFragment3_to_menuEstadisticasFragment5)
        }

        binding.botonMisDiscos.setOnClickListener{
            findNavController().navigate(R.id.action_menuPrincipalFragment3_to_misDiscosContenedor4)
        }




    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}