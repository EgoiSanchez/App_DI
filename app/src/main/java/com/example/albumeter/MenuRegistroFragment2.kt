package com.example.albumeter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.albumeter.databinding.FragmentMenuRegistro2Binding


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuRegistroFragment2 : Fragment() {

    private var _binding: FragmentMenuRegistro2Binding? = null
    private val binding get() = _binding!!

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





    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}