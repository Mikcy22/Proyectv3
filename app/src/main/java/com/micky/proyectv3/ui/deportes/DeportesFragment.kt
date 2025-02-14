package com.micky.proyectv3.ui.deportes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.micky.proyectv3.databinding.FragmentDeportesBinding

class DeportesFragment : Fragment() {

    private var _binding: FragmentDeportesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val deportesViewModel =
            ViewModelProvider(this).get(DeportesViewModel::class.java)

        _binding = FragmentDeportesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDeportes
        deportesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}