package com.example.albumeter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        (activity as AppCompatActivity).supportActionBar?.title = "Tus discos"


        //live data oberserver de la lista de albumes para que si se cambia la base de datos se actualize autamticamente
        (activity as MainActivity).miViewModel.listaAlbumes.observe(viewLifecycleOwner) { albumes ->
            binding.newMisDiscosContenedor4.layoutManager = LinearLayoutManager(requireContext())
            binding.newMisDiscosContenedor4.adapter = Adaptador(albumes)
        }



        binding.botonAtrasMisdiscosContenedor4.setOnClickListener {
            findNavController().popBackStack()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}