package com.example.dice4g.ui.profiles

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dice4g.databinding.FragmentDashboardBinding
import com.example.dice4g.ui.history.ResultAdapter
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val listener: ProfileListener by lazy {
        object : ProfileListener {
            override fun deleteProfile(profileId: String) {
                profileViewModel.removeProfile(profileId)
            }

            override fun useProfile(profileId: String) {
                profileViewModel.useProfile(profileId)
                Snackbar.make(binding.root, "Profile Updated!", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.loadProfiles()

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ProfileAdapter(listener, listOf())
        binding.recyclerView.adapter = adapter

        profileViewModel.profiles.observe(viewLifecycleOwner) {
            adapter.profiles = it
            adapter.notifyDataSetChanged()
        }

        binding.editTextTextPersonName.addTextChangedListener {
            it?.let {
                profileViewModel.setName(it.toString())
            }
        }

        binding.button2.setOnClickListener {
            profileViewModel.addProfile()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}