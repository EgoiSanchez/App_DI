package com.example.albumeter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.albumeter.databinding.FragmentEstadisticasGraficos9Binding
import com.example.albumeter.databinding.FragmentMenuLogin1Binding
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


/**
 * A simple [Fragment] subclass.
 * Use the [MenuLoginFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EstadisticasGraficosFragment9 : Fragment() {

    private var _binding: FragmentEstadisticasGraficos9Binding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEstadisticasGraficos9Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mapaEstiloColor = mutableMapOf<String, Int>()
        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        //spinner, referencia: https://www.geeksforgeeks.org/spinner-in-android-with-example/
        val spinner = view.findViewById<Spinner>(R.id.spinnerEstilos)
        val opciones = listOf("Estilos", "Año", "Pais")
        val adapater =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapater.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapater

        //manejar opcion
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSeleccionada = opciones[position]
                //para mas adelante elegir, pais y año

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        //colores qyue me traigo de Values.colors.xml para usarlos en el queso
        val colores = listOf(
            R.color.R,
            R.color.Java,
            R.color.Python,
            R.color.colorPrimary,
            R.color.color_one,
            R.color.colorAccent,
            R.color.color_white,
            R.color.CPP,
            R.color.colorPrimaryDark,
            R.color.color_two,

            )


        // la forma de recorrer un for de un livedata es con un observer
        //refenrecia https://stackoverflow.com/questions/61935606/mpandroidchart-how-to-set-custom-colors-in-piechart
        (activity as MainActivity).miViewModel.listaAlbumes.observe(viewLifecycleOwner) { albumes ->
            val estilosSuma = albumes.groupingBy { it.estilo }.eachCount()
            val colores = listOf(
                R.color.R, R.color.Java, R.color.Python,
                R.color.colorPrimary, R.color.color_one,
                R.color.colorAccent, R.color.color_white,
                R.color.CPP, R.color.colorPrimaryDark,
                R.color.color_two
            )

            var index = 0 // Para asignar colores en orden
            val mapaEstiloColor = mutableMapOf<String, Int>()
            estilosSuma.forEach { (estilo, cantidad) ->
                if (index >= colores.size) index = 0 // Reiniciar si supera el tamaño de la lista

                val colorRes = colores[index]
                val colorFinal = ContextCompat.getColor(requireContext(), colorRes)

                pieChart.addPieSlice(
                    PieModel(estilo ?: "Desconocido", cantidad.toFloat(), colorFinal)
                )
                mapaEstiloColor[estilo ?: "Desconocido"] = colorFinal
                index++ // Pasar al siguiente color de la lista
            }

            pieChart.startAnimation()


            val leyendagrafico = view.findViewById<LinearLayout>(R.id.leyendaColores)
            mapaEstiloColor.forEach { (estilo, color) ->
                val indicadorColor = View(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(40, 40)
                    setBackgroundColor(color)
                }

                val textoLeyenda = TextView(requireContext()).apply {
                    text = estilo
                    setTextColor(Color.BLACK)
                    textSize = 16f
                    setPadding(8, 8, 8, 8)
                }

                val contenedorLeyenda = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(8, 8, 8, 8)
                    addView(indicadorColor)
                    addView(textoLeyenda)
                }

                leyendagrafico.addView(contenedorLeyenda)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}