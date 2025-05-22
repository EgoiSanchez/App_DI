package com.example.albumeter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.albumeter.databinding.FragmentMisDiscosContenedor4Binding
import com.example.albumeter.databinding.RecyclerViewDiscoBinding
import com.example.albumeter.recyclerView.Adaptador


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class MisDiscosContenedor4 : Fragment() {

    private var _binding: FragmentMisDiscosContenedor4Binding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMisDiscosContenedor4Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //cargar base de datos
        //(activity as MainActivity).miViewModel.mostrarAlbum()
        //live data oberserver de la lista de albumes
        (activity as MainActivity).miViewModel.listaAlbumes.observe(activity as MainActivity) {
            binding.newMisDiscosContenedor4.layoutManager = LinearLayoutManager(context)
            binding.newMisDiscosContenedor4.adapter = Adaptador(it)
        }

        binding.botonAtrasMisdiscosContenedor4.setOnClickListener{
            findNavController().popBackStack()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}