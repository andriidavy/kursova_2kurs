package com.example.registration.myISAM.ui.customer.custom

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
import com.example.registration.adapter.custom.CustomAdapter
import com.example.registration.databinding.FragmentCustomerCustomPageBinding
import com.example.registration.model.custom.CustomProductDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiCustomerCustomPageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerCustomPageBinding
    private lateinit var adapter: CustomAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiCustomerCustomPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerCustomPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
    }

    private fun setupViews() = with(binding) {
        adapter = CustomAdapter(emptyList(), itemClick())
        customListRecyclerView.adapter = adapter
        customListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customDTOArray.collect { customs ->
                    adapter.updateCustoms(customs)
                }
            }
        }
    }

    private fun itemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()

            val list: ArrayList<CustomProductDTO>? =
                viewModel.customDTOArray.value.getOrNull(position)?.customProductList as ArrayList<CustomProductDTO>?
            bundle.putParcelableArrayList("customProductList", list)

            navController.navigate(
                R.id.action_miCustomerCustomPageFragment_to_miCustomProductDetailFragment,
                bundle
            )
        }
    }
}