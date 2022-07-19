package com.example.dice4g.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dice4g.app.InstanceManager
import com.example.dice4g.databinding.FragmentHistoryBinding
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    lateinit var notificationsViewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null

    val manager = lazy {
        InstanceManager.getInstance(this.requireContext())
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ResultAdapter()
        binding.recyclerView.adapter = adapter

        notificationsViewModel.profile.observe(viewLifecycleOwner) {
            binding.textProfile.text = it.Name
        }

        notificationsViewModel.results.observe(viewLifecycleOwner) { results ->
            lifecycleScope.launch {
                adapter.submitData(results)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}