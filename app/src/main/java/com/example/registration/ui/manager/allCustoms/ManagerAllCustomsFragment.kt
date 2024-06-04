package com.example.registration.ui.manager.allCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagerAllCustomsFragment : Fragment() {

    private lateinit var binding: FragmentManagerAllCustomsBinding
    private lateinit var adapter: ManagerAllCustomAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<ManagerAllCustomsPageViewModel>()
    private var searchStr = 0

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
            if (text.toString().isNotBlank()) {
                searchStr = text.toString().toInt()
                tvPageInfo.visibility = View.GONE
                btNext.visibility = View.GONE
                btPrevious.visibility = View.GONE
            } else {
                searchStr = 0
                tvPageInfo.visibility = View.VISIBLE
                btNext.visibility = View.VISIBLE
                btPrevious.visibility = View.VISIBLE
            }
        }
        updatePageInfo(viewModel.currentPage)
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
            val searchId: Int = searchStr
            if (searchId <= 0) viewModel.getAllCustomsPage(0) else viewModel.searchCustomById(
                searchId
            )
            viewModel.currentPage = 0
            updatePageInfo(viewModel.currentPage)
        }

        btNext.setOnClickListener {
            if (searchStr == 0) {
                if (viewModel.isLastPage()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPage()
                    updatePageInfo(viewModel.currentPage)
                }
            }
        }
        btPrevious.setOnClickListener {
            if (searchStr == 0) {
                viewModel.loadPreviousPage()
                updatePageInfo(viewModel.currentPage)
            }
        }
    }

    private fun updatePageInfo(page: Int) = with(binding) {
        val from = page * 10 + 1
        lifecycleScope.launch {
            delay(600)
            val to = from + viewModel.customAllArray.value.size - 1
            tvPageInfo.text = "з $from по $to"
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val list: ArrayList<CustomProductDTO>? =
                viewModel.customAllArray.value[position].customProductList as ArrayList<CustomProductDTO>?
            bundle.putParcelableArrayList("allCustomProductList", list)
            navController.navigate(
                R.id.action_managerAllCustomsFragment_to_allCustomsDetailFragment, bundle
            )
        }
    }
}