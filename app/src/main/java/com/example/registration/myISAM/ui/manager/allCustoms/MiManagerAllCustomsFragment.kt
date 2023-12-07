package com.example.registration.myISAM.ui.manager.allCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.ManagerAllCustomAdapter
import com.example.registration.databinding.FragmentManagerAllCustomsBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.ui.manager.allCustoms.ManagerAllCustomsPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiManagerAllCustomsFragment : Fragment() {

    private lateinit var binding: FragmentManagerAllCustomsBinding
    private lateinit var adapter: ManagerAllCustomAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiManagerAllCustomsPageViewModel>()
    private val searchStr = MutableStateFlow(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerAllCustomsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
        setListeners()
    }

    private fun setViews() = with(binding) {
        adapter = ManagerAllCustomAdapter(emptyList(), onItemClick())
        allCustomListRecyclerView.adapter = adapter
        allCustomListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
        etSearchCustomField.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank()) searchStr.value =
                text.toString().toInt() else searchStr.value = 0
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customAllArray.collect { customs ->
                    adapter.updateCustoms(customs)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonSearch.setOnClickListener {
            val searchId: Int = searchStr.value
            if (searchId <= 0) viewModel.getAllCustomsForManager() else viewModel.searchCustomById(
                searchId
            )
            lifecycleScope.launch {
                delay(2000)
                if (viewModel.customAllArray.value.isEmpty()) {
                    ToastObj.longToastMake(
                        getString(R.string.custom_not_found), context
                    )
                }
            }
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val list: ArrayList<CustomProductDTO>? =
                viewModel.customAllArray.value[position].customProductList as ArrayList<CustomProductDTO>?
            bundle.putParcelableArrayList("allCustomProductList", list)
            navController.navigate(
                R.id.action_miManagerAllCustomsFragment_to_miAllCustomsDetailFragment, bundle
            )
        }
    }
}