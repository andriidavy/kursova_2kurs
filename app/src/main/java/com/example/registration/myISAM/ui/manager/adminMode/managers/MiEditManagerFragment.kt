package com.example.registration.myISAM.ui.manager.adminMode.managers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.manager.ManageManagerAdapter
import com.example.registration.databinding.FragmentEditManagerBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiEditManagerFragment : Fragment() {

    private lateinit var binding: FragmentEditManagerBinding
    private lateinit var adapter: ManageManagerAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiEditManagerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditManagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = ManageManagerAdapter(emptyList(), onItemClick(), onRemoveManagerClick())
        editManagerRecyclerView.adapter = adapter
        editManagerRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.managerAllArray.collect { managers ->
                    adapter.updateManagers(managers)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonAddManager.setOnClickListener {
            navController.navigate(R.id.action_miEditManagerFragment_to_miAddManagerFragment)
        }
    }

    private fun onRemoveManagerClick(): (Int) -> Unit {
        return { position ->
            val managerId: Int = viewModel.managerAllArray.value[position].id
            val deleteResult = viewModel.deleteManagerById(managerId)

            lifecycleScope.launch {
                deleteResult.collect { result ->
                    result.onSuccess {
                        ToastObj.shortToastMake("Менеджер: $managerId видалений", context)
                        viewModel.getAllManagersProfileDTO()
                    }
                    result.onFailure {
                        ToastObj.shortToastMake("Помилка: $it", context)
                    }
                }
            }
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val managerId: Int = viewModel.managerAllArray.value[position].id
            bundle.putInt("managerId", managerId)
            navController.navigate(
                R.id.action_miEditManagerFragment_to_miManagerDepartDetailFragment,
                bundle
            )
        }
    }
}