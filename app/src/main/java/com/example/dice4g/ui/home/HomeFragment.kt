package com.example.dice4g.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dice4g.app.InstanceManager
import com.example.dice4g.databinding.FragmentHomeBinding
import com.example.dice4g.utils.DiceType

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        homeViewModel.dice.observe(viewLifecycleOwner) {
            binding.textView.text = it.toString()
        }

        binding.button.setOnClickListener {
            homeViewModel.roll(InstanceManager.getInstance(this.requireContext()))
        }

        val buttons = listOf(
            Pair(binding.buttonD4, DiceType.D4),
            Pair(binding.buttonD6, DiceType.D6),
            Pair(binding.buttonD8, DiceType.D8),
            Pair(binding.buttonD10, DiceType.D10),
            Pair(binding.buttonD12, DiceType.D12),
            Pair(binding.buttonD20, DiceType.D20)
        )

        buttons.forEach { value ->
            value.first.setOnClickListener {
                homeViewModel.changeDiceType(value.second)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}