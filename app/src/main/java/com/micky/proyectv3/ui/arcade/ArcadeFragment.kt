package com.micky.proyectv3.ui.arcade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.micky.proyectv3.databinding.FragmentArcadeBinding

class ArcadeFragment : Fragment() {

    private var _binding: FragmentArcadeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val arcadeViewModel =
            ViewModelProvider(this).get(ArcadeViewModel::class.java)

        _binding = FragmentArcadeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textArcade
        arcadeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}