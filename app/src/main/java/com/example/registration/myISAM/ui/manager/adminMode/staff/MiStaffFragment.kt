package com.example.registration.myISAM.ui.manager.adminMode.staff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.staff.StaffAdapter
import com.example.registration.databinding.FragmentStaffBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiStaffFragment : Fragment() {
    private lateinit var binding: FragmentStaffBinding
    private lateinit var adapter: StaffAdapter
    private val viewModel by viewModels<MiStaffViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = StaffAdapter(emptyList())
        staffListRecyclerView.adapter = adapter
        staffListRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.staffArray.collect { staff ->
                    adapter.updateStaff(staff)
                }
            }
        }
    }
}