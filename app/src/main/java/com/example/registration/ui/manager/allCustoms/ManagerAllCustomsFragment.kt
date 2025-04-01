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

    lateinit var binding: FragmentManagerAllCustomsBinding
    private lateinit var adapter: ManagerAllCustomAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<ManagerAllCustomsPageViewModel>()
    private var searchStr = 0
    var arraySize = 10

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
        adapter = ManagerAllCustomAdapter(emptyList(), onItemClick(), onMessagingClick())
        allCustomListRecyclerView.adapter = adapter
        allCustomListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
        etSearchCustomField.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank()) {
                searchStr = text.toString().toInt()
                tvPageInfo.visibility = View.GONE
                btNext.visibility = View.GONE
                btPrevious.visibility = View.GONE
                cbMyDepartmentOption.isEnabled = false
                cbMyDepartmentOption.alpha = 0.5f
                cbMessageOption.isEnabled = false
                cbMessageOption.alpha = 0.5f

            } else {
                searchStr = 0
                tvPageInfo.visibility = View.VISIBLE
                btNext.visibility = View.VISIBLE
                btPrevious.visibility = View.VISIBLE
                cbMessageOption.visibility = View.VISIBLE
                cbMyDepartmentOption.isEnabled = true
                cbMyDepartmentOption.alpha = 1f
            }
        }

        cbMyDepartmentOption.setOnClickListener {
            if (cbMyDepartmentOption.isChecked) {
                cbMessageOption.isEnabled = true
                cbMessageOption.alpha = 1f
            } else {
                cbMessageOption.isEnabled = false
                cbMessageOption.alpha = 0.5f
                cbMessageOption.isChecked = false
            }

            if(cbMyDepartmentOption.isChecked && cbMessageOption.isChecked){

            }
        }
        updatePageInfo(viewModel.currentPage)
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customAllArray.collect { customs ->
                    adapter.updateCustoms(customs)
                    arraySize = customs.size
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonSearch.setOnClickListener {
            val searchId: Int = searchStr
            if (searchId <= 0) loadData() else viewModel.searchCustomById(
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

    fun updatePageInfo(page: Int) = with(binding) {
        val from = page * 10 + 1
        var to = page * 10 + 10
        tvPageInfo.text = "з $from по $to"
        lifecycleScope.launch {
            delay(3000)
            to = page * 10 + arraySize
            if (to != 0) {
                tvPageInfo.text = "з $from по $to"
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
                R.id.action_managerAllCustomsFragment_to_allCustomsDetailFragment, bundle
            )
        }
    }

    private fun onMessagingClick(): (Int) -> Unit {
        return { customId ->
            val bundle = Bundle()
            bundle.putInt("customId", customId)
            navController.navigate(
                R.id.action_managerAllCustomsFragment_to_managerCustomChattingFragment,
                bundle
            )
        }
    }

    private fun loadData() = with(binding) {
        if (cbMyDepartmentOption.isChecked && cbMessageOption.isChecked) {
            viewModel.getAllCustomsWithMessagePage(0)
            viewModel.chooseNum = 2
        } else if (cbMyDepartmentOption.isChecked) {
            viewModel.getAllCustomsWithDepartmentPage(0)
            viewModel.chooseNum = 1
        } else {
            viewModel.getAllCustomsPage(0)
            viewModel.chooseNum = 0
        }
        viewModel.currentPage = 0
        updatePageInfo(viewModel.currentPage)
    }
}